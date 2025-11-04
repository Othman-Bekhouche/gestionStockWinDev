package com.example.Gestion.de.Stock.controller;

import com.example.Gestion.de.Stock.dto.ApiResponse;
import com.example.Gestion.de.Stock.dto.CommandeDTO;
import com.example.Gestion.de.Stock.Entity.StatutCommande;
import com.example.Gestion.de.Stock.service.CommandeService;
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
@RequestMapping("/commandes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Commandes", description = "Gestion des commandes")
@SecurityRequirement(name = "bearerAuth")
public class CommandeController {

    private final CommandeService commandeService;

    @GetMapping
    @Operation(summary = "Liste de toutes les commandes", description = "Récupérer toutes les commandes")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<CommandeDTO>>> getAllCommandes() {
        List<CommandeDTO> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(ApiResponse.success(commandes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détails d'une commande", description = "Récupérer une commande par son ID")
    public ResponseEntity<ApiResponse<CommandeDTO>> getCommandeById(@PathVariable Long id) {
        CommandeDTO commande = commandeService.getCommandeById(id);
        return ResponseEntity.ok(ApiResponse.success(commande));
    }

    @GetMapping("/numero/{numeroCommande}")
    @Operation(summary = "Commande par numéro", description = "Rechercher une commande par son numéro")
    public ResponseEntity<ApiResponse<CommandeDTO>> getCommandeByNumero(@PathVariable String numeroCommande) {
        CommandeDTO commande = commandeService.getCommandeByNumero(numeroCommande);
        return ResponseEntity.ok(ApiResponse.success(commande));
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    @Operation(summary = "Commandes d'un utilisateur", description = "Récupérer toutes les commandes d'un utilisateur")
    public ResponseEntity<ApiResponse<List<CommandeDTO>>> getCommandesByUtilisateur(@PathVariable Long utilisateurId) {
        List<CommandeDTO> commandes = commandeService.getCommandesByUtilisateur(utilisateurId);
        return ResponseEntity.ok(ApiResponse.success(commandes));
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Commandes par statut", description = "Filtrer les commandes par statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<List<CommandeDTO>>> getCommandesByStatut(@PathVariable StatutCommande statut) {
        List<CommandeDTO> commandes = commandeService.getCommandesByStatut(statut);
        return ResponseEntity.ok(ApiResponse.success(commandes));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle commande", description = "Passer une nouvelle commande")
    public ResponseEntity<ApiResponse<CommandeDTO>> createCommande(@Valid @RequestBody CommandeDTO commandeDTO) {
        CommandeDTO created = commandeService.createCommande(commandeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Commande créée avec succès", created));
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Modifier le statut", description = "Changer le statut d'une commande")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONNEL')")
    public ResponseEntity<ApiResponse<CommandeDTO>> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutCommande statut) {
        CommandeDTO updated = commandeService.updateStatut(id, statut);
        return ResponseEntity.ok(ApiResponse.success("Statut modifié avec succès", updated));
    }

    @PutMapping("/{id}/annuler")
    @Operation(summary = "Annuler une commande", description = "Annuler une commande et remettre le stock")
    public ResponseEntity<ApiResponse<CommandeDTO>> annulerCommande(@PathVariable Long id) {
        CommandeDTO annulee = commandeService.annulerCommande(id);
        return ResponseEntity.ok(ApiResponse.success("Commande annulée avec succès", annulee));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande", description = "Supprimer définitivement une commande")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.ok(ApiResponse.success("Commande supprimée avec succès", null));
    }
}