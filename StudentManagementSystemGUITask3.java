
    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class StudentManagementSystemGUITask3 extends JFrame {

    private StudentManagementSystemtask3 studentManagementSystem;
    private JTextField nameField;
    private JTextField rollNumberField;
    private JTextField gradeField;
    private JTextArea resultArea;

    private static final String FILE_PATH = "students.txt";

    public StudentManagementSystemGUITask3() {
        setTitle("Student Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        studentManagementSystem = new StudentManagementSystemtask3();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLabel, constraints);

        nameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);

        JLabel rollNumberLabel = new JLabel("Roll Number:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(rollNumberLabel, constraints);

        rollNumberField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(rollNumberField, constraints);

        JLabel gradeLabel = new JLabel("Grade:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(gradeLabel, constraints);

        gradeField = new JTextField(5);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(gradeField, constraints);

        JButton addButton = new JButton("Add Student");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(addButton, constraints);

        JButton displayButton = new JButton("Display All Students");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(displayButton, constraints);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(scrollPane, constraints);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });

        add(panel);
    }

    private void addStudent() {
        
        String name = nameField.getText();
        String rollNumberStr = rollNumberField.getText();
        String grade = gradeField.getText();

        if (name.isEmpty() || rollNumberStr.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            int rollNumber = Integer.parseInt(rollNumberStr);
            Student newStudent = new Student(name, rollNumber, grade);
            studentManagementSystem.addStudent(newStudent);

            // Save the student data to the file
            saveStudentsToFile();

            nameField.setText("");
            rollNumberField.setText("");
            gradeField.setText("");
            JOptionPane.showMessageDialog(this, "Student added successfully.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Roll Number. Please enter a valid number.");
        }
    }

    private void displayAllStudents() {
        List<Student> students = studentManagementSystem.getAllStudents();
        if (students.isEmpty()) {
            resultArea.setText("No students found.");
        } else {
            StringBuilder allStudents = new StringBuilder();
            for (Student student : students) {
                allStudents.append(student.toString()).append("\n");
            }
            resultArea.setText(allStudents.toString());
        }
    }

    private void saveStudentsToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            List<Student> students = studentManagementSystem.getAllStudents();
            for (Student student : students) {
                String data = student.getName() + "," + student.getRollNumber() + "," + student.getGrade() + "\n";
                writer.write(data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0];
                    int rollNumber = Integer.parseInt(data[1]);
                    String grade = data[2];
                    Student student = new Student(name, rollNumber, grade);
                    studentManagementSystem.addStudent(student);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystemGUITask3 gui = new StudentManagementSystemGUITask3();
            gui.setVisible(true);

            
            gui.readStudentsFromFile();
        });
    }
}

