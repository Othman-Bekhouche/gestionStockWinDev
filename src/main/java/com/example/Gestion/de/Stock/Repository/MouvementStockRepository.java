package com.example.Gestion.de.Stock.Repository;

import com.example.Gestion.de.Stock.Entity.MouvementStock;
import com.example.Gestion.de.Stock.Entity.TypeMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    List<MouvementStock> findByProduitId(Long produitId);
    List<MouvementStock> findByType(TypeMouvement type);
    List<MouvementStock> findByUtilisateurId(Long utilisateurId);
    List<MouvementStock> findByDateMouvementBetween(LocalDateTime dateDebut, LocalDateTime dateFin);

    @Query("SELECT m FROM MouvementStock m WHERE m.produit.id = :produitId " +
            "ORDER BY m.dateMouvement DESC")
    List<MouvementStock> findHistoriqueProduit(@Param("produitId") Long produitId);

    @Query("SELECT m FROM MouvementStock m WHERE m.type = :type " +
            "AND m.dateMouvement BETWEEN :dateDebut AND :dateFin")
    List<MouvementStock> findByTypeAndPeriode(@Param("type") TypeMouvement type,
                                              @Param("dateDebut") LocalDateTime dateDebut,
                                              @Param("dateFin") LocalDateTime dateFin);
}