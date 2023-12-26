/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.controllers.AuthenticationController;
import com.etiennecollin.ift2255.clientCLI.controllers.ProfileController;
import com.etiennecollin.ift2255.clientCLI.controllers.ShopController;
import com.etiennecollin.ift2255.clientCLI.controllers.TicketController;
import com.etiennecollin.ift2255.clientCLI.models.*;
import com.etiennecollin.ift2255.clientCLI.models.data.Database;
import com.etiennecollin.ift2255.clientCLI.models.data.JavaSerializedDatabase;
import com.etiennecollin.ift2255.clientCLI.views.MainMenu;
import com.etiennecollin.ift2255.clientCLI.views.ViewRenderer;

/**
 * The main class representing the UniShop application. It serves as a singleton and
 * provides access to various models, controllers, and the view renderer.
 */
public class UniShop {
    /**
     * The _instance field represents the singleton instance of the UniShop class.
     * It is marked as static to ensure a single instance exists throughout the application.
     */
    private static UniShop _instance;
    /**
     * The db field holds the reference to the Database object used by the UniShop instance.
     */
    private final Database db;
    /**
     * The renderer field is responsible for rendering Views in the UniShop instance.
     * It is used to manage the user interface of the application.
     */
    private final ViewRenderer renderer;
    /**
     * The auth field represents the AuthenticationModel used for handling user authentication in UniShop.
     */
    private final AuthenticationModel auth;
    /**
     * The profile field represents the ProfileModel used for managing user profiles in UniShop.
     */
    private final ProfileModel profile;
    /**
     * The shop field represents the ShopModel used for handling shop-related functionalities in UniShop.
     */
    private final ShopModel shop;
    /**
     * The social field represents the SocialModel used for managing social interactions in UniShop.
     */
    private final SocialModel social;
    /**
     * The ticketing field represents the TicketingModel used for handling tickets and transactions in UniShop.
     */
    private final TicketingModel ticketing;
    /**
     * The authController field represents the AuthenticationController used for controlling authentication-related logic.
     */
    private final AuthenticationController authController;
    /**
     * The profileController field represents the ProfileController used for controlling profile-related logic.
     */
    private final ProfileController profileController;
    /**
     * The shopController field represents the ShopController used for controlling shop-related logic.
     */
    private final ShopController shopController;
    /**
     * The ticketController field represents the TicketController used for controlling ticket and transaction-related logic.
     */
    private final TicketController ticketController;

    /**
     * Private constructor to initialize the UniShop instance with default models and controllers.
     */
    private UniShop() {
        this.db = new JavaSerializedDatabase();
        this.auth = new AuthenticationModel(db);
        this.profile = new ProfileModel(db);
        this.shop = new ShopModel(db);
        this.social = new SocialModel(db);
        this.ticketing = new TicketingModel(db);

        this.renderer = new ViewRenderer();

        this.authController = new AuthenticationController(renderer, auth);
        this.profileController = new ProfileController(renderer, profile, social);
        this.shopController = new ShopController(renderer, shop, profile, social);
        this.ticketController = new TicketController(renderer, ticketing);

        this.renderer.addNextView(new MainMenu(authController), true);
    }

    /**
     * Gets the instance of the UniShop singleton. If it doesn't exist, a new instance is created.
     *
     * @return The UniShop instance.
     */
    public static UniShop getInstance() {
        if (_instance == null) {
            _instance = new UniShop();
        }
        return _instance;
    }

    /**
     * Gets the view renderer associated with the UniShop instance.
     *
     * @return The view renderer.
     */
    public ViewRenderer getRenderer() {
        return renderer;
    }

    /**
     * Gets the authentication models associated with the UniShop instance.
     *
     * @return The authentication models.
     */
    public AuthenticationModel getAuth() {
        return auth;
    }

    /**
     * Gets the profile models associated with the UniShop instance.
     *
     * @return The profile models.
     */
    public ProfileModel getProfile() {
        return profile;
    }

    /**
     * Gets the authentication controller associated with the UniShop instance.
     *
     * @return The authentication controller.
     */
    public AuthenticationController getAuthController() {
        return authController;
    }

    /**
     * Gets the profile controller associated with the UniShop instance.
     *
     * @return The profile controller.
     */
    public ProfileController getProfileController() {
        return profileController;
    }

    /**
     * Gets the shop controller associated with the UniShop instance.
     *
     * @return The shop controller.
     */
    public ShopController getShopController() {
        return shopController;
    }

    /**
     * Gets the ticket controller associated with the UniShop instance.
     *
     * @return The ticket controller.
     */
    public TicketController getTicketController() {
        return ticketController;
    }

