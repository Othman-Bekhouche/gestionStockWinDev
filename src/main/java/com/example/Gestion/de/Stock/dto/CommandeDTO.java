package com.example.Gestion.de.Stock.dto;

import com.example.Gestion.de.Stock.Entity.StatutCommande;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeDTO {
    private Long id;
    private String numeroCommande;

    @NotNull(message = "La date de commande est obligatoire")
    private LocalDate dateCommande;

    private LocalDate dateLivraison;
    private StatutCommande statut;
    private Double montantTotal;
    private String commentaire;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long utilisateurId;

    private String utilisateurNom;
    private LocalDateTime dateCreation;
    private List<LigneCommandeDTO> lignes;
}