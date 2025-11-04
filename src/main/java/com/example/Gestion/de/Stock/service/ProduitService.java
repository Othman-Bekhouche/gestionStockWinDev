package com.example.Gestion.de.Stock.service;

import com.example.Gestion.de.Stock.dto.ProduitDTO;
import com.example.Gestion.de.Stock.Entity.Categorie;
import com.example.Gestion.de.Stock.Entity.Produit;
import com.example.Gestion.de.Stock.Repository.CategorieRepository;
import com.example.Gestion.de.Stock.Repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ProduitDTO> getAllProduits() {
        return produitRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProduitDTO getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
        return convertToDTO(produit);
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> searchProduits(String keyword) {
        return produitRepository.searchProduits(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> getProduitsEnAlerte() {
        return produitRepository.findProduitsEnAlerte().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> getProduitsEnRupture() {
        return produitRepository.findProduitsEnRupture().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> getProduitsByCategorie(Long categorieId) {
        return produitRepository.findByCategorieId(categorieId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProduitDTO createProduit(ProduitDTO produitDTO) {
        Categorie categorie = categorieRepository.findById(produitDTO.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + produitDTO.getCategorieId()));

        Produit produit = convertToEntity(produitDTO);
        produit.setCategorie(categorie);
        produit.setActif(true);

        if (produit.getQuantiteStock() == null) {
            produit.setQuantiteStock(0);
        }

        Produit savedProduit = produitRepository.save(produit);
        return convertToDTO(savedProduit);
    }

    @Transactional
    public ProduitDTO updateProduit(Long id, ProduitDTO produitDTO) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));

        Categorie categorie = categorieRepository.findById(produitDTO.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        produit.setNom(produitDTO.getNom());
        produit.setDescription(produitDTO.getDescription());
        produit.setReference(produitDTO.getReference());
        produit.setCodeBarres(produitDTO.getCodeBarres());
        produit.setPrixAchat(produitDTO.getPrixAchat());
        produit.setPrixVente(produitDTO.getPrixVente());
        produit.setSeuilAlerte(produitDTO.getSeuilAlerte());
        produit.setImage(produitDTO.getImage());
        produit.setCategorie(categorie);

        Produit updatedProduit = produitRepository.save(produit);
        return convertToDTO(updatedProduit);
    }

    @Transactional
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
        produit.setActif(false);
        produitRepository.save(produit);
    }

    @Transactional
    public ProduitDTO ajouterStock(Long id, Integer quantite) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));

        produit.setQuantiteStock(produit.getQuantiteStock() + quantite);
        Produit updatedProduit = produitRepository.save(produit);
        return convertToDTO(updatedProduit);
    }

    @Transactional
    public ProduitDTO retirerStock(Long id, Integer quantite) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));

        if (produit.getQuantiteStock() < quantite) {
            throw new RuntimeException("Stock insuffisant. Stock disponible: " + produit.getQuantiteStock());
        }

        produit.setQuantiteStock(produit.getQuantiteStock() - quantite);
        Produit updatedProduit = produitRepository.save(produit);
        return convertToDTO(updatedProduit);
    }

    private ProduitDTO convertToDTO(Produit produit) {
        ProduitDTO dto = modelMapper.map(produit, ProduitDTO.class);
        dto.setCategorieId(produit.getCategorie().getId());
        dto.setCategorieNom(produit.getCategorie().getNom());
        return dto;
    }

    private Produit convertToEntity(ProduitDTO dto) {
        return modelMapper.map(dto, Produit.class);
    }
}