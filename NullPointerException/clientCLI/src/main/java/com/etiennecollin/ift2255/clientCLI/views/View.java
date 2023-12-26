/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

/**
 * The {@code View} class is an abstract base class for implementing views in the CLI application. Views are responsible
 * for rendering user interfaces and handling user interactions for various functionalities within the application.
 * Classes that extend {@code View} must provide an implementation for the {@link #render()} method.
 * <p>
 * The {@code render()} method is called to display the user interface and handle user interactions specific to the
 * functionality represented by the extending class.
 */
public abstract class View {
    /**
     * Renders the user interface and handles user interactions for the functionality represented by the extending class.
     * Subclasses must provide an implementation for this method to define the behavior of the view.
     */
    public abstract void render();
}
