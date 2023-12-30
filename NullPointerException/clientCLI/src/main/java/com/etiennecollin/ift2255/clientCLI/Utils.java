/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The `Utils` class provides utility methods for common tasks in the client command-line interface (CLI).
 * It includes methods for handling user input, formatting strings, and creating menus.
 */
public class Utils {
    /**
     * The input scanner used by utils.
     */
    private static final Scanner scanner = new Scanner(System.in); // Prevents closing the System.in stream

    /**
     * Displays a prompt with single question and returns the user answer if valid.
     *
     * @param prompt    The message to display as the prompt.
     * @param validator A {@code Function} that takes a {@code String} as input and returns a {@code ValidationResult}.
     *
     * @return The answer of the user.
     */
    public static String prettyPrompt(String prompt, Function<String, OperationResult> validator) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + ": ");
            String answer = scanner.nextLine().strip();
            OperationResult result = validator.apply(answer);
            if (result.isValid()) {
                return answer;
            }

            System.out.println(prettify(result.message()));
        }
    }

    /**
     * Prettifies a string by adding a delimiter bar at the beginning.
     *
     * @param string The input string to prettify.
     *
     * @return The prettified string with a delimiter at the beginning.
     */
    public static String prettify(String string) {
        return "| " + string;
    }

    /**
     * Displays a formatted prompt to the console and retrieves an integer input from the user.
     * Continues prompting the user until a valid integer is provided.
     *
     * @param prompt The prompt message to be displayed.
     *
     * @return A valid integer entered by the user.
     */
    public static int prettyPromptInt(String prompt) {
        return prettyPromptInt(prompt, i -> new OperationResult(true, ""));
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
    public static int prettyPromptInt(String prompt, Function<Integer, OperationResult> validator) {
        while (true) {
            System.out.println("------------");
            System.out.print(prettify(prompt) + ": ");
            try {
                int num = Integer.parseInt(scanner.nextLine().strip());
                OperationResult result = validator.apply(num);
                if (result.isValid()) {
                    return num;
                }

                System.out.println(prettify(result.message()));
            } catch (NumberFormatException ignored) {
                System.out.println(prettify("Please enter a whole number with no thousands symbol."));
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
    public static boolean prettyPromptBool(String prompt) {
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
    public static int prettyPromptCurrency(String prompt) {
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
    public static LocalDate prettyPromptDate(String prompt) {
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
    public static int prettyMenu(String prompt, String[] choices) {
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
    public static int prettyMenu(String prompt, ArrayList<String> choices) {
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

    /**
     * Displays a menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param <T>     The type of elements in the menu.
     * @param prompt  The message to display as the prompt.
     * @param choices An ArrayList of elements representing the menu choices.
     *
     * @return The selected element from the menu based on the numeric input.
     */
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
     * Displays a formatted menu for selecting an option from an Enum, given a prompt.
     * The user is prompted to choose from the available Enum constants.
     *
     * @param prompt    The message prompting the user for input.
     * @param enumClass The Class object representing the Enum type.
     * @param <T>       The Enum type.
     *
     * @return The selected Enum constant.
     */
    public static <T extends Enum<T>> T prettyMenu(String prompt, Class<T> enumClass) {
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

    /**
     * Displays a dynamic menu with choices to the user and expects a numeric selection.
     * It keeps prompting until a valid selection is made.
     *
     * @param prompt             The message to display as the prompt.
     * @param backName           The name associated with the "go back" option.
     * @param menuItems          An ArrayList of DynamicMenuItems representing the menu choices.
     * @param topOfLoopDisplayer A Runnable providing functionality to be displayed at the top of the menu loop.
     */
    public static void prettyDynamicMenu(String prompt, String backName, ArrayList<DynamicMenuItem> menuItems, Runnable topOfLoopDisplayer) {

        ArrayList<DynamicMenuItem> filteredItems = new ArrayList<>();
        for (var item : menuItems) {
            if (item.displayCondition.get()) {
                filteredItems.add(item);
            }
        }

        ArrayList<String> itemNames = new ArrayList<>();
        itemNames.add(backName);
        for (var item : filteredItems) {
            itemNames.add(item.name);
        }

        // Setup action menu
        clearConsole();
        topOfLoopDisplayer.run();

        int answer = prettyMenu(prompt, itemNames);
        if (answer != 0) {
            filteredItems.get(answer - 1).action.run();
        }
    }

    /**
     * Clears the console screen by printing empty lines.
     */
    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    /**
     * Displays a paginated menu for a list of items, allowing the user to perform actions on the items.
     *
     * @param <T>           The type of elements in the menu.
     * @param items         The list of items to be displayed in the menu.
     * @param itemsPerPage  The number of items to display per page.
     * @param actionName    The name of the action to perform on selected items.
     * @param itemDisplayer A Consumer that displays an individual item.
     * @param itemMenuName  A Function that returns a string representing the menu name for an individual item.
     * @param action        A Consumer that defines the action to be performed on selected items. Return false to quit menu, true to continue in menu.
     */
    public static <T> void prettyPaginationMenu(List<T> items, int itemsPerPage, String actionName, Consumer<T> itemDisplayer, Function<T, String> itemMenuName, Function<T, Boolean> action) {
        outerLoop:
        for (int i = 0; i < items.size(); i += itemsPerPage) {
            int itemsOnPage = Math.min(itemsPerPage, items.size() - i);
            clearConsole();

            System.out.println(prettify("Page from " + (i + 1) + " to " + (i + itemsOnPage) + ":"));

            ArrayList<String> itemMenuNames = new ArrayList<>();
            itemMenuNames.add("Go back");

            for (int j = i; j < i + itemsOnPage; j++) {
                T item = items.get(j);
                itemDisplayer.accept(item);
                itemMenuNames.add(itemMenuName.apply(item));
            }

            // Setup action menu
            ArrayList<String> options = new ArrayList<>();
            options.add("Go back");
            options.add(actionName);
            if (i + itemsOnPage < items.size()) {
                options.add("See more");
            }

            innerLoop:
            while (true) {
                int answer = prettyMenu("Select action", options);
                switch (answer) {
                    case 0 -> {
                        // Go back
                        break outerLoop;
                    }
                    case 1 -> {
                        int index = prettyMenu("Select", itemMenuNames);
                        if (index == 0) break;
                        boolean continueMenu = action.apply(items.get(i + index - 1));
                        if (!continueMenu) {
                            break outerLoop;
                        }
                    }
                    case 2 -> {
                        // See more
                        break innerLoop;
                    }
                }
            }
        }
    }

    /**
     * Formats a currency amount in cents to a string representation in dollars and cents.
     *
     * @param cents The amount in cents to be formatted.
     *
     * @return The formatted currency string (e.g., "12.34$").
     */
    public static String formatMoney(int cents) {
        return cents / 100 + "." + String.format("%02d", cents % 100) + "$";
    }

    /**
     * Closes the scanner, releasing system resources.
     */
    public static void quit() {
        scanner.close();
    }

    //    protected static void quit(UniShop unishop) {
    //        System.out.println(prettify("Saving app state..."));
    //        unishop.saveUserList(Client.savePath);
    //        System.out.println(prettify("Quitting UniShop"));
    //        scanner.close();
    //    }

    //    protected static void logout(UniShop uniShop) {
    //        System.out.println(prettify("Logging-out..."));
    //        uniShop.setCurrentUser(null);
    //    }

    /**
     * Validates a name to ensure it contains only letters and optional spaces or hyphens.
     *
     * @param s The name to be validated.
     *
     * @return An OperationResult indicating whether the name is valid, along with an error message if invalid.
     *
     * @throws RuntimeException If the validation process encounters an exception.
     */
    public static OperationResult validateName(String s) throws RuntimeException {
        if (!s.matches("[a-zA-Z]+[\\s-]?[a-zA-Z]*")) {
            return new OperationResult(false, "Your name should only contains letters");
        }
        return new OperationResult(true, "");
    }

    /**
     * Validates an email address using a regular expression.
     *
     * @param s The email address to be validated.
     *
     * @return An OperationResult indicating whether the email address is valid, along with an error message if invalid.
     *
     * @throws RuntimeException If the validation process encounters an exception.
     */
    public static OperationResult validateEmail(String s) throws RuntimeException {
        if (!s.matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")) {
            return new OperationResult(false, "Your email has a wrong format");
        }
        return new OperationResult(true, "");
    }

    /**
     * Validates a phone number using a regular expression that accepts formats xxx xxx xxxx, xxx-xxx-xxxx, and xxxxxxxxxx.
     * <p>
     * regex taken from <a href="https://www.baeldung.com/java-regex-validate-phone-numbers">https://www.baeldung.com/java-regex-validate-phone-numbers</a>
     *
     * @param s The phone number to be validated.
     *
     * @return An OperationResult indicating whether the phone number is valid, along with an error message if invalid.
     *
     * @throws RuntimeException If the validation process encounters an exception.
     */
    public static OperationResult validatePhoneNumber(String s) throws RuntimeException {
        if (!s.matches("(\\(\\d{3}\\)|\\d{3})[- ]?\\d{3}[- ]?\\d{4}")) {
            return new OperationResult(false, "Your phone number has a wrong format");
        }
        return new OperationResult(true, "");
    }

    /**
     * Validates an ISBN (International Standard Book Number) using a regular expression.
     *
     * @param s The ISBN to be validated.
     *
     * @return An OperationResult indicating whether the ISBN is valid, along with an error message if invalid.
     *
     * @throws RuntimeException If the validation process encounters an exception.
     */
    public static OperationResult validateISBN(String s) throws RuntimeException {
        if (!s.matches("\\d{13}")) {
            return new OperationResult(false, "Your ISBN has a wrong format");
        }
        return new OperationResult(true, "");
    }

    /**
     * Validates bonus fidelity points based on the provided bonus points and the price of a product.
     *
     * @param bonusPoints The bonus points to be validated.
     * @param price       The price of the product.
     *
     * @return An OperationResult indicating whether the bonus points are valid, along with an error message if invalid.
     */
    public static OperationResult validateBonusFidelityPoints(int bonusPoints, int price) {
        int dollars = price / 100;
        int maxBonusPoints = 19 * dollars;
        if (bonusPoints < 0) {
            return new OperationResult(false, "Bonus points cannot be negative.");
        } else if (maxBonusPoints < bonusPoints) {
            return new OperationResult(false, "A maximum of " + maxBonusPoints + " bonus points are allowed based on this product's price.");
        } else {
            return new OperationResult(true, "");
        }
    }

    /**
     * Validates a number to ensure it falls within a specified range.
     *
     * @param number     The number to be validated.
     * @param lowerBound The lower bound of the valid range.
     * @param upperBound The upper bound of the valid range.
     *
     * @return An OperationResult indicating whether the number is within the specified range, along with an error message if invalid.
     */
    public static OperationResult validateNumberRange(int number, int lowerBound, int upperBound) {
        if (number < lowerBound) {
            return new OperationResult(false, "Number must not be less than " + lowerBound);
        } else if (upperBound < number) {
            return new OperationResult(false, "Number must not be greater than " + upperBound);
        } else {
            return new OperationResult(true, "");
        }
    }

    /**
     * Validates that a string is not empty.
     *
     * @param string The string to be validated.
     *
     * @return An OperationResult indicating whether the string is not empty, along with an error message if empty.
     */
    public static OperationResult validateNotEmpty(String string) {
        if (string.isEmpty()) {
            return new OperationResult(false, "This field must not be empty.");
        } else {
            return new OperationResult(true, "");
        }
    }

    /**
     * Displays a prompt and waits for the user to press any key to continue.
     */
    public static void waitForKey() {
        prettyPrompt("Press any key to continue");
    }

    /**
     * Displays a prompt with single question and returns the user answer.
     *
     * @param prompt The message to display as the prompt.
     *
     * @return The answer of the user.
     */
    public static String prettyPrompt(String prompt) {
        System.out.println("------------");
        System.out.print(prettify(prompt) + ": ");
        return scanner.nextLine().strip();
    }

    /**
     * A record representing a dynamic menu item with a name, action, and display condition.
     *
     * @param name             The name of the menu item.
     * @param action           The action to be performed when the menu item is selected.
     * @param displayCondition The condition indicating whether the menu item should be displayed.
     */
    public record DynamicMenuItem(String name, Runnable action, Supplier<Boolean> displayCondition) {}
}
