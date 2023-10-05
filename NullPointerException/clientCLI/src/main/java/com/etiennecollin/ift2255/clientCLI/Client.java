
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
        System.out.println(prettify("Welcome to UniShop"));

        String[] choices = {"Login", "Register"};
        String answer = choices[prettyMenu("Main menu", choices)];
        if (answer.equals(choices[0])) {
            connexionForm();
        } else if (answer.equals(choices[1])) {
            createAccount();
        }
    }

        String[] roleChoice = {"Buyer", "Seller"};
        String role = roleChoice[prettyMenu("Choose a menu to display", roleChoice)];

        boolean disconnect = false;
        do {
            if(role.equals(roleChoice[0])){
                int buyerAnswer = prettyMenu("Main menu", buyerMenu);
                switch(buyerAnswer){
                    case 0:
                        displayCatalog();
                        break;
                    case 1:
                        searchProduct();
                        break;
                    case 2:
                        displayCart();
                        break;
                    case 3:
                        displayActivities();
                        break;
                    case 4:
                        findSeller();
                        break;
                    case 5:
                        //displayOrders();
                    case 6:
                        disconnect = true;
                }
            }
        } while(!disconnect);
        System.out.println(prettify("You have been successfully disconnected"));
    }
    private static void loginForm() {
        System.out.println(prettify("Login menu"));
        while (true) {
            String username = prettyPrompt("Username");
            String password = prettyPrompt("Password");

            if (!true) { // TODO add user info validation
                System.out.println(prettify("The username or password is incorrect"));
                continue;
            }
            break;
        }
        System.out.println(prettify("Successfully logged in"));
    }
    private static void createAccount() {
        String[] choices = {"Buyer", "Seller"};
        String accountType = choices[prettyMenu("Select your account type", choices)];
        if (accountType.equals(choices[0])) {
            buyerCreationForm();
        } else if (accountType.equals(choices[1])) {
            sellerCreationForm();
        }

        System.out.println(prettify("Successfully registered"));
    }
    private static void buyerCreationForm() { // TODO return buyer
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        String phoneNumber = prettyPrompt("Phone number");
        String address = prettyPrompt("Shipping address");
    }
    private static void sellerCreationForm() { // TODO return seller
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        String phoneNumber = prettyPrompt("Phone number");
        String product = prettyPrompt("Offered product"); // TODO create a product with createProduct()
    }
    private static void displayCatalog(){
        String[] catChoice = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment", "Main menu"};
        int choice=0;

        while(choice!=5){ //5 is the option for Main Menu
            choice = prettyMenu("Categories", catChoice);
            //for prototype only

            int addToCart = prettyMenu("Add to cart", addToCartMenu);

            if(addToCart==(addToCartMenu.length-1)){ //back option
                continue;
            } else {
                shoppingCart.add(addToCartMenu[addToCart]);
                System.out.println(prettify("Item successfully added to cart."));
                boolean answer = prettyYesNo("Keep browsing product?");
                if(!answer)
                    choice = 5;         //5 goes back to main menu
            }
        }
    }
    private static void searchProduct(){
        ArrayList<String> searchResult = new ArrayList<>();
        String keyWord = prettyPrompt("Search");

        //search for result
        for (String item: productListDataBase) {
            int index = item.indexOf(keyWord);
            if (!(index==-1)){
                searchResult.add(item);
            }
        }
        searchResult.add("Main menu");
        //display result
        int choice = prettyMenu("Resultats for \"" + keyWord + "\"", searchResult);

        if(!(choice==searchResult.size()-1)){
            String productMenu[] = {"Like the product", "Add to cart", "Main Menu"};
            int answer = prettyMenu(searchResult.get(choice), productMenu);

            switch(answer){
                case 0:
                    likedProducts.add(searchResult.get(choice));
                    System.out.println(prettify("Item successfully liked"));
                    break;
                case 1:
                    shoppingCart.add(searchResult.get(choice));
                    System.out.println(prettify("Item successfully added to cart."));
                    break;
                case 2:
                    break;
            }
        }
    }
    //For prototype only
    private static void findSeller() {
        String[] searchBy = {"Name", "Address", "Article category"};
        int search = prettyMenu("Search by", searchBy);

        switch(search){
            case 0: //Name
                String name = prettyPrompt("Search by name");
                /*nameSearch(name);*/
                break;
            case 1: //Address
                String address = prettyPrompt("Search by address");
                /*addressSearch(address);*/
                break;
            case 2: //Article category
                String[] catChoice = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment", "Main menu"};
                int cat = prettyMenu("Categories", catChoice);
                break;
        }
        displaySellers();
    }
    public static void displaySellers(){
        for(String seller : sellersUsername)
        System.out.println(prettify(seller));
    }
    public static void displayCart(){
        System.out.println(prettify("My cart: "));
        if(shoppingCart.isEmpty())
            System.out.println(prettify("Empty cart"));
        else {
            for(String item : shoppingCart) {
                System.out.println(prettify(item));
            }
        }
        //Fictional amount for prototype only.
        System.out.println(prettify("Total: 1000$"));
        boolean placeOrder = prettyYesNo("Place an order?");
        if(placeOrder){
            orderPlaced.add(shoppingCart);
            System.out.println("Your order has been placed successfully");
        }
    }
    //General metrics
    public static void displayActivities(){
        System.out.println(prettify("My activities:"));
        System.out.println(prettify("Total orders: " + orderPlaced.size()));
        System.out.println(prettify("Total likes: " + likedProducts.size()));
    }

}

