# IFT2255 - UniShop <!-- omit from toc -->

_Par l'équipe NullPointerException._

---

## Table des matières

- [Table des matières](#table-des-matières)
- [Description du projet](#description-du-projet)
- [Données de départ](#données-de-départ)
- [Installation](#installation)
- [Exécution](#exécution)

## Description du projet

UniShop est une plateforme de commerce en ligne conçue pour simplifier l'achat
de fournitures scolaires, de matériel informatique, de manuels et de ressources
d'apprentissage. L'application offre une interface conviviale aux acheteurs et
aux revendeurs, facilitant la recherche de produits et la gestion des
transactions.

## Données de départ

Acheteurs :

-   Pseudo: acheteur1
    -   Mot de passe: password1
    -   Commandes passées: 5
        -   États des commandes: Delivered, Cancelled, In Transit, In Production
    -   Tickets:
        -   Ticket normal où le revendeur à proposé de remplacer les produits
            -   État: Return In Transit
            -   Commande de remplacement: Une commande en état PendingTicket (sera changé à EnProduction une fois le retour est reçu par le revendeur)
    -   Reviews: 2 évaluations de produits du revendeur1 dont une likée par acheteur2
    -   Suiveur de: acheteur3
-   Pseudo: acheteur2
    -   Mot de passe: password2
    -   Commandes passées: 5
        -   États des commandes: Livré, En livraison
    -   Tickets:
        -   Demande de retour en moins de 30 jours
            -   État: Closed
        -   Demande d'échange en moins de 30 jours
            -   État: Replacement In Transit
            -   Commande de remplacement: Une commande en état In Transit
-   Pseudo: acheteur3
    -   Mot de passe: password3
-   Pseudo: acheteur4
    -   Mot de passe: password4
-   Pseudo: acheteur5
    -   Mot de passe: password5
-   Pseudo: acheteur6
    -   Mot de passe: password6
-   Pseudo: acheteur7
    -   Mot de passe: password7
-   Pseudo: acheteur8
    -   Mot de passe: password8
-   Pseudo: acheteur9
    -   Mot de passe: password9
-   Pseudo: acheteur10
    -   Mot de passe: password10

Revendeurs :

-   Nom: revendeur1
    -   Mot de passe: password1
    -   Produits: 5 de BookOrManual
    -   Tickets: un ticket de acheteur2 - Closed
    -   Likes: Un produit liké 6 fois et un autre liké une fois
-   Nom: revendeur2
    -   Mot de passe: password2
    -   Produits: 4 OfficeEquipment
    -   Tickets: Un ticket de acheteur2 - Replacement In Transit
-   Nom: revendeur3
    -   Mot de passe: password3
    -   Produits: 5 IT
    -   Tickets: Un ticket de acheteur1
-   Nom: revendeur4
    -   Mot de passe: password4
    -   Produits: 4 StationeryArticle
-   Nom: revendeur5
    -   Mot de passe: password5
    -   Produits: 2 LearningResource

Notez: 3 produits sont en promotion

## Installation

1. Assurez-vous d'avoir une version à jour de Java.
    - Le projet a été testé avec `openjdk 21.0.1 2023-10-17`
2. Assurez-vous d'avoir Maven installé sur votre système.
    - Le projet a été testé avec `Apache Maven 3.9.6`
3. Téléchargez le projet depuis le dépôt GitHub.
    - `git clone https://github.com/etiennecollin/ift2255`
4. Naviguez vers le répertoire du projet dans le terminal.
    - `cd <...>/ift2255/NullPointerException/clientCLI`
5. Exécutez la commande `mvn clean install` pour compiler et construire le projet.
6. Afin d'utiliser les données de départ, veuillez extraire le zip `default-database.zip` et placer tous les fichiers `.txt` dans le même dossier que le `.jar` généré.

## Exécution

Après l'installation, exécutez le fichier JAR généré à partir du point
d'entrée principal.

```bash
> pwd
<...>/ift2255/NullPointerException/clientCLI
> java -jar target/clientCLI-<VERSION>.jar
```
