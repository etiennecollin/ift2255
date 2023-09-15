/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper, Gilles Dumas, Charlotte Locas
 */

package com.etiennecollin.ift2255.clientCLI;

import java.util.Scanner;

/**
 * The `Utils` class provides utility methods for common tasks in the client command-line interface (CLI).
 * It includes methods for handling user input, formatting strings, and creating menus.
 */
public class Utils {
    /**
     * Displays a prompt to the user and expects a yes/no response.
     * It keeps prompting until a valid response is given.
     *
     * @param prompt The message to display as the prompt.
     *
     * @return `true` if the user answers "yes" (case-insensitive), `false` if the user answers "no" (case-insensitive).
     */
    protected static boolean prettyYesNo(String prompt) {
        Scanner scanner = new Scanner(System.in);

        // Instantiate the variables used to store the answer and its parsed version
        String answer;
        boolean answerParsed;

        // Repeat until input is valid
        while (true) {
            // Print prompt
            System.out.println("------------");
            System.out.print(prettify(prompt) + " (y/n): ");
            answer = scanner.next().strip();

            // Parse answer
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                answerParsed = true;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                answerParsed = false;
            } else {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            scanner.close();
            return answerParsed;
        }
    }

    /**
     * Prettifies a string by adding a delimiter bar at the beginning.
     *
     * @param string The input string to prettify.
     *
     * @return The prettified string with a delimiter at the beginning.
     */
    protected static String prettify(String string) {
        return "| " + string;
    }

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt  The message to display as the prompt.
     * @param choices An array of strings representing the menu choices.
     *
     * @return The index of the selected choice (0-based index).
     */
    protected static int prettyMenu(String prompt, String[] choices) {
        Scanner scanner = new Scanner(System.in);

        // Instantiate the variables used to store the answer and its parsed version
        String answer;
        int answerParsed;

        // Generate the menu containing all choices
        String menu = "";
        int i = 0;
        for (String choice : choices) {
            menu = menu.concat(prettify(i + ") " + choice + "\n"));
            i++;
        }

        // Repeat until input is valid
        while (true) {
            // Print menu and prompt
            System.out.println("------------");
            System.out.println(prettify(prompt) + ": ");
            System.out.print(menu);
            System.out.print(prettify("Selection: "));
            answer = scanner.next().strip();

            // Parse answer
            try {
                answerParsed = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            // Check that answer corresponds to a choice
            if (answerParsed < 0 || answerParsed >= choices.length) {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            scanner.close();
            return answerParsed;
        }
    }
}
