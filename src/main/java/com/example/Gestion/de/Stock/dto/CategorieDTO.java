package com.example.Gestion.de.Stock.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategorieDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String description;
    private String code;
    private LocalDateTime dateCreation;
    private Integer nombreProduits;
}