package com.example.Gestion.de.Stock.service;

import com.example.Gestion.de.Stock.dto.CategorieDTO;
import com.example.Gestion.de.Stock.Entity.Categorie;
import com.example.Gestion.de.Stock.Repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<CategorieDTO> getAllCategories() {
        return categorieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategorieDTO getCategorieById(Long id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + id));
        return convertToDTO(categorie);
    }

    @Transactional(readOnly = true)
    public Integer countProduitsByCategorie(Long categorieId) {
        return categorieRepository.countProduitsByCategorie(categorieId);
    }

    @Transactional
    public CategorieDTO createCategorie(CategorieDTO categorieDTO) {
        if (categorieRepository.existsByNom(categorieDTO.getNom())) {
            throw new RuntimeException("Une catégorie avec ce nom existe déjà");
        }

        Categorie categorie = convertToEntity(categorieDTO);
        Categorie savedCategorie = categorieRepository.save(categorie);
        return convertToDTO(savedCategorie);
    }

    @Transactional
    public CategorieDTO updateCategorie(Long id, CategorieDTO categorieDTO) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + id));

        categorie.setNom(categorieDTO.getNom());
        categorie.setDescription(categorieDTO.getDescription());
        categorie.setCode(categorieDTO.getCode());

        Categorie updatedCategorie = categorieRepository.save(categorie);
        return convertToDTO(updatedCategorie);
    }

    @Transactional
    public void deleteCategorie(Long id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + id));

        Integer produitsCount = categorieRepository.countProduitsByCategorie(id);
        if (produitsCount > 0) {
            throw new RuntimeException("Impossible de supprimer la catégorie car elle contient " + produitsCount + " produit(s)");
        }

        categorieRepository.delete(categorie);
    }

    private CategorieDTO convertToDTO(Categorie categorie) {
        CategorieDTO dto = modelMapper.map(categorie, CategorieDTO.class);
        dto.setNombreProduits(categorieRepository.countProduitsByCategorie(categorie.getId()));
        return dto;
    }

    private Categorie convertToEntity(CategorieDTO dto) {
        return modelMapper.map(dto, Categorie.class);
    }
}