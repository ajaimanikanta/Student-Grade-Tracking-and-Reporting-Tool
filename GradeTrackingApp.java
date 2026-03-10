import java.util.*;

class Student {
    private String id;
    private String name;
    private Map<String, Double> grades = new HashMap<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addGrade(String subject, double grade) {
        grades.put(subject, grade);
    }

    public double calculateAverage() {
        if (grades.isEmpty()) return 0.0;
        double total = 0;
        for (double g : grades.values()) total += g;
        return total / grades.size();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Map<String, Double> getGrades() { return grades; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Average: " + String.format("%.2f", calculateAverage());
    }
}

class GradeManager {
    private Map<String, Student> students = new HashMap<>();

    public void addStudent(String id, String name) {
        if (students.containsKey(id)) {
            System.out.println("Student ID already exists!");
            return;
        }
        students.put(id, new Student(id, name));
        System.out.println("Student added successfully.");
    }

    public void recordGrade(String id, String subject, double grade) {
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        s.addGrade(subject, grade);
        System.out.println("Grade recorded successfully.");
    }

    public void showReport() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (Student s : students.values()) {
            System.out.println(s);
            for (Map.Entry<String, Double> entry : s.getGrades().entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public void showLowPerformers(double threshold) {
        System.out.println("Students below threshold (" + threshold + "):");
        for (Student s : students.values()) {
            if (s.calculateAverage() < threshold) {
                System.out.println("  " + s.getName() + " - Avg: " + s.calculateAverage());
            }
        }
    }
}

public class GradeTrackingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GradeManager manager = new GradeManager();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Student Grade Tracking System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Record Grade");
            System.out.println("3. Show Report");
            System.out.println("4. Show Low Performers");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    manager.addStudent(id, name);
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    id = sc.nextLine();
                    System.out.print("Enter Subject: ");
                    String subject = sc.nextLine();
                    System.out.print("Enter Grade: ");
                    double grade = sc.nextDouble();
                    manager.recordGrade(id, subject, grade);
                    break;
                case 3:
                    manager.showReport();
                    break;
                case 4:
                    System.out.print("Enter threshold: ");
                    double threshold = sc.nextDouble();
                    manager.showLowPerformers(threshold);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}