    //    public void updateCatalog() {
    //        this.catalog = new ArrayList<>();
    //        sellerList.forEach((sellerName, seller) -> this.catalog.addAll(seller.getProductsOffered()));
    //    }
    //
    //    /**
    //     * Loads user data from the specified file path.
    //     *
    //     * @param path The file path from which user data needs to be loaded.
    //     */
    //    @SuppressWarnings("unchecked")
    //    public void loadUserList(String path) {
    //        try (FileInputStream file = new FileInputStream(path)) {
    //            try (ObjectInputStream input = new ObjectInputStream(file)) {
    //                this.buyerList = (HashMap<String, Buyer>) input.readObject();
    //                this.sellerList = (HashMap<String, Seller>) input.readObject();
    //            }
    //        } catch (IOException | ClassNotFoundException e) {
    //            // throw new RuntimeException("Could not load the user list");
    //        }
    //        updateCatalog();
    //    }
    //
    //    public ArrayList<Product> getCatalog() {
    //        return catalog;
    //    }
    //
    //    /**
    //     * Saves the current state of buyer and seller lists to the specified file path.
    //     *
    //     * @param path The file path to which user data needs to be saved.
    //     */
    //    public void saveUserList(String path) {
    //        File file = new File(path);
    //        try {
    //            file.createNewFile();
    //        } catch (IOException e) {
    //            throw new RuntimeException("Could not create the save file");
    //        }
    //
    //        try (FileOutputStream outputFile = new FileOutputStream(file, false)) {
    //            try (ObjectOutputStream output = new ObjectOutputStream(outputFile)) {
    //                output.writeObject(getBuyerList());
    //                output.writeObject(getSellerList());
    //                output.flush();
    //            }
    //        } catch (IOException e) {
    //            throw new RuntimeException("Could not save the user list");
    //        }
    //    }
    //
    //    public HashMap<String, Buyer> getBuyerList() {
    //        return buyerList;
    //    }
    //
    //    public HashMap<String, Seller> getSellerList() {
    //        return sellerList;
    //    }
    //
    //    public User getCurrentUser() {
    //        return currentUser;
    //    }
    //
    //    public void setCurrentUser(User currentUser) {
    //        this.currentUser = currentUser;
    //    }
    //
    //    public void addUser(Buyer buyer) throws IllegalArgumentException {
    //        if (buyerList.containsKey(buyer.getUsername().toLowerCase())) {
    //            throw new IllegalArgumentException("This buyer already exists");
    //        }
    //        this.buyerList.put(buyer.getUsername().toLowerCase(), buyer);
    //    }
    //
    //    public void addUser(Seller seller) throws IllegalArgumentException {
    //        if (sellerList.containsKey(seller.getName().toLowerCase())) {
    //            throw new IllegalArgumentException("This seller already exists");
    //        }
    //        this.sellerList.put(seller.getName().toLowerCase(), seller);
    //        updateCatalog();
    //    }
    //
    //    public void removeUser(Buyer buyer) {
    //        if (!buyerList.containsKey(buyer.getUsername().toLowerCase())) {
    //            throw new IllegalArgumentException("This buyer does not exist");
    //        }
    //        this.buyerList.remove(buyer.getUsername().toLowerCase());
    //    }
    //
    //    public void removeUser(Seller seller) {
    //        if (!sellerList.containsKey(seller.getName().toLowerCase())) {
    //            throw new IllegalArgumentException("This seller does not exist");
    //        }
    //        this.sellerList.remove(seller.getName().toLowerCase());
    //        updateCatalog();
    //    }
    //
    //    public void loginBuyer(String username, String password) throws IllegalArgumentException {
    //        Buyer buyer = buyerList.get(username.toLowerCase());
    //
    //        // Check that a buyer is found
    //        if (buyer == null) {
    //            throw new IllegalArgumentException("Incorrect username");
    //        }
    //
    //        if (buyer.getPassword() != password.hashCode()) {
    //            throw new IllegalArgumentException("Incorrect password");
    //        }
    //
    //        this.setCurrentUser(buyer);
    //    }
    //
    //    public void loginSeller(String name, String password) throws IllegalArgumentException {
    //        Seller seller = sellerList.get(name.toLowerCase());
    //
    //        if (seller == null) {
    //            throw new IllegalArgumentException("Incorrect name");
    //        }
    //
    //        if (seller.getPassword() != password.hashCode()) {
    //            throw new IllegalArgumentException("Incorrect password");
    //        }
    //
    //        this.setCurrentUser(seller);
    //    }
    //
    //    public boolean isPasswordMatching(String password) {
    //        return this.currentUser.getPassword() == password.hashCode();
    //    }
}
