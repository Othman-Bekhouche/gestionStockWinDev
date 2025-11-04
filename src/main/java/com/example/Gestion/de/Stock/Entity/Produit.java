package com.example.Gestion.de.Stock.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String description;

    @Column(unique = true)
    private String reference;

    @Column(unique = true)
    private String codeBarres;

    @Column(nullable = false)
    private Double prixAchat;

    @Column(nullable = false)
    private Double prixVente;

    @Column(nullable = false)
    private Integer quantiteStock = 0;

    @Column(nullable = false)
    private Integer seuilAlerte = 10;

    private String image;

    @Column(nullable = false)
    private Boolean actif = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    private LocalDateTime dateModification;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @ManyToMany
    @JoinTable(
            name = "produit_fournisseur",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "fournisseur_id")
    )
    @JsonIgnore
    private List<Fournisseur> fournisseurs;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<MouvementStock> mouvements;
}
