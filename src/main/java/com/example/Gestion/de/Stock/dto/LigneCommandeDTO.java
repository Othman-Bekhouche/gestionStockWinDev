package com.example.Gestion.de.Stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LigneCommandeDTO {
    private Long id;

    @NotNull(message = "Le produit est obligatoire")
    private Long produitId;

    private String produitNom;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantite;

    private Double prixUnitaire;
    private Double sousTotal;
}
