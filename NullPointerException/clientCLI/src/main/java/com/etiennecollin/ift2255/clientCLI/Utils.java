/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import java.util.ArrayList;
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
            answer = scanner.nextLine().strip();

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
        return scanner.nextLine().strip();
    }

    /**
     * Displays a formatted prompt to the console and validates the user input using a custom validator function.
     * Continues prompting the user until a valid response is provided.
     *
     * @param prompt    The prompt message to be displayed.
     * @param validator A {@code Function} that takes a {@code String} as input and returns {@code true} if the input is valid, {@code false} otherwise.
     *
     * @return A valid user input as a {@code String} that passes the provided validator function.
     */
    protected static String prettyPromptValidated(String prompt, Function<String, Boolean> validator) {
        while (true) {
            System.out.print(prettify(prompt) + ": ");
            String answer = scanner.nextLine().strip();
            if (validator.apply(answer)) {
                return answer;
            }

            System.out.println("Invalid response. Please follow the prompt.");
        }
    }

    /**
     * Displays a formatted prompt to the console and retrieves an integer input from the user.
     * Continues prompting the user until a valid integer is provided.
     *
     * @param prompt The prompt message to be displayed.
     *
     * @return A valid integer entered by the user.
     */
    protected static int prettyPromptInt(String prompt) {
        while (true) {
            System.out.print(prettify(prompt) + ": ");
            try {
                return Integer.parseInt(scanner.nextLine().strip());
            } catch (NumberFormatException ignored) {
                System.out.println("Please enter a whole number with no thousands symbol.");
            }
        }
    }

    /**
     * Displays a formatted prompt to the console and retrieves a currency amount input from the user.
     * The input is expected to be in the format of a number representing a currency value (e.g., 1000 for $10.00).
     * Continues prompting the user until a valid currency amount is provided.
     *
     * @param prompt The prompt message to be displayed.
     *
     * @return A valid currency amount entered by the user, without decimal points and thousands symbols.
     *
     * @throws NumberFormatException If the input provided is not a valid number.
     */
    protected static int prettyPromptCurrency(String prompt) {
        while (true) {
            System.out.print(prettify(prompt) + ": ");
            try {
                return Integer.parseInt(scanner.nextLine().strip().replace(".", ""));
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
            answer = scanner.nextLine().strip();

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
     * @param prompt    The message to display as the prompt.
     * @param enumClass The class of the Enum used to represent choices.
     *
     * @return The Enum associated with the selected choice.
     */
    protected static <T extends Enum<T>> T prettyMenu(String prompt, Class<T> enumClass) {
        var enumConstants = enumClass.getEnumConstants();
        ArrayList<String> enumNames = new ArrayList<>();
        for (var c : enumConstants) {
            enumNames.add(c.name());
        }

        return Enum.valueOf(enumClass, prettyMenu(prompt, enumNames));
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
     * @param choices An ArrayList of ArrayLists of strings representing the menu choices.
     *
     * @return The int associated with the selected choice.
     */
    protected static int prettyMenu2DArray(String prompt, ArrayList<ArrayList<String>> choices) {

        System.out.println("------------");
        System.out.println(prettify(prompt));

        for (int i = 0; i < choices.size(); i++) {
            System.out.println(prettify("Order " + (i) + ": "));
            for (int j = 0; j < choices.get(i).size(); j++) {
                System.out.println(prettify(choices.get(i).get(j)));
            }
        }

        System.out.print(prettify("Selection: "));
        Scanner scan = new Scanner(System.in);
        int answer = scan.nextInt();

        // repeat until input is valid
        while (answer < 0 || answer > choices.size() - 1) {
            System.out.println(prettify("Invalid input"));
            System.out.print(prettify("Selection: "));
            answer = scan.nextInt(); // answer correction
            continue;
        }

        return answer;
    }
}
