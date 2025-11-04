package com.example.Gestion.de.Stock.controller;

import com.example.Gestion.de.Stock.dto.ApiResponse;
import com.example.Gestion.de.Stock.dto.CategorieDTO;
import com.example.Gestion.de.Stock.service.CategorieService;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Catégories", description = "Gestion des catégories de produits")
@SecurityRequirement(name = "bearerAuth")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    @Operation(summary = "Liste de toutes les catégories", description = "Récupérer toutes les catégories")
    public ResponseEntity<ApiResponse<List<CategorieDTO>>> getAllCategories() {
        List<CategorieDTO> categories = categorieService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détails d'une catégorie", description = "Récupérer une catégorie par son ID")
    public ResponseEntity<ApiResponse<CategorieDTO>> getCategorieById(@PathVariable Long id) {
        CategorieDTO categorie = categorieService.getCategorieById(id);
        return ResponseEntity.ok(ApiResponse.success(categorie));
    }

    @GetMapping("/{id}/produits-count")
    @Operation(summary = "Nombre de produits", description = "Compter le nombre de produits dans une catégorie")
    public ResponseEntity<ApiResponse<Integer>> countProduits(@PathVariable Long id) {
        Integer count = categorieService.countProduitsByCategorie(id);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PostMapping
    @Operation(summary = "Créer une catégorie", description = "Ajouter une nouvelle catégorie")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<CategorieDTO>> createCategorie(@Valid @RequestBody CategorieDTO categorieDTO) {
        CategorieDTO created = categorieService.createCategorie(categorieDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Catégorie créée avec succès", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une catégorie", description = "Mettre à jour une catégorie")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<CategorieDTO>> updateCategorie(
            @PathVariable Long id,
            @Valid @RequestBody CategorieDTO categorieDTO) {
        CategorieDTO updated = categorieService.updateCategorie(id, categorieDTO);
        return ResponseEntity.ok(ApiResponse.success("Catégorie modifiée avec succès", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie", description = "Supprimer une catégorie (si aucun produit associé)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.ok(ApiResponse.success("Catégorie supprimée avec succès", null));
    }
}