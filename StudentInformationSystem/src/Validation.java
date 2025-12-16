import java.util.*;

public class Validation {

    public static int readInt(Scanner sc, String prompt, boolean positiveOnly) {
        int val;
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                val = Integer.parseInt(line);
                if (positiveOnly && val <= 0) {
                    System.out.println("Please enter a positive integer.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }

    public static double readDoubleInRange(Scanner sc, String prompt, double min, double max) {
        double val;
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                val = Double.parseDouble(line);
                if (min <= max) {
                    if (val < min || val > max) {
                        System.out.printf("Please enter a value between %.2f and %.2f.%n", min, max);
                        continue;
                    }
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc, String prompt) {
        String s;
        while (true) {
            System.out.print(prompt);
            s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    public static boolean isValidContact(String contact, int minLen, int maxLen) {
        if (contact == null) return false;
        String digits = contact.replaceAll("[^0-9]", "");
        return digits.length() >= minLen && digits.length() <= maxLen;
    }


    public static String readOptionalString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static Integer readOptionalInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) return null;
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer input. Update cancelled for this field.");
            return null;
        }
    }

    public static Double readOptionalDoubleInRange(Scanner sc, String prompt, double min, double max) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) return null;
        try {
            double val = Double.parseDouble(line);
            if (val < min || val > max) {
                System.out.printf("Value not in range %.2f-%.2f. Update cancelled for this field.%n", min, max);
                return null;
            }
            return val;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Update cancelled for this field.");
            return null;
        }
    }


}
