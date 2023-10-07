/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The `Utils` class provides utility methods for common tasks in the client command-line interface (CLI).
 * It includes methods for handling user input, formatting strings, and creating menus.
 */
public class Utils {
    // Prevents closing the System.in stream
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays a prompt to the user and expects a yes/no response.
     * It keeps prompting until a valid response is given.
     *
     * @param prompt The message to display as the prompt.
     *
     * @return `true` if the user answers "yes" (case-insensitive), `false` if the user answers "no" (case-insensitive).
     */
    protected static boolean prettyYesNo(String prompt) {

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
     * Displays a prompt with single question and returns the user answer.
     *
     * @param prompt The message to display as the prompt.
     *
     * @return The answer of the user.
     */
    protected static String prettyPrompt(String prompt) {
        System.out.print(prettify(prompt) + ": ");
        return scanner.next().strip();
    }

    protected static String prettyPromptValidated(String prompt, Function<String, Boolean> validator) {
        while (true) {
            System.out.print(prettify(prompt) + ": ");
            String answer = scanner.next().strip();
            if (validator.apply(answer)) {
                return answer;
            }

            System.out.println("Invalid response. Please follow the prompt.");
        }
    }

    protected static int prettyPromptInt(String prompt) {
        while (true) {
            System.out.print(prettify(prompt) + ": ");
            try {
                return Integer.parseInt(scanner.next().strip());
            } catch (NumberFormatException ignored) {
                System.out.println("Please enter a whole number with no thousands symbol.");
            }
        }

    }

    protected static float prettyPromptFloat(String prompt) {
        while (true) {
            System.out.print(prettify(prompt) + ": ");
            try {
                return Float.parseFloat(scanner.next().strip());
            } catch (NumberFormatException ignored) {
                System.out.println("Please enter a number with no thousands symbol.");
            }
        }

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
    protected static int prettyMenuInt(String prompt, String[] choices) {
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

            return answerParsed;
        }
    }

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt  The message to display as the prompt.
     * @param choices An ArrayList of strings representing the menu choices.
     *
     * @return The index of the selected choice (0-based index).
     */
    protected static int prettyMenuInt(String prompt, ArrayList<String> choices) {
        return prettyMenuInt(prompt, choices.toArray(new String[0]));
    }

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt  The message to display as the prompt.
     * @param choices An array of strings representing the menu choices.
     *
     * @return The String associated with the selected choice.
     */
    protected static String prettyMenu(String prompt, String[] choices) {
        return choices[prettyMenuInt(prompt, choices)];
    }

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt  The message to display as the prompt.
     * @param choices An ArrayList of strings representing the menu choices.
     *
     * @return The String associated with the selected choice.
     */
    protected static String prettyMenu(String prompt, ArrayList<String> choices) {
        return choices.get(prettyMenuInt(prompt, choices.toArray(new String[0])));
    }

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt  The message to display as the prompt.
     * @param enumClass The class of the Enum used to represent choices.
     *
     * @return The Enum associated with the selected choice.
     */
    protected static <T extends Enum<T>> T prettyMenu(String prompt, Class<T> enumClass) {
        var enumConstants = enumClass.getEnumConstants();
        ArrayList<String> enumNames = new ArrayList<>();
        for (var c: enumConstants) {
            enumNames.add(c.name());
        }

        return Enum.valueOf(enumClass, prettyMenu(prompt, enumNames));
    }
}
