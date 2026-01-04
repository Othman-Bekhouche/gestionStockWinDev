# 📦 API REST - Gestion de Stock

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

API REST complète pour la gestion de stock avec authentification JWT, développée avec Spring Boot.

## 📋 Table des Matières

- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Technologies](#technologies)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Lancement](#lancement)
- [Documentation API](#documentation-api)
- [Architecture](#architecture)
- [Tests](#tests)  
- [Déploiement](#déploiement)
- [Contribution](#contribution)
- [Licence](#licence)

---

## 🎯 Aperçu

Cette API REST permet de gérer un système de stock complet incluant :
- Gestion des produits, catégories et fournisseurs
- Système de commandes avec gestion automatique du stock
- Authentification JWT avec gestion des rôles
- Alertes de stock automatiques
- Historique des mouvements de stock

**URL de base :** `http://localhost:8080/api`

---

## ✨ Fonctionnalités

### 🔐 Authentification & Sécurité
- ✅ Authentification JWT (Token Bearer)
- ✅ Gestion des rôles : ADMIN, PERSONNEL, UTILISATEUR, CLIENT
- ✅ Permissions granulaires par endpoint
- ✅ Cryptage des mots de passe (BCrypt)
- ✅ Durée de session configurable (24h par défaut)

### 📦 Gestion des Produits
- ✅ CRUD complet des produits
- ✅ Recherche par nom, référence ou code-barres
- ✅ Gestion du stock en temps réel
- ✅ Alertes de stock (seuil configurable)
- ✅ Détection automatique des ruptures de stock
- ✅ Filtrage par catégorie

### 🛒 Gestion des Commandes
- ✅ Création de commandes avec lignes multiples
- ✅ Déduction automatique du stock
- ✅ Validation du stock avant commande
- ✅ Gestion des statuts (EN_ATTENTE, VALIDEE, EN_PREPARATION, LIVREE, ANNULEE)
- ✅ Annulation avec remise en stock automatique
- ✅ Calcul automatique des montants
- ✅ Génération de numéro de commande unique

### 📁 Gestion des Catégories
- ✅ CRUD des catégories
- ✅ Comptage des produits par catégorie
- ✅ Validation des suppressions (empêche si produits associés)

### 🏭 Gestion des Fournisseurs
- ✅ CRUD des fournisseurs
- ✅ Recherche par nom
- ✅ Filtrage par statut actif/inactif

### 📊 Historique & Traçabilité
- ✅ Historique de tous les mouvements de stock
- ✅ Traçabilité complète des commandes
- ✅ Logs des actions utilisateurs

---

## 🛠 Technologies

### Backend
- **Java 17** - Langage de programmation
- **Spring Boot 3.2.0** - Framework principal
- **Spring Security** - Sécurité et authentification
- **Spring Data JPA** - Accès aux données
- **Hibernate** - ORM

### Base de Données
- **MySQL 8.0** - Base de données relationnelle

### Sécurité
- **JWT (JSON Web Tokens)** - Authentification stateless
- **BCrypt** - Cryptage des mots de passe

### Documentation
- **Springdoc OpenAPI 3** - Documentation Swagger UI

### Build & Dépendances
- **Maven** - Gestionnaire de dépendances
- **Lombok** - Réduction du code boilerplate
- **ModelMapper** - Conversion DTO/Entity

---

## 📋 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- ☕ **Java JDK 17** ou supérieur
  ```bash
  java -version
  # Doit afficher : openjdk version "17.x.x" ou "21.x.x"
  ```

- 🗄️ **MySQL 8.0** ou supérieur
  ```bash
  mysql --version
  # Doit afficher : mysql Ver 8.0.x
  ```

- 🔧 **Maven 3.6+** (optionnel, Maven Wrapper inclus)
  ```bash
  mvn -version
  # Doit afficher : Apache Maven 3.x.x
  ```

- 💻 **IDE recommandé**
  - IntelliJ IDEA
  - Eclipse
  - Visual Studio Code avec Extension Pack for Java

---

## 🚀 Installation

### 1. Cloner le Repository

```bash
git clone https://github.com/votre-username/gestion-stock-api.git
cd gestion-stock-api
```

### 2. Créer la Base de Données



-- Créer la base de données
CREATE DATABASE gestion_stock_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- coller
  Ensuite, copiez et collez le code SQL contenu dans le fichier data.txt (section requêtes SQL) afin  insérer les données. 
```

### 3. Configurer l'Application

Modifier le fichier `src/main/resources/application.properties` :

```properties
# Configuration MySQL - MODIFIER ICI
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_stock_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE_MYSQL

# JWT Secret - MODIFIER POUR LA PRODUCTION
jwt.secret=VotreCleSecreteTresLongueEtSecuriseeMinimum256Bits2024GestionStock
jwt.expiration=86400000
```

### 4. Installer les Dépendances

```bash
# Avec Maven
mvn clean install

# Ou avec Maven Wrapper (recommandé)
./mvnw clean install
```

---

## ⚙️ Configuration

### Fichier `application.properties`

```properties
# ========================================
# SERVER CONFIGURATION
# ========================================
server.port=8080
server.servlet.context-path=/api

# ========================================
# DATABASE CONFIGURATION
# ========================================
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_stock_db
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# ========================================
# JWT CONFIGURATION
# ========================================
jwt.secret=VotreCleSecrete256Bits
jwt.expiration=86400000  # 24 heures

# ========================================
# CORS CONFIGURATION
# ========================================
cors.allowed-origins=http://localhost:3000,http://localhost:4200
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS

# ========================================
# LOGGING
# ========================================
logging.level.com.example.Gestion.de.Stock=DEBUG
```

### Variables d'Environnement (Production)

```bash
export DB_URL=jdbc:mysql://votre-serveur:3306/gestion_stock_db
export DB_USERNAME=username
export DB_PASSWORD=password
export JWT_SECRET=VotreCleSecreteTresSecurisee
```

---

## 🎬 Lancement

### Avec Maven

```bash
# Compiler et lancer
mvn spring-boot:run
```

### Avec Maven Wrapper

```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

### Avec votre IDE

**IntelliJ IDEA :**
1. Ouvrir le projet
2. Clic droit sur `GestionDeStockApplication.java`
3. `Run 'GestionDeStockApplication'`

**Eclipse :**
1. Importer le projet Maven
2. Clic droit sur le projet → `Run As` → `Spring Boot App`

### Vérification

L'application démarre sur : **http://localhost:8080/api**

Test rapide :
```bash
curl http://localhost:8080/api/auth/test
```

Réponse attendue :
```json
{
  "success": true,
  "message": "API Gestion de Stock fonctionne correctement !",
  "timestamp": "2024-01-30T10:30:00"
}
```

---

## 📚 Documentation API

### Swagger UI (Recommandé)

Une fois l'application lancée, accédez à :

🌐 **http://localhost:8080/api/swagger-ui.html**

Interface interactive pour tester tous les endpoints.

### OpenAPI JSON

📄 **http://localhost:8080/api/v3/api-docs**

### Endpoints Principaux

#### 🔐 Authentification

| Méthode | Endpoint | Description | Auth |
|---------|----------|-------------|------|
| POST | `/auth/login` | Connexion | Non |
| POST | `/auth/register` | Inscription | Non |
| GET | `/auth/test` | Test API | Non |

#### 📦 Produits

| Méthode | Endpoint | Description | Rôle Requis |
|---------|----------|-------------|-------------|
| GET | `/produits` | Liste tous les produits | Tous |
| GET | `/produits/{id}` | Détails d'un produit | Tous |
| GET | `/produits/search?keyword=` | Rechercher | Tous |
| GET | `/produits/alertes` | Produits en alerte | ADMIN, PERSONNEL |
| POST | `/produits` | Créer un produit | ADMIN, PERSONNEL |
| PUT | `/produits/{id}` | Modifier un produit | ADMIN, PERSONNEL |
| DELETE | `/produits/{id}` | Supprimer un produit | ADMIN |
| PUT | `/produits/{id}/stock/ajouter?quantite=` | Ajouter du stock | ADMIN, PERSONNEL |
| PUT | `/produits/{id}/stock/retirer?quantite=` | Retirer du stock | ADMIN, PERSONNEL |

#### 🛒 Commandes

| Méthode | Endpoint | Description | Rôle Requis |
|---------|----------|-------------|-------------|
| GET | `/commandes` | Liste toutes les commandes | ADMIN, PERSONNEL |
| GET | `/commandes/{id}` | Détails d'une commande | Tous |
| GET | `/commandes/utilisateur/{id}` | Commandes d'un utilisateur | Tous |
| GET | `/commandes/statut/{statut}` | Filtrer par statut | ADMIN, PERSONNEL |
| POST | `/commandes` | Créer une commande | Tous |
| PUT | `/commandes/{id}/statut?statut=` | Changer le statut | ADMIN, PERSONNEL |
| PUT | `/commandes/{id}/annuler` | Annuler une commande | Tous |
| DELETE | `/commandes/{id}` | Supprimer une commande | ADMIN |

#### 📁 Catégories

| Méthode | Endpoint | Description | Rôle Requis |
|---------|----------|-------------|-------------|
| GET | `/categories` | Liste toutes les catégories | Tous |
| GET | `/categories/{id}` | Détails d'une catégorie | Tous |
| POST | `/categories` | Créer une catégorie | ADMIN, PERSONNEL |
| PUT | `/categories/{id}` | Modifier une catégorie | ADMIN, PERSONNEL |
| DELETE | `/categories/{id}` | Supprimer une catégorie | ADMIN |

#### 🏭 Fournisseurs

| Méthode | Endpoint | Description | Rôle Requis |
|---------|----------|-------------|-------------|
| GET | `/fournisseurs` | Liste tous les fournisseurs | ADMIN, PERSONNEL |
| GET | `/fournisseurs/{id}` | Détails d'un fournisseur | ADMIN, PERSONNEL |
| GET | `/fournisseurs/search?keyword=` | Rechercher | ADMIN, PERSONNEL |
| POST | `/fournisseurs` | Créer un fournisseur | ADMIN, PERSONNEL |
| PUT | `/fournisseurs/{id}` | Modifier un fournisseur | ADMIN, PERSONNEL |
| DELETE | `/fournisseurs/{id}` | Supprimer un fournisseur | ADMIN |

### Exemples de Requêtes

#### Connexion

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@gestionstock.com",
    "motDePasse": "password123"
  }'
```

#### Récupérer les Produits

```bash
curl -X GET http://localhost:8080/api/produits \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT"
```

#### Créer une Commande

```bash
curl -X POST http://localhost:8080/api/commandes \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT" \
  -H "Content-Type: application/json" \
  -d '{
    "utilisateurId": 1,
    "dateCommande": "2024-01-30",
    "commentaire": "Commande urgente",
    "lignes": [
      {
        "produitId": 1,
        "quantite": 2
      },
      {
        "produitId": 3,
        "quantite": 1
      }
    ]
  }'
```

---

## 🏗 Architecture

### Structure du Projet

```
gestion-stock-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/Gestion/de/Stock/
│   │   │   ├── Entity/              # Entités JPA
│   │   │   │   ├── Utilisateur.java
│   │   │   │   ├── Produit.java
│   │   │   │   ├── Commande.java
│   │   │   │   └── ...
│   │   │   ├── Repository/          # Repositories
│   │   │   │   ├── UtilisateurRepository.java
│   │   │   │   ├── ProduitRepository.java
│   │   │   │   └── ...
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── ProduitDTO.java
│   │   │   │   └── ...
│   │   │   ├── service/             # Logique métier
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── ProduitService.java
│   │   │   │   └── ...
│   │   │   ├── controller/          # Contrôleurs REST
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ProduitController.java
│   │   │   │   └── ...
│   │   │   ├── security/            # Configuration sécurité
│   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── ...
│   │   │   ├── exception/           # Gestion des erreurs
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── GestionDeStockApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── import.sql (optionnel)
│   └── test/                        # Tests unitaires
├── pom.xml                          # Configuration Maven
└── README.md
```

### Diagramme de Classes (Simplifié)

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│ Utilisateur │       │   Produit   │       │  Commande   │
├─────────────┤       ├─────────────┤       ├─────────────┤
│ id          │       │ id          │       │ id          │
│ nom         │       │ nom         │       │ numero      │
│ email       │  1..* │ prix        │  *..1 │ montant     │
│ motDePasse  │───────│ stock       │───────│ statut      │
│ role        │       │ seuil       │       │ date        │
└─────────────┘       └─────────────┘       └─────────────┘
       │                      │                      │
       │                      │                      │
       └──────────────────────┴──────────────────────┘
                              │
                              │
                    ┌─────────────────┐
                    │ MouvementStock  │
                    ├─────────────────┤
                    │ id              │
                    │ type            │
                    │ quantite        │
                    │ date            │
                    └─────────────────┘
```
