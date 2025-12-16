public class Student {

    private String name;
    private int age;
    private double grade;
    private String studentid;
    private String contact;

    public Student(String studentid, String name, int age, double grade, String contact) {
        this.studentid = studentid;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.contact = contact;
    }

    public String getStudentId() { return studentid; }
    public void setStudentId(String studentId) { this.studentid = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String tablerow() {
        return String.format("%-8s | %-20s | %3d | %6.2f | %-12s",
                studentid, truncate(name,20), age, grade, truncate(contact,12));
    }

    private String truncate(String s, int max) {
        if (s == null) return " ";
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentid='" + studentid + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                ", contact='" + contact + '\'' +
                '}';
    }
}