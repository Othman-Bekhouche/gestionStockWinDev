package com.example.Gestion.de.Stock.Repository;

import com.example.Gestion.de.Stock.Entity.Commande;
import com.example.Gestion.de.Stock.Entity.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    Optional<Commande> findByNumeroCommande(String numeroCommande);
    List<Commande> findByUtilisateurId(Long utilisateurId);
    List<Commande> findByStatut(StatutCommande statut);
    List<Commande> findByDateCommandeBetween(LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT c FROM Commande c WHERE c.utilisateur.id = :utilisateurId " +
            "AND c.statut = :statut")
    List<Commande> findByUtilisateurAndStatut(@Param("utilisateurId") Long utilisateurId,
                                              @Param("statut") StatutCommande statut);

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.statut = :statut")
    Long countByStatut(@Param("statut") StatutCommande statut);
}
