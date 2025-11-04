package com.example.Gestion.de.Stock.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FournisseurDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Email(message = "Email invalide")
    private String email;

    private String telephone;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private Boolean actif;
    private LocalDateTime dateCreation;
}