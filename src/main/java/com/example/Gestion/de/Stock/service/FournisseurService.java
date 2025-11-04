package com.example.Gestion.de.Stock.service;

import com.example.Gestion.de.Stock.dto.FournisseurDTO;
import com.example.Gestion.de.Stock.Entity.Fournisseur;
import com.example.Gestion.de.Stock.Repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<FournisseurDTO> getAllFournisseurs() {
        return fournisseurRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FournisseurDTO getFournisseurById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID: " + id));
        return convertToDTO(fournisseur);
    }

    @Transactional(readOnly = true)
    public List<FournisseurDTO> searchFournisseurs(String keyword) {
        return fournisseurRepository.searchFournisseurs(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FournisseurDTO> getFournisseursActifs() {
        return fournisseurRepository.findByActif(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FournisseurDTO createFournisseur(FournisseurDTO fournisseurDTO) {
        Fournisseur fournisseur = convertToEntity(fournisseurDTO);
        fournisseur.setActif(true);
        Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
        return convertToDTO(savedFournisseur);
    }

    @Transactional
    public FournisseurDTO updateFournisseur(Long id, FournisseurDTO fournisseurDTO) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID: " + id));

        fournisseur.setNom(fournisseurDTO.getNom());
        fournisseur.setEmail(fournisseurDTO.getEmail());
        fournisseur.setTelephone(fournisseurDTO.getTelephone());
        fournisseur.setAdresse(fournisseurDTO.getAdresse());
        fournisseur.setVille(fournisseurDTO.getVille());
        fournisseur.setCodePostal(fournisseurDTO.getCodePostal());
        fournisseur.setPays(fournisseurDTO.getPays());

        Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);
        return convertToDTO(updatedFournisseur);
    }

    @Transactional
    public void deleteFournisseur(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID: " + id));
        fournisseur.setActif(false);
        fournisseurRepository.save(fournisseur);
    }

    private FournisseurDTO convertToDTO(Fournisseur fournisseur) {
        return modelMapper.map(fournisseur, FournisseurDTO.class);
    }

    private Fournisseur convertToEntity(FournisseurDTO dto) {
        return modelMapper.map(dto, Fournisseur.class);
    }
}