import java.util.List;
import java.util.Scanner;

public class StudentInformationSystem {

    private static final StudentManager manager = new StudentManager();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Student Information System ===");
        manager.loadSampleData();

        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addStudentFlow(); break;
                case "2": viewAllStudents(); break;
                case "3": searchStudentFlow(); break;
                case "4": updateStudentFlow(); break;
                case "5": deleteStudentFlow(); break;
                case "6": manager.loadSampleData(); System.out.println("Sample data loaded."); break;
                case "0": exit = true; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Exiting. Goodbye!");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search (by ID or name)");
        System.out.println("4. Update student (by ID)");
        System.out.println("5. Delete student (by ID)");
        System.out.println("6. Load sample data");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addStudentFlow() {
        System.out.println("\n-- Add Student --");
        String name = Validation.readNonEmptyString(sc, "Enter name: ");
        int age = Validation.readInt(sc, "Enter age (positive integer): ", true);
        double grade = Validation.readDoubleInRange(sc, "Enter grade (0.0 - 100.0): ", 0.0, 100.0);

        String contact;
        while (true) {
            contact = Validation.readNonEmptyString(sc, "Enter contact (7-15 digits): ");
            if (Validation.isValidContact(contact, 7, 15)) break;
            System.out.println("Invalid contact. Try again.");
        }

        System.out.print("Enter student ID (leave blank to auto-generate): ");
        String sid = sc.nextLine().trim();

        Student s = manager.addStudent(name, age, grade, contact, sid);
        System.out.println("Added: " + s.tablerow());
    }

    private static void viewAllStudents() {
        System.out.println("\n-- All Students --");
        List<Student> list = manager.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        printTableHeader();
        for (Student s : list) {
            System.out.println(s.tablerow());
        }
    }

    private static void printTableHeader() {
        System.out.println(String.format("%-8s | %-20s | %3s | %6s | %-12s",
                "ID", "Name", "Age", "Grade", "Contact"));
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void searchStudentFlow() {
        System.out.println("\n-- Search Students --");
        System.out.print("Search by (1) ID or (2) Name: ");
        String mode = sc.nextLine().trim();

        if (mode.equals("1")) {
            System.out.print("Enter student ID: ");
            String id = sc.nextLine().trim();
            Student s = manager.findById(id);
            if (s == null) System.out.println("No student found with ID " + id);
            else {
                printTableHeader();
                System.out.println(s.tablerow());
            }
        } else if (mode.equals("2")) {
            System.out.print("Enter name or partial name: ");
            String q = sc.nextLine().trim();
            List<Student> matches = manager.findByName(q);
            if (matches.isEmpty()) System.out.println("No students matched \"" + q + "\"");
            else {
                printTableHeader();
                for (Student s : matches) System.out.println(s.tablerow());
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void updateStudentFlow() {
        System.out.println("\n-- Update Student --");
        System.out.print("Enter student ID to update: ");
        String id = sc.nextLine().trim();
        Student s = manager.findById(id);

        if (s == null) {
            System.out.println("No student found with ID " + id);
            return;
        }

        System.out.println("Leave fields blank to keep current value.");
        printTableHeader();
        System.out.println(s.tablerow());

        String newName = Validation.readOptionalString(sc, "New name: ").trim();
        Integer newAge = Validation.readOptionalInt(sc, "New age: ");
        Double newGrade = Validation.readOptionalDoubleInRange(sc, "New grade (0.0 - 100.0): ", 0.0, 100.0);
        String newContact = Validation.readOptionalString(sc, "New contact: ").trim();

        if (newAge != null && newAge <= 0) {
            System.out.println("Invalid age. Update aborted.");
            return;
        }
        if (!newContact.isEmpty() && !Validation.isValidContact(newContact, 7, 15)) {
            System.out.println("Invalid contact. Update aborted.");
            return;
        }

        if (newName.isEmpty()) newName = null;
        if (newContact.isEmpty()) newContact = null;

        boolean ok = manager.updateStudent(id, newName, newAge, newGrade, newContact);
        if (ok) {
            System.out.println("Updated student:");
            printTableHeader();
            System.out.println(manager.findById(id).tablerow());
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deleteStudentFlow() {
        System.out.println("\n-- Delete Student --");
        System.out.print("Enter student ID to delete: ");
        String id = sc.nextLine().trim();
        Student s = manager.findById(id);

        if (s == null) {
            System.out.println("No student found with ID " + id);
            return;
        }

        printTableHeader();
        System.out.println(s.tablerow());

        System.out.print("Confirm delete? (y/N): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("y")) {
            boolean removed = manager.deleteById(id);
            System.out.println(removed ? "Deleted." : "Delete failed.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }
}