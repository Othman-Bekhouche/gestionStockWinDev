package com.example.Gestion.de.Stock.dto;

import com.example.Gestion.de.Stock.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UtilisateurDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;

    private String motDePasse;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;

    private String telephone;
    private Boolean actif;
    private LocalDateTime dateCreation;
}