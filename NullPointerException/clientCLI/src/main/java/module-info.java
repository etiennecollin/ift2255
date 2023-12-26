/**
 * This module, named clientCLI, provides classes and packages for a command-line interface (CLI) client application.
 * It includes controllers, models, views, and display-related functionality for managing an online shopping system.
 *
 * <p>The module exports several packages to allow other modules to access the functionality it provides.
 * The exported packages are:
 * <ul>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI}</li>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI.controllers}</li>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI.models}</li>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI.models.data}</li>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI.models.data.products}</li>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI.views}</li>
 *     <li>{@code com.etiennecollin.ift2255.clientCLI.views.productDisplay}</li>
 * </ul>
 * <p>
 * The classes in this module facilitate user authentication, user profiles, product management, and ticketing.
 * It is designed to be part of a larger system for an online shopping platform.
 */
module clientCLI {
    exports com.etiennecollin.ift2255.clientCLI;
    exports com.etiennecollin.ift2255.clientCLI.controllers;
    exports com.etiennecollin.ift2255.clientCLI.models;
    exports com.etiennecollin.ift2255.clientCLI.models.data;
    exports com.etiennecollin.ift2255.clientCLI.models.data.products;
    exports com.etiennecollin.ift2255.clientCLI.views;
    exports com.etiennecollin.ift2255.clientCLI.views.productDisplay;
}