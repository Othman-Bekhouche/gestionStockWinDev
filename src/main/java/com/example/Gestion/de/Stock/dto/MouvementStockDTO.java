package com.example.Gestion.de.Stock.dto;

import com.example.Gestion.de.Stock.Entity.TypeMouvement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MouvementStockDTO {
    private Long id;

    @NotNull(message = "Le type de mouvement est obligatoire")
    private TypeMouvement type;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantite;

    private LocalDateTime dateMouvement;
    private String reference;
    private String motif;
    private Double prixUnitaire;

    @NotNull(message = "Le produit est obligatoire")
    private Long produitId;

    private String produitNom;
    private Long utilisateurId;
    private String utilisateurNom;
    private Long fournisseurId;
    private String fournisseurNom;
}
