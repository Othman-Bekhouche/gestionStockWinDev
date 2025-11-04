package com.example.Gestion.de.Stock.service;

import com.example.Gestion.de.Stock.dto.CommandeDTO;
import com.example.Gestion.de.Stock.dto.LigneCommandeDTO;
import com.example.Gestion.de.Stock.Entity.*;
import com.example.Gestion.de.Stock.Repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<CommandeDTO> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommandeDTO getCommandeById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
        return convertToDTO(commande);
    }

    @Transactional(readOnly = true)
    public CommandeDTO getCommandeByNumero(String numeroCommande) {
        Commande commande = commandeRepository.findByNumeroCommande(numeroCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec le numéro: " + numeroCommande));
        return convertToDTO(commande);
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> getCommandesByUtilisateur(Long utilisateurId) {
        return commandeRepository.findByUtilisateurId(utilisateurId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommandeDTO> getCommandesByStatut(StatutCommande statut) {
        return commandeRepository.findByStatut(statut).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommandeDTO createCommande(CommandeDTO commandeDTO) {
        Utilisateur utilisateur = utilisateurRepository.findById(commandeDTO.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Commande commande = Commande.builder()
                .numeroCommande(genererNumeroCommande())
                .dateCommande(commandeDTO.getDateCommande() != null ? commandeDTO.getDateCommande() : LocalDate.now())
                .statut(StatutCommande.EN_ATTENTE)
                .utilisateur(utilisateur)
                .commentaire(commandeDTO.getCommentaire())
                .lignes(new ArrayList<>())
                .build();

        double montantTotal = 0.0;

        for (LigneCommandeDTO ligneDTO : commandeDTO.getLignes()) {
            Produit produit = produitRepository.findById(ligneDTO.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

            if (produit.getQuantiteStock() < ligneDTO.getQuantite()) {
                throw new RuntimeException("Stock insuffisant pour le produit: " + produit.getNom() +
                        ". Stock disponible: " + produit.getQuantiteStock());
            }

            LigneCommande ligne = LigneCommande.builder()
                    .produit(produit)
                    .commande(commande)
                    .quantite(ligneDTO.getQuantite())
                    .prixUnitaire(produit.getPrixVente())
                    .build();
            ligne.calculerSousTotal();

            montantTotal += ligne.getSousTotal();
            commande.getLignes().add(ligne);

            produit.setQuantiteStock(produit.getQuantiteStock() - ligneDTO.getQuantite());
            produitRepository.save(produit);

            MouvementStock mouvement = MouvementStock.builder()
                    .type(TypeMouvement.SORTIE)
                    .quantite(ligneDTO.getQuantite())
                    .produit(produit)
                    .utilisateur(utilisateur)
                    .commande(commande)
                    .motif("Commande " + commande.getNumeroCommande())
                    .prixUnitaire(produit.getPrixVente())
                    .build();
            mouvementStockRepository.save(mouvement);
        }

        commande.setMontantTotal(montantTotal);
        Commande savedCommande = commandeRepository.save(commande);

        return convertToDTO(savedCommande);
    }

    @Transactional
    public CommandeDTO updateStatut(Long id, StatutCommande statut) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commande.setStatut(statut);
        if (statut == StatutCommande.LIVREE) {
            commande.setDateLivraison(LocalDate.now());
        }

        Commande updatedCommande = commandeRepository.save(commande);
        return convertToDTO(updatedCommande);
    }

    @Transactional
    public CommandeDTO annulerCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (commande.getStatut() == StatutCommande.LIVREE) {
            throw new RuntimeException("Impossible d'annuler une commande déjà livrée");
        }

        for (LigneCommande ligne : commande.getLignes()) {
            Produit produit = ligne.getProduit();
            produit.setQuantiteStock(produit.getQuantiteStock() + ligne.getQuantite());
            produitRepository.save(produit);
        }

        commande.setStatut(StatutCommande.ANNULEE);
        Commande updatedCommande = commandeRepository.save(commande);
        return convertToDTO(updatedCommande);
    }

    @Transactional
    public void deleteCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commandeRepository.delete(commande);
    }

    private String genererNumeroCommande() {
        return "CMD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private CommandeDTO convertToDTO(Commande commande) {
        CommandeDTO dto = modelMapper.map(commande, CommandeDTO.class);
        dto.setUtilisateurId(commande.getUtilisateur().getId());
        dto.setUtilisateurNom(commande.getUtilisateur().getNom() + " " + commande.getUtilisateur().getPrenom());

        if (commande.getLignes() != null) {
            List<LigneCommandeDTO> lignesDTO = commande.getLignes().stream()
                    .map(ligne -> {
                        LigneCommandeDTO ligneDTO = modelMapper.map(ligne, LigneCommandeDTO.class);
                        ligneDTO.setProduitId(ligne.getProduit().getId());
                        ligneDTO.setProduitNom(ligne.getProduit().getNom());
                        return ligneDTO;
                    })
                    .collect(Collectors.toList());
            dto.setLignes(lignesDTO);
        }

        return dto;
    }
}