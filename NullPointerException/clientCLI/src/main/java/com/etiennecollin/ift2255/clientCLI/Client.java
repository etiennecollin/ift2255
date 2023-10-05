
/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    /**
     * The main method of the `Client` class, which is the entry point for the CLI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println(prettify("Bienvenue sur la plateforme Unishop"));

        String[] menuConnexion = {"Créer un compte", "Se connecter"};

        String[] menuAcheteur = {"Consulter le catalogue de produit", "Recherche de produit",
                "Évaluer un produit", "Consulter le panier d'achat", "Passer une commande",
                "Gérer une commande", "Confirmer la réception d'une commande",
                "Signaler un problème avec un produit", "Retourner ou échanger une commande",
                "Afficher les métriques de mes activités", "Trouver un revendeur", "se déconnecter"};

        String[] menuRevendeur = {"Offrir un produit", "Modifier l'état d'une commande",
                "Afficher les métriques de mes activités", "Gestion d'un problème",
                "Confirmer la réception d'un retour", "se déconnecter"};



        String answer = menuConnexion[prettyMenu("Menu principal", menuConnexion)];
        boolean validate = false; //Allow client to connect to principal menu

        while(!validate){
            switch(answer) {
                case "Créer un compte":
                    String choices[] = {"Acheteur", "Revendeur"};
                    String accountType = choices[prettyMenu("Type de compte", choices)];

                    if (accountType=="Acheteur")
                        buyerForm();
                    else if (accountType=="Revendeur")
                        sellerForm();

                    System.out.println(prettify("Inscription réussie"));

                    //Direct client to connexion form
                case "Se connecter": //TODO validate information
                    System.out.println(prettify("--- Se connecter: ---"));
                    do{
                        validate = connexionForm();
                        if(!validate)
                            System.out.println(prettify("Le nom d'utilisateur ou le mot de passe est invalide"));
                    }
                    while(!validate);
                    System.out.println(prettify("Connexion réussie"));
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }

        //TODO display the right menu



       /* boolean answer = prettyYesNo("Are you a nice person?");
        if (answer) {
            System.out.println(prettify("Nice"));
        } else {
            System.out.println(prettify("Bruh"));
        }*/
    }

    private static boolean connexionForm(){
        String username = prettyForm("Nom d'utilisateur: ");
        String password = prettyForm("Password: ");

        return true; //TODO implementer la vérification
    }

    private static void buyerCreationForm() { // TODO return buyer
        String firstName = prettyPrompt("Prénom");
        String lastName = prettyPrompt("Nom");
        String username = prettyPrompt("Nom d'utilisateur");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Adresse courriel");
        String phoneNumber = prettyPrompt("Numéro de téléphone");
        String address = prettyPrompt("Adresse d'expédition");
    }

    private static void sellerCreationForm() { // TODO return seller
        String firstName = prettyPrompt("Prénom");
        String lastName = prettyPrompt("Nom");
        String username = prettyPrompt("Nom d'utilisateur");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Adresse courriel");
        String phoneNumber = prettyPrompt("Numéro de téléphone");
        String product = prettyPrompt("Produit à offrir"); // TODO create a product
    }
}
