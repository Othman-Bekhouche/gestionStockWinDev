package com.example.Gestion.de.Stock.controller;

import com.example.Gestion.de.Stock.dto.ApiResponse;
import com.example.Gestion.de.Stock.dto.ProduitDTO;
import com.example.Gestion.de.Stock.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Produits", description = "Gestion des produits")
@SecurityRequirement(name = "bearerAuth")
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    @Operation(summary = "Liste de tous les produits", description = "Récupérer tous les produits actifs")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> getAllProduits() {
        List<ProduitDTO> produits = produitService.getAllProduits();
        return ResponseEntity.ok(ApiResponse.success(produits));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un produit", description = "Récupérer un produit par son ID")
    public ResponseEntity<ApiResponse<ProduitDTO>> getProduitById(@PathVariable Long id) {
        ProduitDTO produit = produitService.getProduitById(id);
        return ResponseEntity.ok(ApiResponse.success(produit));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des produits", description = "Recherche par nom, référence ou code-barres")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> searchProduits(@RequestParam String keyword) {
        List<ProduitDTO> produits = produitService.searchProduits(keyword);
        return ResponseEntity.ok(ApiResponse.success(produits));
    }

    @GetMapping("/alertes")
    @Operation(summary = "Produits en alerte de stock", description = "Liste des produits dont le stock est en dessous du seuil")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> getProduitsEnAlerte() {
        List<ProduitDTO> produits = produitService.getProduitsEnAlerte();
        return ResponseEntity.ok(ApiResponse.success(produits));
    }

    @GetMapping("/rupture")
    @Operation(summary = "Produits en rupture de stock", description = "Liste des produits avec stock à zéro")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> getProduitsEnRupture() {
        List<ProduitDTO> produits = produitService.getProduitsEnRupture();
        return ResponseEntity.ok(ApiResponse.success(produits));
    }

    @GetMapping("/categorie/{categorieId}")
    @Operation(summary = "Produits par catégorie", description = "Récupérer tous les produits d'une catégorie")
    public ResponseEntity<ApiResponse<List<ProduitDTO>>> getProduitsByCategorie(@PathVariable Long categorieId) {
        List<ProduitDTO> produits = produitService.getProduitsByCategorie(categorieId);
        return ResponseEntity.ok(ApiResponse.success(produits));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit", description = "Ajouter un nouveau produit au stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<ProduitDTO>> createProduit(@Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO created = produitService.createProduit(produitDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Produit créé avec succès", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un produit", description = "Mettre à jour les informations d'un produit")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<ProduitDTO>> updateProduit(
            @PathVariable Long id,
            @Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO updated = produitService.updateProduit(id, produitDTO);
        return ResponseEntity.ok(ApiResponse.success("Produit modifié avec succès", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit", description = "Désactiver un produit (suppression logique)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.ok(ApiResponse.success("Produit supprimé avec succès", null));
    }

    @PutMapping("/{id}/stock/ajouter")
    @Operation(summary = "Ajouter du stock", description = "Augmenter la quantité en stock d'un produit")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<ProduitDTO>> ajouterStock(
            @PathVariable Long id,
            @RequestParam Integer quantite) {
        ProduitDTO updated = produitService.ajouterStock(id, quantite);
        return ResponseEntity.ok(ApiResponse.success("Stock ajouté avec succès", updated));
    }

    @PutMapping("/{id}/stock/retirer")
    @Operation(summary = "Retirer du stock", description = "Diminuer la quantité en stock d'un produit")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<ProduitDTO>> retirerStock(
            @PathVariable Long id,
            @RequestParam Integer quantite) {
        ProduitDTO updated = produitService.retirerStock(id, quantite);
        return ResponseEntity.ok(ApiResponse.success("Stock retiré avec succès", updated));
    }
}
