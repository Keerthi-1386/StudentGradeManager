import java.io.*;
import java.util.*;

class Student {
    String name;
    int rollNo;
    Map<String, Integer> subjects; // subject -> marks

    public Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
        this.subjects = new LinkedHashMap<>(); // maintains insertion order
    }

    public void addSubject(String subject, int marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
        subjects.put(subject, marks);
    }

    public double calculateAverage() {
        if (subjects.isEmpty()) return 0.0;
        int total = subjects.values().stream().mapToInt(Integer::intValue).sum();
        return (double) total / subjects.size();
    }

    public int calculateTotal() {
        return subjects.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String getGrade() {
        double avg = calculateAverage();
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Roll No: ").append(rollNo)
          .append(" | Name: ").append(name)
          .append(" | Total: ").append(calculateTotal())
          .append(" | Avg: ").append(String.format("%.2f", calculateAverage()))
          .append(" | Grade: ").append(getGrade())
          .append("\nSubjects:\n");
        for (Map.Entry<String, Integer> entry : subjects.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    // For file saving
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rollNo).append("|").append(name);
        for (Map.Entry<String, Integer> entry : subjects.entrySet()) {
            sb.append("|").append(entry.getKey()).append(":").append(entry.getValue());
        }
        return sb.toString();
    }

    public static Student fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 2) return null;

        int rollNo = Integer.parseInt(parts[0]);
        String name = parts[1];
        Student s = new Student(name, rollNo);

        for (int i = 2; i < parts.length; i++) {
            String[] subMark = parts[i].split(":");
            if (subMark.length == 2) {
                String subject = subMark[0];
                int marks = Integer.parseInt(subMark[1]);
                s.addSubject(subject, marks);
            }
        }
        return s;
    }
}

public class StudentGradeManager {
    private static List<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "students.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadStudentsFromFile();
        showMenu();
        saveStudentsToFile(); // Save before exit
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n=== Student Grade Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> searchStudent();
                case 4 -> {
                    System.out.println("Saving data and exiting...");
                    return;
                }
                default -> System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter Roll No: ");
        int rollNo = getIntInput();

        if (findStudentByRoll(rollNo) != null) {
            System.out.println("Student with this roll number already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        Student student = new Student(name, rollNo);

        System.out.print("How many subjects? ");
        int numSubjects = getIntInput();
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter subject " + i + " name: ");
            String subject = scanner.nextLine().trim();
            System.out.print("Enter marks (0-100) for " + subject + ": ");
            int marks = getIntInput();
            try {
                student.addSubject(subject, marks);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid marks! Skipping subject.");
                i--; // retry
            }
        }

        students.add(student);
        System.out.println("✅ Student added successfully!");
    }

    private static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("📭 No students found.");
            return;
        }
        System.out.println("\n--- All Student Records ---");
        students.forEach(System.out::println);
    }

    private static void searchStudent() {
        System.out.print("Enter Roll No to search: ");
        int rollNo = getIntInput();
        Student s = findStudentByRoll(rollNo);
        if (s != null) {
            System.out.println("\n--- Student Found ---");
            System.out.println(s);
        } else {
            System.out.println("❌ Student not found with Roll No: " + rollNo);
        }
    }

    private static Student findStudentByRoll(int rollNo) {
        return students.stream()
                .filter(s -> s.rollNo == rollNo)
                .findFirst()
                .orElse(null);
    }

    // Input helper
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next(); // discard invalid input
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return num;
    }

    // File I/O
    private static void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Student s : students) {
                writer.println(s.toFileString());
            }
            System.out.println("💾 Data saved to " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("⚠️ Failed to save data: " + e.getMessage());
        }
    }

    private static void loadStudentsFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("📁 No existing data file. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Student s = Student.fromFileString(line);
                    if (s != null) students.add(s);
                }
            }
            System.out.println("📥 Loaded " + students.size() + " student(s) from " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("⚠️ Failed to load data: " + e.getMessage());
        }
    }
}