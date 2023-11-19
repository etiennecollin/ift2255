/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.classes.*;
import com.etiennecollin.ift2255.clientCLI.classes.products.*;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static com.etiennecollin.ift2255.clientCLI.Utils.*;

/**
 * The `Client` class serves as the main entry point for the client command-line interface (CLI).
 */
public class Client {
    public static final String savePath;
    public static final UniShop unishop = new UniShop();
    // Hardcoded for prototype
    static String[] sellerMenu = {"Offer product", "Modify order status", "Manage issues", "Update account information", "Log out"};
    static ArrayList<String> shoppingCart = new ArrayList<>();
    static ArrayList<String> likedProducts = new ArrayList<>();
    static String[] productListDataBase = {"Computer", "Manual", "Utilities"};
    static ArrayList<String> sellersUsername = new ArrayList<>();
    static ArrayList<ArrayList<String>> ordersPlaced = new ArrayList<>();

    static {
        try {
            // Inspired by https://stackoverflow.com/a/3627527
            savePath = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile() + "/unishop_save.txt";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The main method of the `Client` class, which is the entry point for the CLI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        unishop.loadUserList(savePath);

        while (true) {
            String[] loginMenu = {"Login", "Register", "Quit"};
            int answer = prettyMenu("Welcome to UniShop", loginMenu);

            if (answer == 0) {
                loginForm();
            } else if (answer == 1) {
                createAccount();
            } else if (answer == 2) {
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

    private static void loginForm() {
        while (true) {
            clearConsole();
            System.out.println(prettify("Login menu"));
            String[] options = {"Buyer", "Seller"};
            int answer = prettyMenu("Login as", options);

            try {
                if (answer == 0) {
                    String username = prettyPrompt("Username");
                    String password = prettyPrompt("Password");
                    unishop.loginBuyer(username, password);
                } else if (answer == 1) {
                    String username = prettyPrompt("Name");
                    String password = prettyPrompt("Password");
                    unishop.loginSeller(username, password);
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }
        clearConsole();
    }

    private static void createAccount() {
        while (true) {
            clearConsole();
            System.out.println(prettify("Account creation menu"));
            String[] options = {"Buyer", "Seller"};
            int answer = prettyMenu("What type of account would you like to create?", options);
            User user = null;

            try {
                if (answer == 0) {
                    user = buyerCreationForm();
                    if (user == null) break;
                    unishop.addUser((Buyer) user);
                } else if (answer == 1) {
                    user = sellerCreationForm();
                    if (user == null) break;
                    unishop.addUser((Seller) user);
                }

                unishop.setCurrentUser(user);
                System.out.println(prettify("Successfully created account"));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }
        clearConsole();
    }

    // TODO complete
    public static void buyerMenu() {
        String[] buyerMenu = {"Catalog", "Search a product", "My cart", "My activities", "Find user", "My orders", "Update account information", "Log out"};

        loop:
        while (true) {
            int buyerAnswer = prettyMenu("Main menu", buyerMenu);
            switch (buyerAnswer) {
                case 0 -> displayCatalog();
                case 1 -> searchProduct();
                case 2 -> displayCart();
                case 3 -> displayActivities();
                case 4 -> findUser();
                case 5 -> displayOrders();
                case 6 -> updateBuyerInfo();
                case 7 -> {
                    logout(unishop);
                    break loop;
                }
            }
        }
    }

    // TODO complete
    public static void sellerMenu() {
        loop:
        while (true) {
            int answer = prettyMenu("Main menu", sellerMenu);
            switch (answer) {
                case 0 -> addProduct();
                case 1 -> changeOrderStatus();
                case 2 -> manageProblems();
                case 3 -> updateSellerInfo();
                case 4 -> {
                    logout(unishop);
                    break loop;
                }
            }
        }
    }

    private static Buyer buyerCreationForm() {
        clearConsole();

        while (true) {

            try {
                String firstName = (prettyPrompt("First name"));
                validateName(firstName);
                String lastName = (prettyPrompt("Last name"));
                validateName(lastName);
                String username = prettyPrompt("Username");
                String email = prettyPrompt("Email");
                validateEmail(email);
                String password = prettyPrompt("Password");
                String phoneNumber = prettyPrompt("Phone number");
                validatePhone(phoneNumber);
                String address = prettyPrompt("Shipping address");

                return new Buyer(firstName, lastName, username, email, phoneNumber, address, password);
            } catch (RuntimeException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }

        return null;
    }

    private static Seller sellerCreationForm() {
        clearConsole();

        while (true) {
            try {
                String name = prettyPrompt("Name");
                validateName(name);
                String email = prettyPrompt("Email");
                validateEmail(email);
                String password = prettyPrompt("Password");
                String phoneNumber = prettyPrompt("Phone number");
                validatePhone(phoneNumber);
                String address = prettyPrompt("Shipping address");

                return new Seller(name, email, phoneNumber, address, password);
            } catch (RuntimeException e) {
                System.out.println(prettify(e.getMessage()));
                boolean tryAgain = prettyPromptBool("Try again?");
                if (!tryAgain) break;
            }
        }

        return null;
    }

    private static void displayCatalog() {
        ArrayList<String> options = ProductCategory.getOptions();
        options.add("Main menu");

        Buyer buyer = (Buyer) unishop.getCurrentUser();
        while (true) {
            // Select category
            int choice = prettyMenu("Categories", options);
            if (choice == options.size() - 1) break;
            ProductCategory selectedCategory = ProductCategory.values()[choice];

            // Select subcategory
            ArrayList<String> subOptions = selectedCategory.getSubOptions();
            subOptions.add("Main menu");

            int subChoice = prettyMenu("Sub-Categories", subOptions);
            if (subChoice == subOptions.size() - 1) break;
            Enum<?> selectedSubCategory = selectedCategory.getEnum().getEnumConstants()[subChoice];

            // Get products that match category/subcategory

            ArrayList<Product> matchedProducts = new ArrayList<>();
            ArrayList<String> matchedProductsString = new ArrayList<>();
            matchedProductsString.add("Back to categories");
            matchedProductsString.add("Back to main menu");

            for (Product product : unishop.getCatalog()) {
                if (product.getCategory().equals(selectedCategory) && product.getSubCategory().equals(selectedSubCategory)) {
                    matchedProducts.add(product);
                    matchedProductsString.add(product.getTitle());
                }
            }

            int answer = prettyMenu("Select a product", matchedProductsString);

            // Check if we go back
            if (answer == 0) {
                continue;
            } else if (answer == 1) {
                break;
            }

            // Get product
            Product product = matchedProducts.get(answer);
            displayProduct(product);
            displayBuyerProductActions(product);

            boolean tryAgain = prettyPromptBool("Keep browsing product?");
            if (!tryAgain) break;
        }
    }

    // TODO
    private static void searchProduct() {
        ArrayList<String> searchResult = new ArrayList<>();
        String keyWord = prettyPrompt("Search");
    }

    // TODO
    public static void displayCart() {
        while (true) {
            clearConsole();
            System.out.println(prettify("My cart: "));
            Cart cart = ((Buyer) unishop.getCurrentUser()).getCart();
            if (cart.getProducts().isEmpty()) {
                System.out.println(prettify("Empty cart"));
            } else {
                for (Tuple<Product, Integer> tuple : cart.getProducts()) {
                    Product product = tuple.first;
                    int quantity = tuple.second;
                    int cost = product.getCost() * quantity;

                    System.out.println(prettify(product.getTitle() + " x" + quantity + " | Cost: " + product.getFormattedCost()));
                }
            }
            System.out.println(prettify("Total: " + cart.getFormattedCost() + "$"));

            String[] options = {"Main menu", "Remove product", "Place order", "Empty cart"};
            int answer = prettyMenu("Cart menu", options);
            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println(prettify("Empty cart"));
                    } else {
                        ArrayList<Product> products = new ArrayList<>();
                        ArrayList<String> productsString = new ArrayList<>();

                        for (Tuple<Product, Integer> tuple : cart.getProducts()) {
                            Product product = tuple.first;
                            int quantity = tuple.second;
                            products.add(product);
                            productsString.add(product.getTitle() + " x" + quantity + " | Cost: " + product.getFormattedCost());
                        }

                        int productToRemove = prettyMenu("What product do you want to remove?", productsString);
                        Product product = products.get(productToRemove);

                        while (true) {
                            int removeHowMany = prettyPromptInt("How many do you want to remove? (-1 to remove all)");
                            if (removeHowMany == -1 || removeHowMany >= cart.getProducts().get(productToRemove).second) {
                                cart.removeProduct(product);
                            } else if (removeHowMany > 0) {
                                cart.subtractProduct(product, removeHowMany);
                            } else {
                                System.out.println(prettify("Invalid quantity"));

                                boolean tryAgain = prettyPromptBool("Try again?");
                                if (tryAgain) continue;
                            }
                            System.out.println(prettify("Product successfully removed"));
                            break;
                        }
                    }
                }
                case 2 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println(prettify("Empty cart"));
                    } else {
                        placeOrder();
                    }
                }
                case 3 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println(prettify("Empty cart"));
                    } else {
                        cart.emptyCart();
                        System.out.println(prettify("Cart successfully emptied"));
                    }
                }
            }
        }
    }

    // General metrics
    public static void displayActivities() {
        User currentUser = unishop.getCurrentUser();
        int nMonths = prettyPromptInt("Display activities for the last how many months");

        System.out.println(prettify("My activities:"));
        if (currentUser instanceof Buyer) {
            BuyerMetrics metrics = ((Buyer) currentUser).getMetrics(nMonths);
            // TODO complete this
        } else if (currentUser instanceof Seller) {
            SellerMetrics metrics = ((Seller) currentUser).getMetrics(nMonths);
            // TODO complete this
        }
    }

    public static void findUser() {
        String[] options = {"Main menu", "Buyer", "Seller"};
        loop:
        while (true) {
            int answer = prettyMenu("Search for", options);
            switch (answer) {
                case 0 -> {
                    break loop;
                }
                case 1 -> findBuyer();
                case 2 -> findSeller();
            }
        }
    }

    // TODO
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

    // TODO
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
        Product product = null;

        String title = prettyPrompt("Title");
        ProductCategory productCategory = prettyMenu("Category", ProductCategory.class);
        String description = prettyPrompt("Description");
        int price = prettyPromptCurrency("Price");
        int fidelityPoints = prettyPromptInt("Fidelity points", bonusPoints -> validateBonusFidelityPoints(bonusPoints, price));
        int quantity = prettyPromptInt("Quantity", amount -> validateNumberRange(amount, 0, Integer.MAX_VALUE));

        switch (productCategory) {
            case BookOrManual -> {
                String author = prettyPrompt("Author");
                String editor = prettyPrompt("Publisher");
                LocalDate releaseDate = prettyPromptDate("Release date");
                BookOrManualGenre genre = prettyMenu("Genre", BookOrManualGenre.class);
                int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
                int volume = prettyPromptInt("Volume number (enter 0 if not applicable)");
                int isbn = prettyPromptInt("ISBN");
                product = new BookOrManual(price, quantity, title, description, fidelityPoints, isbn, author, editor, genre, releaseDate, edition, volume);
            }
            case IT -> {
                String brand = prettyPrompt("Brand name");
                String model = prettyPrompt("Model name");
                LocalDate releaseDate = prettyPromptDate("Release date");
                ITCategory itCategory = prettyMenu("Sub-category", ITCategory.class);
                product = new IT(price, quantity, title, description, fidelityPoints, brand, model, releaseDate, itCategory);
            }
            case LearningResource -> {
                String org = prettyPrompt("Organization");
                LocalDate releaseDate = prettyPromptDate("Release date");
                LearningResourceType type = prettyMenu("Sub-category", LearningResourceType.class);
                int edition = prettyPromptInt("Edition number (enter 0 if not applicable)");
                int isbn = prettyPromptInt("ISBN");
                product = new LearningResource(price, quantity, title, description, fidelityPoints, isbn, org, releaseDate, type, edition);
            }
            case OfficeEquipment -> {
                String brand = prettyPrompt("Brand name");
                String model = prettyPrompt("Model name");
                OfficeEquipmentCategory oeCategory = prettyMenu("Sub-category", OfficeEquipmentCategory.class);
                product = new OfficeEquipment(price, quantity, title, description, fidelityPoints, brand, model, oeCategory);
            }
            case StationeryArticle -> {
                String brand = prettyPrompt("Brand name");
                String model = prettyPrompt("Model name");
                StationeryArticleCategory saCategory = prettyMenu("Sub-category", StationeryArticleCategory.class);
                product = new StationeryArticle(price, quantity, title, description, fidelityPoints, brand, model, saCategory);
            }
        }

        if (prettyPromptBool("Save product?")) {
            ((Seller) unishop.getCurrentUser()).addProductOffered(product);
            unishop.updateCatalog();
            System.out.println("Product " + title + " added!");
        }
        else {
            System.out.println("Cancelled adding a new product.");
        }
    }

