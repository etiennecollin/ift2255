
/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.classes.Buyer;
import com.etiennecollin.ift2255.clientCLI.classes.Seller;
import com.etiennecollin.ift2255.clientCLI.classes.UniShop;
import com.etiennecollin.ift2255.clientCLI.classes.User;
import com.etiennecollin.ift2255.clientCLI.classes.products.ProductCategory;

import java.util.ArrayList;
import java.util.Map;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    public static final String savePath = "~/.unishop.data";
    // Hardcoded for prototype
    static String[] sellerMenu = {"Offer product", "Modify order status", "Manage issues", "Update account information", "Log out"};
    static String[] buyerMenu = {"Catalog", "Search a product", "My cart", "My activities", "Find a seller", "My orders", "Update account information", "Log out"};
    static ArrayList<String> shoppingCart = new ArrayList<>();
    static ArrayList<String> likedProducts = new ArrayList<>();
    static String[] categories = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment"};
    static String[] productListDataBase = {"Computer", "Manual", "Utilities"};
    static String[] addToCartMenu = new String[productListDataBase.length + 1];
    static ArrayList<String> sellersUsername = new ArrayList<>();
    static ArrayList<ArrayList<String>> ordersPlaced = new ArrayList<>();

    /**
     * The main method of the `Client` class, which is the entry point for the CLI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {

        /* String[] buyerMenu = {"Product catalog", "Search product", "Review a product", "Cart", "Order", "Review previous orders", "Confirm order reception", "Signal product issue", "Return/Exchange", "My activities", "Find seller", "Disconnect"};*/
        // for prototype
        sellersUsername.add("sellerUsernameExample");

        // Make productListDataBase = addToCartMenu
        System.arraycopy(productListDataBase, 0, addToCartMenu, 0, productListDataBase.length);

        // last elm = back menu
        addToCartMenu[addToCartMenu.length - 1] = "Back";

        System.out.println(prettify("For this prototype, here are the users available for login:"));
        System.out.println(prettify("Buyer: username=buyer, password=hunter2"));
        System.out.println(prettify("Seller: username=seller, password=1234"));


        UniShop unishop = new UniShop();
        while (true) {
            String[] loginMenu = {"Login", "Register", "Quit"};
            int answer = prettyMenu("Welcome to UniShop", loginMenu);

            if (answer == 0) {
                unishop = loginForm(unishop);
            } else if (answer == 1) {
                unishop = createAccount(unishop);
            } else {
                quit(unishop);
                break;
            }

            User user = unishop.getCurrentUser();

            if (user instanceof Buyer) {
                buyerMenu();
            } else if (user instanceof Seller) {
                sellerMenu();
            }
        }
    }

    private static UniShop loginForm(UniShop unishop) {
        //UserRole ur = null;
        //UniShop unishop = new UniShop();

        System.out.println(prettify("Login menu"));

        String[] roleMenu = {"Buyer", "Seller"};
        int answer = prettyMenu("Login as a: ", roleMenu);

        String username = prettyPrompt("Username");
        String password = prettyPrompt("Password");

        if(answer == 0) {
           // ur = UserRole.Buyer;
            unishop.loginBuyer(username, password);
        }
        else if(answer == 1){
           // ur = UserRole.Seller;
            unishop.loginSeller(username, password);
        }
        return unishop;
    }

    private static UniShop createAccount(UniShop unishop) {
        UserRole role = prettyMenu("What type of account would you like to create?", UserRole.class);
        User user = null;

        if (role == UserRole.Buyer) {
            user = buyerCreationForm();
            System.out.println("hello:)");
            unishop.addUser((Buyer) user);
            System.out.println("Hello again :) ");
        } else if (role == UserRole.Seller) {
            user = sellerCreationForm();
            unishop.addUser((Seller) user);
        }

        unishop.setCurrentUser(user);
        return unishop;
    }

    public static void buyerMenu() {
        boolean disconnect = false;
        do {
            int buyerAnswer = prettyMenu("Main menu", buyerMenu);
            switch (buyerAnswer) {
                case 0 -> displayCatalog();
                case 1 -> searchProduct();
                case 2 -> displayCart();
                case 3 -> displayActivities();
                case 4 -> findSeller();
                case 5 -> displayOrders();
                case 6 -> updateBuyerInfo();
                case 7 -> disconnect = true;
            }
        } while (!disconnect);
        System.out.println(prettify("You have successfully logged out"));
    }

    public static void sellerMenu() {
        boolean disconnect = false;

        do {
            int answer = prettyMenu("Main menu", sellerMenu);
            switch (answer) {
                case 0 -> addProduct();
                case 1 -> changeOrderStatus();
                case 2 -> manageProblems();
                case 3 -> updateSellerInfo();
                case 4 -> disconnect = true;
            }
        } while (!disconnect);

        System.out.println(prettify("You have successfully logged out"));
    }

    private static Buyer buyerCreationForm() { // TODO return buyer
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String userName = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        int phone = Integer.parseInt(prettyPrompt("Phone number"));
        String address = prettyPrompt("Shipping address");

        Buyer buyer = new Buyer(lastName, firstName, userName, password, phone, email, address);
        System.out.println(prettify("Successfully registered"));

        return buyer;
    }

    private static Seller sellerCreationForm() { // TODO return seller
        String firstName = prettyPrompt("First name");
        String lastName = prettyPrompt("Last name");
        String userName = prettyPrompt("Username");
        String password = prettyPrompt("Password");
        String email = prettyPrompt("Email");
        int phone = Integer.parseInt(prettyPrompt("Phone number"));
        String address = prettyPrompt("Shipping address");

        Seller seller = new Seller(lastName, firstName, userName, email, phone, address, password);

        System.out.println(prettify("Successfully registered"));

        return seller;
    }

    private static void displayCatalog() {
        String[] catChoice = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment", "Main menu"};
        while (true) { // 5 is the option for Main Menu
            // for prototype only
            int choice = prettyMenu("Categories", catChoice);
            if (choice == catChoice.length - 1) break;

            int addToCart = prettyMenu("Add to cart", addToCartMenu);

            if (addToCart != (addToCartMenu.length - 1)) {
                shoppingCart.add(addToCartMenu[addToCart]);
                System.out.println(prettify("Item successfully added to cart."));
                boolean answer = prettyYesNo("Keep browsing product?");
                if (!answer) break;         // 5 goes back to main menu
            }
        }
    }

    private static void searchProduct() {
        ArrayList<String> searchResult = new ArrayList<>();
        String keyWord = prettyPrompt("Search");

        // search for result
        for (String item : productListDataBase) {
            int index = item.indexOf(keyWord);
            if (!(index == -1)) {
                searchResult.add(item);
            }
        }
        searchResult.add("Main menu");
        // display result
        int choice = prettyMenu("Resultats for \"" + keyWord + "\"", searchResult);

        if (!(choice == searchResult.size() - 1)) {
            String[] productMenu = {"Like the product", "Add to cart", "Main Menu"};
            int answer = prettyMenu(searchResult.get(choice), productMenu);

            switch (answer) {
                case 0 -> {
                    likedProducts.add(searchResult.get(choice));
                    System.out.println(prettify("Item successfully liked"));
                }
                case 1 -> {
                    shoppingCart.add(searchResult.get(choice));
                    System.out.println(prettify("Item successfully added to cart."));
                }
            }
        }
    }

    public static void displayCart() {
        System.out.println(prettify("My cart: "));
        if (shoppingCart.isEmpty()) {
            System.out.println(prettify("Empty cart"));
        } else {
            for (String item : shoppingCart) {
                System.out.println(prettify(item));
            }
        }
        // Fictional amount for prototype only.
        System.out.println(prettify("Total: 1000$"));
        boolean placeOrder = prettyYesNo("Place an order?");
        if (placeOrder) {
            ordersPlaced.add(shoppingCart);
            System.out.println("Your order has been placed successfully");
        }
    }

    // General metrics
    public static void displayActivities() {
        System.out.println(prettify("My activities:"));
        System.out.println(prettify("Total orders: " + ordersPlaced.size()));
        System.out.println(prettify("Total likes: " + likedProducts.size()));
    }

    // For prototype only
    private static void findSeller() {
        String[] searchBy = {"Name", "Address", "Article category"};
        int search = prettyMenu("Search by", searchBy);

        switch (search) {
            case 0 -> { // Name
                String name = prettyPrompt("Search by name");
                /*nameSearch(name);*/
            }
            case 1 -> { // Address
                String address = prettyPrompt("Search by address");
                /*addressSearch(address);*/
            }
            case 2 -> { // Article category
                String[] catChoice = {"Books and manuals", "Learning ressources", "Stationery", "Hardware", "Office equipment", "Main menu"};
                int cat = prettyMenu("Categories", catChoice);
            }
        }
        displaySellers();
    }

    public static void displayOrders() {
        System.out.println(prettify("Your orders: "));
        for (int i = 0; i < ordersPlaced.size(); i++) {
            System.out.println(prettify("Order " + (i + 1) + ": "));
            for (int j = 0; j < ordersPlaced.get(i).size(); j++) {
                System.out.println(prettify(ordersPlaced.get(i).get(j)));
            }
        }

        int answer = prettyMenu("Order menu", new String[]{"Confirm an order", "Signal an issue", "Back"});
        switch (answer) {
            case 0 -> {
                if (ordersPlaced.isEmpty()) {
                    System.out.println("No placed orders");
                } else {
                    int orderToConfirm = Utils.prettyMenu("What order do you want to confirm?", ordersPlaced, "Order");
                    System.out.println("Order " + orderToConfirm + " is successfully confirmed");
                }
            }
            case 1 -> {
                if (ordersPlaced.isEmpty()) {
                    System.out.println("No placed orders");
                } else {
                    int orderToSignal = Utils.prettyMenu("What order do you want to signal?", ordersPlaced, "Order");
                    System.out.println("Order " + orderToSignal + " is successfully signaled");
                }
            }
        }
    }

    public static void updateBuyerInfo() {
        String[] updateInfoMenu = new String[]{"First name", "Last name", "Password", "Email", "Phone number", "Shipping address", "Main menu"};
        while (true) {
            int menuIdx = prettyMenu("Select the information you'd like to change", updateInfoMenu);

            switch (menuIdx) {
                case 0 -> prettyPrompt("Set a new first name");
                case 1 -> prettyPrompt("Set a new last name");
                case 2 -> prettyPrompt("Set a new password");
                case 3 -> prettyPrompt("Set a new email address");
                case 4 -> prettyPrompt("Set a new phone number");
                case 5 -> prettyPrompt("Set a new shipping address");
                case 6 -> {
                    return;
                }
            }
        }
    }

    public static void addProduct() {
        String title = prettyPrompt("Title");
        ProductCategory productCategory = prettyMenu("Category", ProductCategory.class);
        String description = prettyPrompt("Description");
        String brandName = prettyPrompt("Brand name");
        String modelName = prettyPrompt("Model name");
        int quantity = prettyPromptInt("Quantity");
        float price = prettyPromptCurrency("Price");

        System.out.println("Product " + title + " added!");
    }

    public static void changeOrderStatus() {
        String[] orderMenu = new String[]{"#123 - Pending Seller", "Main menu"};
        int orderIdx = prettyMenu("Select the order to update", orderMenu);
        if (orderIdx == orderMenu.length - 1) { // last index returns to main menu
            return;
        }

        String[] orderStatusMenu = new String[]{"In transit", "Main menu"};
        int statusIdx = prettyMenu("Select new status", orderStatusMenu);
        if (statusIdx == orderStatusMenu.length - 1) { // last index returns to main menu
            return;
        }

        String shippingCompany = prettyPromptValidated("Shipping company", (s) -> !s.isEmpty());
        String trackingId = prettyPromptValidated("Tracking ID", (s) -> !s.isEmpty());

        System.out.println("Order status updated!");
    }

    public static void manageProblems() {
        String[] problemList = new String[]{"#123 - Order not received", "Main menu"};

        int problemIdx = prettyMenu("Select a problem", problemList);
        if (problemIdx == problemList.length - 1) {
            return;
        }

        System.out.println("#123 - Order not received");
        System.out.println("I put in an order 5 minutes ago and I still have not received it!!!");

        String[] solutionTypes = new String[]{"Refund", "Replace", "Repair"};
        int solution = prettyMenu("Select a solution", solutionTypes);
        String description = prettyPrompt("Provide solution details to the buyer");
        // set new problem status

        System.out.println("Problem response sent!");
    }

    public static void updateSellerInfo() {
        String[] updateInfoMenu = new String[]{"Password", "Email", "Phone number", "Shipping address", "Main menu"};
        while (true) {
            int menuIdx = prettyMenu("Select the information you'd like to change", updateInfoMenu);

            switch (menuIdx) {
                case 0 -> prettyPrompt("Set a new password");
                case 1 -> prettyPrompt("Set a new email address");
                case 2 -> prettyPrompt("Set a new phone number");
                case 3 -> prettyPrompt("Set a new shipping address");
                case 4 -> {
                    return;
                }
            }
        }
    }

    public static void displaySellers() {
        for (String seller : sellersUsername) {
            System.out.println(prettify(seller));
        }
    }

    /**
     * Temporary for prototype.
     */
    public enum UserRole {
        Buyer, Seller
    }
}



