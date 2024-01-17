import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter arithmetic expression:");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid input format");
        }

        int num1 = convertToNumber(tokens[0]);
        int num2 = convertToNumber(tokens[2]);

        validateInput(num1, num2);

        char operator = tokens[1].charAt(0);

        int result = switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Invalid operator");
        };

        return convertToOutput(result, tokens);
    }

    private static int convertToNumber(String str) {
        Map<String, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);

        if (romanNumerals.containsKey(str)) {
            return romanNumerals.get(str);
        } else {
            return Integer.parseInt(str);
        }
    }

    private static void validateInput(int num1, int num2) {
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Numbers must be between 1 and 10 (inclusive)");
        }
    }

    private static String convertToOutput(int result, String[] tokens) {
        if (tokens[0].matches("[IVX]+")) {
            // Input is in Roman numerals, convert result to Roman numerals
            if (result < 1) {
                throw new IllegalArgumentException("Result cannot be represented in Roman numerals");
            }
            return convertToRoman(result);
        } else {
            // Input is in Arabic numerals, return result as is
            return String.valueOf(result);
        }
    }

    private static String convertToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number out of Roman numeral range");
        }

        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder result = new StringBuilder();
        int i = 12; // Start from the largest Roman numeral

        while (number > 0) {
            int div = number / values[i];
            number %= values[i];

            while (div-- > 0) {
                result.append(romanNumerals[i]);
            }
            i--;
        }

        return result.toString();
    }
}