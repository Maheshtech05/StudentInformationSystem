import java.util.*;

public class StudentManager {

    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public Student addStudent(String name, int age, double grade, String contact, String studentIdProvided) {
        String id = (studentIdProvided == null || studentIdProvided.trim().isEmpty())
                ? generateId()
                : studentIdProvided.trim();

        while (findById(id) != null) {
            id = generateId();
        }

        Student s = new Student(id, name, age, grade, contact);
        students.add(s);
        return s;
    }

    private String generateId() {
        return "S" + (nextId++);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student findById(String id) {
        if (id == null) return null;
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id.trim())) return s;
        }
        return null;
    }

    public List<Student> findByName(String namePart) {
        List<Student> matches = new ArrayList<>();
        if (namePart == null || namePart.trim().isEmpty()) return matches;

        String q = namePart.toLowerCase(Locale.ROOT);
        for (Student s : students) {
            if (s.getName().toLowerCase(Locale.ROOT).contains(q)) {
                matches.add(s);
            }
        }
        return matches;
    }

    public boolean deleteById(String id) {
        Student s = findById(id);
        if (s != null) return students.remove(s);
        return false;
    }

    public boolean updateStudent(String id, String newName, Integer newAge, Double newGrade, String newContact) {
        Student s = findById(id);
        if (s == null) return false;

        if (newName != null && !newName.trim().isEmpty()) s.setName(newName.trim());
        if (newAge != null && newAge > 0) s.setAge(newAge);
        if (newGrade != null && newGrade >= 0.0 && newGrade <= 100.0) s.setGrade(newGrade);
        if (newContact != null && !newContact.trim().isEmpty()) s.setContact(newContact.trim());

        return true;
    }

    public void loadSampleData() {
        students.clear();
        nextId = 1;

        addStudent("Mahesh Ayerathan", 22, 86.5, "9876543210", null);
        addStudent("Priya Sharma", 20, 91.2, "9123456780", null);
        addStudent("Arjun Kumar", 19, 78.0, "9988776655", null);
        addStudent("Sana Khan", 21, 88.75, "9012345678", null);
    }
}