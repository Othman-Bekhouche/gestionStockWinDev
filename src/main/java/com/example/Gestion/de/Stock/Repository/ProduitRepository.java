package com.example.Gestion.de.Stock.Repository;

import com.example.Gestion.de.Stock.Entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByReference(String reference);
    Optional<Produit> findByCodeBarres(String codeBarres);
    List<Produit> findByCategorieId(Long categorieId);
    List<Produit> findByActif(Boolean actif);

    @Query("SELECT p FROM Produit p WHERE p.quantiteStock <= p.seuilAlerte")
    List<Produit> findProduitsEnAlerte();

    @Query("SELECT p FROM Produit p WHERE p.quantiteStock = 0")
    List<Produit> findProduitsEnRupture();

    @Query("SELECT p FROM Produit p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.reference) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.codeBarres) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Produit> searchProduits(@Param("keyword") String keyword);
}