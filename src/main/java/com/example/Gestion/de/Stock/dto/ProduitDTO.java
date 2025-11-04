package com.example.Gestion.de.Stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProduitDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String description;
    private String reference;
    private String codeBarres;

    @NotNull(message = "Le prix d'achat est obligatoire")
    @Min(value = 0, message = "Le prix d'achat doit être positif")
    private Double prixAchat;

    @NotNull(message = "Le prix de vente est obligatoire")
    @Min(value = 0, message = "Le prix de vente doit être positif")
    private Double prixVente;

    private Integer quantiteStock;
    private Integer seuilAlerte;
    private String image;
    private Boolean actif;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categorieId;

    private String categorieNom;
    private LocalDateTime dateCreation;
}
