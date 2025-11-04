package com.example.Gestion.de.Stock.controller;

import com.example.Gestion.de.Stock.dto.ApiResponse;
import com.example.Gestion.de.Stock.dto.LoginRequest;
import com.example.Gestion.de.Stock.dto.LoginResponse;
import com.example.Gestion.de.Stock.dto.UtilisateurDTO;
import com.example.Gestion.de.Stock.Entity.Utilisateur;
import com.example.Gestion.de.Stock.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Authentification", description = "API d'authentification et inscription")
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur", description = "Authentification avec email et mot de passe")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Connexion réussie", response));
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription utilisateur", description = "Créer un nouveau compte utilisateur")
    public ResponseEntity<ApiResponse<UtilisateurDTO>> register(@Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = modelMapper.map(utilisateurDTO, Utilisateur.class);
        Utilisateur savedUtilisateur = authService.register(utilisateur);
        UtilisateurDTO responseDTO = modelMapper.map(savedUtilisateur, UtilisateurDTO.class);
        return ResponseEntity.ok(ApiResponse.success("Inscription réussie", responseDTO));
    }

    @GetMapping("/test")
    @Operation(summary = "Test connexion", description = "Vérifier si l'API fonctionne")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok(ApiResponse.success("API Gestion de Stock fonctionne correctement !"));
    }
}