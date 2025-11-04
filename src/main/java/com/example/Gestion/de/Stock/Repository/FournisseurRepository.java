package com.example.Gestion.de.Stock.Repository;

import com.example.Gestion.de.Stock.Entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    Optional<Fournisseur> findByEmail(String email);
    List<Fournisseur> findByActif(Boolean actif);

    @Query("SELECT f FROM Fournisseur f WHERE LOWER(f.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Fournisseur> searchFournisseurs(@Param("keyword") String keyword);
}