    // TODO
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

        String shippingCompany = prettyPrompt("Shipping company", Utils::validateNotEmpty);
        String trackingId = prettyPrompt("Tracking ID", Utils::validateNotEmpty);

        System.out.println("Order status updated!");
    }

    // TODO
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

    // TODO
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

    public static void displayProduct(Product product) {
        System.out.println(prettify("Title: " + product.getTitle()));
        System.out.println(prettify("Description: ") + product.getDescription());
        System.out.println(prettify("Category: ") + product.getCategory());
        System.out.println(prettify("Subcategory: ") + product.getSubCategory());
        System.out.println(prettify("Price : ") + product.getCost() / 100 + "." + product.getCost() % 100 + "$");
        System.out.println(prettify("Discount: ") + product.getDiscount() + "%");
        System.out.println(prettify("Quantity: ") + product.getQuantity());
        System.out.println(prettify("Fidelity Points: ") + product.getBonusFidelityPoints());
        System.out.println(prettify("Sold by: ") + product.getSeller().getName());
        System.out.println(prettify("Likes: ") + product.getLikes());
        System.out.println(prettify("Commercialization date: ") + product.getCommercializationDate());
    }

    private static void displayBuyerProductActions(Product product) {
        String[] options = {"Go back", "Toggle like", "Display reviews", "Add to cart"};
        while (true) {
            int answer = prettyMenu("Select action", options);
            switch (answer) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    Buyer buyer = (Buyer) unishop.getCurrentUser();
                    boolean wasLiked = buyer.toggleLike(product);

                    if (wasLiked) {
                        System.out.println(prettify("Product successfully liked"));
                    } else {
                        System.out.println(prettify("Product successfully unliked"));
                    }
                }
                case 2 -> {
                    displayReviews(product);
                }
                case 3 -> {
                    int qty = prettyPromptInt("Quantity");
                    if (qty > product.getQuantity()) {
                        System.out.println(prettify("Insufficient product quantity in inventory"));
                    } else {
                        ((Buyer) unishop.getCurrentUser()).getCart().addProduct(product, qty);
                        System.out.println(prettify("Product successfully added to cart"));
                    }
                }
            }

            boolean tryAgain = prettyPromptBool("New action?");
            if (!tryAgain) break;
        }
    }

    private static void placeOrder() {
        Buyer buyer = (Buyer) unishop.getCurrentUser();
        Cart cart = buyer.getCart();

        System.out.println(prettify("Payement form"));
        String shippingAddress = prettyPrompt("Shipping address");

        int fidelityPointsUsed = 0;
        if (buyer.getFidelityPoints() > 0) {
            boolean doUsePoints = prettyPromptBool("You have " + buyer.getFidelityPoints() + " fidelity points. Do you want to use them?");
            if (doUsePoints) {
                int availablePointMoney = buyer.getFidelityPoints() * 2; // In cents
                if (availablePointMoney > cart.getCost()) {
                    fidelityPointsUsed = cart.getCost() / 2;
                } else {
                    fidelityPointsUsed = buyer.getFidelityPoints();
                }

                int toPayWithMoney = cart.getCost() - fidelityPointsUsed * 2;
                System.out.println(prettify("Remaining to pay: " + toPayWithMoney / 100 + "." + toPayWithMoney % 100 + "$"));
            }
        }

        String creditCardName = prettyPrompt("Credit card name");

        String creditCardNumber = "";
        while (true) {
            creditCardNumber = prettyPrompt("Credit card number");
            if (creditCardNumber.length() == 16 && creditCardNumber.matches("\\d+")) {
                break;
            } else {
                System.out.println(prettify("Invalid credit card number"));
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        YearMonth expirationDate = null;
        while (true) {
            try {
                expirationDate = YearMonth.parse(prettyPrompt("Expiration date MMYY"), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(prettify("Invalid expiration date"));
            }
        }

        String cvc = "";
        while (true) {
            cvc = prettyPrompt("CVC");
            if (cvc.length() == 3 && cvc.matches("\\d+")) {
                break;
            } else {
                System.out.println(prettify("Invalid CVC"));
            }
        }

        // Confirm whethre or not to place order using prettyAskBool

        boolean doOrder = prettyPromptBool("Do you want to place the order?");

        if (!doOrder) {
            System.out.println("Cancelled order.");
            return;
        }

        buyer.getCart().createOrder(buyer.getEmail(), buyer.getPhoneNumber(), shippingAddress, buyer.getAddress(), creditCardName, creditCardNumber, expirationDate, cvc, fidelityPointsUsed);

        System.out.println(prettify("Your order has been placed successfully"));
    }

    // TODO complete
    private static void findBuyer() {
    }

    private static void findSeller() {
        loop:
        while (true) {
            String[] searchBy = {"Name", "Address", "Phone number", "email", "Go Back"};
            int search = prettyMenu("Search seller by", searchBy);

            ArrayList<Seller> matchList = new ArrayList<>();
            ArrayList<String> matchListString = new ArrayList<>();
            switch (search) {
                case 0 -> {
                    String name = prettyPrompt("Name").toLowerCase();
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getName().toLowerCase().contains(name)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 1 -> {
                    String address = prettyPrompt("Address").toLowerCase();
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getAddress().toLowerCase().contains(address)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 2 -> {
                    String phoneNumber = prettyPrompt("Phone number");
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getPhoneNumber().contains(phoneNumber)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 3 -> {
                    String email = prettyPrompt("email").toLowerCase();
                    for (Seller seller : unishop.getSellerList().values()) {
                        if (seller.getEmail().contains(email)) {
                            matchList.add(seller);
                            matchListString.add(seller.getName());
                        }
                    }
                }
                case 4 -> {
                    break loop;
                }
            }

            while (true) {
                matchListString.add("Go back");
                int index = prettyMenu("Select seller", matchListString);
                if (index == matchListString.size() - 1) break;

                Seller seller = matchList.get(index);
                displaySeller(seller);
            }
        }
    }

    private static void displayReviews(Product product) {
        ArrayList<Review> reviews = product.getReviews();
        if (reviews.isEmpty()) {
            System.out.println(prettify("No reviews for this product"));
        } else if (reviews.size() <= 3) {
            // Print reviews in batches of 3
            for (int i = 0; i < reviews.size(); i += 3) {
                clearConsole();
                System.out.println(prettify("Reviews " + i + " to " + (i + 3) + ":"));
                for (int j = i; j < i + 3; j++) {
                    if (j >= reviews.size()) break;
                    Review review = reviews.get(j);
                    Buyer author = review.getAuthor();
                    System.out.println(prettify("--------------------"));
                    System.out.println(prettify(author.getFirstName() + " " + author.getLastName() + " - " + review.getRating() + "/100"));
                    System.out.println(prettify("Title: " + review.getTitle()));
                    System.out.println(prettify(review.getContent()));
                }
                boolean tryAgain = prettyPromptBool("See more reviews?");
                if (!tryAgain) break;
            }
        }
    }

    // TODO
    public static void displaySeller(Seller seller) {
        String[] options = {"test"};
        int index = prettyMenu("Seller " + seller.getName(), options);
    }
}
