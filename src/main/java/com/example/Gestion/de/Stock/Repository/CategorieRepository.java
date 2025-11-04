package com.example.Gestion.de.Stock.Repository;

import com.example.Gestion.de.Stock.Entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByNom(String nom);
    Optional<Categorie> findByCode(String code);
    Boolean existsByNom(String nom);

    @Query("SELECT COUNT(p) FROM Produit p WHERE p.categorie.id = :categorieId")
    Integer countProduitsByCategorie(@Param("categorieId") Long categorieId);
}