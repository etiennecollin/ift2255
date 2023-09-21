
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
        System.out.println(prettify("Hello world!"));

        boolean answer = prettyYesNo("Are you a nice person?");
        if (answer) {
            System.out.println(prettify("Nice"));
        } else {
            System.out.println(prettify("Bruh"));
        }

        String[] choices = {"Test1", "Test2", "Test3"};
        System.out.println(choices[prettyMenu("Make a choice", choices)]);
    }
}
