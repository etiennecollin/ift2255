/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import com.etiennecollin.ift2255.clientCLI.classes.UniShop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The `Utils` class provides utility methods for common tasks in the client command-line interface (CLI).
 * It includes methods for handling user input, formatting strings, and creating menus.
 */
public class Utils {
    // Prevents closing the System.in stream
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays a prompt with single question and returns the user answer.
     *
     * @param prompt The message to display as the prompt.
     *
     * @return The answer of the user.
     */
    protected static String prettyPrompt(String prompt) {
        System.out.println("------------");
        System.out.print(prettify(prompt) + ": ");
        return scanner.nextLine().strip();
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
     * Displays a prompt with single question and returns the user answer if valid.
     *
     * @param prompt    The message to display as the prompt.
     * @param validator A {@code Function} that takes a {@code String} as input and returns a {@code ValidationResult}.
     *
     * @return The answer of the user.
     */
    protected static String prettyPrompt(String prompt, Function<String, ValidationResult> validator) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + ": ");
            String answer = scanner.nextLine().strip();
            ValidationResult result = validator.apply(answer);
            if (result.isValid) {
                return answer;
            }

            System.out.println(result.message);
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
        return prettyPromptInt(prompt, i -> new ValidationResult(true, ""));
    }

    /**
     * Displays a formatted prompt to the console and retrieves an integer input from the user.
     * Continues prompting the user until a valid integer is provided.
     *
     * @param prompt    The prompt message to be displayed.
     * @param validator A {@code Function} that takes an {@code Integer} as input and returns a {@code ValidationResult}
     *
     * @return A valid integer entered by the user.
     */
    protected static int prettyPromptInt(String prompt, Function<Integer, ValidationResult> validator) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + ": ");
            try {
                int num = Integer.parseInt(scanner.nextLine().strip());
                ValidationResult result = validator.apply(num);
                if (result.isValid) {
                    return num;
                }

                System.out.println(result.message);
            } catch (NumberFormatException ignored) {
                System.out.println("Please enter a whole number with no thousands symbol.");
            }
        }
    }

    /**
     * Displays a prompt to the user and expects a yes/no response.
     * It keeps prompting until a valid response is given.
     *
     * @param prompt The message to display as the prompt.
     *
     * @return `true` if the user answers "yes/y/true/1" (case-insensitive), `false` if the user answers "no/n/false/0" (case-insensitive).
     */
    protected static boolean prettyPromptBool(String prompt) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + " (y/n): ");
            String answer = scanner.nextLine().strip();
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("true") || answer.equalsIgnoreCase("1")) {
                return true;
            } else if (answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("false") || answer.equalsIgnoreCase("0")) {
                return false;
            } else {
                System.out.println("Please enter a valid answer (yes/y/true/1 or no/n/false/0)");
            }
        }
    }

    /**
     * Displays a formatted prompt to the console and retrieves a currency amount input from the user.
     * The input is expected to be in the format of a number representing a currency value
     * (e.g. for $10.00: "10", "$10", "10.", "10.00", and "$10.00" are valid).
     * Continues prompting the user until a valid currency amount is provided.
     *
     * @param prompt The prompt message to be displayed.
     *
     * @return A valid currency amount in cents.
     *
     * @throws NumberFormatException If the input provided is not a valid number.
     */
    protected static int prettyPromptCurrency(String prompt) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + ": ");

            Pattern pattern = Pattern.compile("^\\$?(\\d+)\\.?(\\d{2})?$");
            Matcher matcher = pattern.matcher(scanner.nextLine().strip());

            if (matcher.find()) {
                int value = Integer.parseInt(matcher.group(1)) * 100;
                if (matcher.group(2) != null) {
                    value += Integer.parseInt(matcher.group(2));
                }

                return value;
            } else {
                System.out.println("Please enter a positive dollar (and cents) value with no thousands symbol. E.g. 42.99");
            }
        }
    }

    /**
     * Displays a formatted prompt to the console and retrieves a date input from the user.
     * Continues prompting the user until a valid date is provided.
     *
     * @param prompt The prompt message to be displayed.
     *
     * @return A valid LocalDate entered by the user.
     */
    protected static LocalDate prettyPromptDate(String prompt) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + " (yyyy-mm-dd): ");
            try {
                return LocalDate.parse(scanner.nextLine().strip(), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a date in yyyy-mm-dd format. E.g. 2023-01-01");
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
    protected static int prettyMenu(String prompt, String[] choices) {
        return prettyMenu(prompt, new ArrayList<>(Arrays.asList(choices)));
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
    protected static int prettyMenu(String prompt, ArrayList<String> choices) {
        // Instantiate the variables used to store the answer and its parsed version
        String answer;
        int answerParsed;

        // Generate the menu containing all choices
        StringBuilder menu = new StringBuilder();
        int i = 0;
        for (String choice : choices) {
            menu.append(prettify(i + ") " + choice + "\n"));
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
            if (answerParsed < 0 || answerParsed >= choices.size()) {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            return answerParsed;
        }
    }

    protected static <T> T prettyMenuT(String prompt, ArrayList<T> choices) {
        // Instantiate the variables used to store the answer and its parsed version
        int index;

        // Generate the menu containing all choices
        StringBuilder menu = new StringBuilder();
        int i = 0;
        for (T choice : choices) {
            menu.append(prettify(i + ") " + choice + "\n"));
            i++;
        }

        // Repeat until input is valid
        while (true) {
            // Print menu and prompt
            System.out.println("------------");
            System.out.println(prettify(prompt) + ": ");
            System.out.print(menu);
            System.out.print(prettify("Selection: "));
            String answer = scanner.nextLine().strip();

            // Parse answer
            try {
                index = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            // Check that answer corresponds to a choice
            if (index < 0 || index >= choices.size()) {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            return choices.get(index);
        }
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

        return enumConstants[prettyMenu(prompt, enumNames)];
    }

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt  The message to display as the prompt.
     * @param choices An ArrayList of ArrayLists of strings representing the menu choices.
     * @param prefix  What each ArrayList contained in the main one represents.
     *                This prefix will be printed with an index.
     *
     * @return The index associated with the selected choice in choices.
     */
    protected static int prettyMenu(String prompt, ArrayList<ArrayList<String>> choices, String prefix) {
        // Instantiate the variables used to store the answer and its parsed version
        String answer;
        int answerParsed;

        StringBuilder menu = new StringBuilder();
        for (int i = 0; i < choices.size(); i++) {
            menu.append(prettify(prefix + " " + i + ":\n"));
            for (String choice : choices.get(i)) {
                menu.append(prettify("  " + choice + "\n"));
            }
        }

        while (true) {
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

            if (answerParsed < 0 || answerParsed >= choices.size()) {
                System.out.println(prettify("Invalid input"));
                continue;
            }

            return answerParsed;
        }
    }

    protected static void quit(UniShop unishop) {
        System.out.println(prettify("Saving app state..."));
        unishop.saveUserList(Client.savePath);
        System.out.println(prettify("Quitting UniShop"));
        scanner.close();
    }

    protected static void logout(UniShop uniShop) {
        System.out.println(prettify("Logging-out..."));
        uniShop.setCurrentUser(null);
    }

    protected static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public static ValidationResult validateName(String s) throws RuntimeException {
        if (!s.matches("[a-zA-Z]+[\\s-]?[a-zA-Z]*")) {
            return new ValidationResult(false, "Your name should only contains letters");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateEmail(String s) throws RuntimeException {
        if (!s.matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")) {
            return new ValidationResult(false, "Your email has a wrong format");
        }
        return new ValidationResult(true, "");
    }

    // regex took from https://www.baeldung.com/java-regex-validate-phone-numbers
    // accept format xxx xxx xxxx  , xxx-xxx-xxxx and xxxxxxxxxx where x are digits
    public static ValidationResult validatePhoneNumber(String s) throws RuntimeException {
        if (!s.matches("(\\(\\d{3}\\)|\\d{3})[- ]?\\d{3}[- ]?\\d{4}")) {
            return new ValidationResult(false, "Your phone number has a wrong format");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateISBN(String s) throws RuntimeException {
        if (!s.matches("\\d{13}")) {
            return new ValidationResult(false, "Your ISBN has a wrong format");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateBonusFidelityPoints(int bonusPoints, int price) {
        int dollars = price / 100;
        int maxBonusPoints = 19 * dollars;
        if (bonusPoints < 0) {
            return new ValidationResult(false, "Bonus points cannot be negative.");
        } else if (maxBonusPoints < bonusPoints) {
            return new ValidationResult(false, "A maximum of " + maxBonusPoints + " bonus points are allowed based on this product's price.");
        } else {
            return new ValidationResult(true, "");
        }
    }

    public static ValidationResult validateNumberRange(int number, int lowerBound, int upperBound) {
        if (number < lowerBound) {
            return new ValidationResult(false, "Number must not be less than " + lowerBound);
        } else if (upperBound < number) {
            return new ValidationResult(false, "Number must not be greater than " + upperBound);
        } else {
            return new ValidationResult(true, "");
        }
    }

    public static ValidationResult validateNotEmpty(String string) {
        if (string.isEmpty()) {
            return new ValidationResult(false, "This field must not be empty.");
        } else {
            return new ValidationResult(true, "");
        }
    }

    public record ValidationResult(boolean isValid, String message) {}
}
