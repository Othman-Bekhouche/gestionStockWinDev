package com.example.Gestion.de.Stock.controller;

import com.example.Gestion.de.Stock.dto.ApiResponse;
import com.example.Gestion.de.Stock.dto.FournisseurDTO;
import com.example.Gestion.de.Stock.service.FournisseurService;
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
@RequestMapping("/fournisseurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Fournisseurs", description = "Gestion des fournisseurs")
@SecurityRequirement(name = "bearerAuth")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @GetMapping
    @Operation(summary = "Liste de tous les fournisseurs", description = "Récupérer tous les fournisseurs actifs")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<FournisseurDTO>>> getAllFournisseurs() {
        List<FournisseurDTO> fournisseurs = fournisseurService.getAllFournisseurs();
        return ResponseEntity.ok(ApiResponse.success(fournisseurs));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un fournisseur", description = "Récupérer un fournisseur par son ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<FournisseurDTO>> getFournisseurById(@PathVariable Long id) {
        FournisseurDTO fournisseur = fournisseurService.getFournisseurById(id);
        return ResponseEntity.ok(ApiResponse.success(fournisseur));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des fournisseurs", description = "Recherche par nom")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<FournisseurDTO>>> searchFournisseurs(@RequestParam String keyword) {
        List<FournisseurDTO> fournisseurs = fournisseurService.searchFournisseurs(keyword);
        return ResponseEntity.ok(ApiResponse.success(fournisseurs));
    }

    @GetMapping("/actifs")
    @Operation(summary = "Fournisseurs actifs", description = "Liste des fournisseurs actifs uniquement")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<FournisseurDTO>>> getFournisseursActifs() {
        List<FournisseurDTO> fournisseurs = fournisseurService.getFournisseursActifs();
        return ResponseEntity.ok(ApiResponse.success(fournisseurs));
    }

    @PostMapping
    @Operation(summary = "Créer un fournisseur", description = "Ajouter un nouveau fournisseur")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<FournisseurDTO>> createFournisseur(@Valid @RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO created = fournisseurService.createFournisseur(fournisseurDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Fournisseur créé avec succès", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un fournisseur", description = "Mettre à jour les informations d'un fournisseur")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<FournisseurDTO>> updateFournisseur(
            @PathVariable Long id,
            @Valid @RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO updated = fournisseurService.updateFournisseur(id, fournisseurDTO);
        return ResponseEntity.ok(ApiResponse.success("Fournisseur modifié avec succès", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un fournisseur", description = "Désactiver un fournisseur")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok(ApiResponse.success("Fournisseur supprimé avec succès", null));
    }
}
