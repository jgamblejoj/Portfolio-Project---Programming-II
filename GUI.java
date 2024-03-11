import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends JFrame {
    private JTextField nameField, addressField, gpaField;
    private JTextArea displayArea;
    private LinkedList<Student> studentList;

    public GUI() {
        super("Student Directory System");
        studentList = new LinkedList<>();

        // Set up the form
        setLayout(new FlowLayout());

        add(new JLabel("Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Address:"));
        addressField = new JTextField(20);
        add(addressField);

        add(new JLabel("GPA:"));
        gpaField = new JTextField(5);
        add(gpaField);

        JButton addButton = new JButton("Add Student");
        JButton saveButton = new JButton("Save to .txt file");
        add(addButton);
        add(saveButton);

        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false); // Make the text area non-editable
        add(new JScrollPane(displayArea)); // Add scroll pane around the text area

        // Add action listener to the Add button
        addButton.addActionListener(e -> addStudent());

        // Add action listener to the Save button
        saveButton.addActionListener(e -> saveStudentsToFile());

        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String gpaText = gpaField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || gpaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; // Don't proceed further
        }

        try {
            double GPA = Double.parseDouble(gpaText);

            if (GPA < 0.0 || GPA > 4.0) {
                JOptionPane.showMessageDialog(this, "GPA must be between 0.0 and 4.0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If everything is valid, add the student
            Student student = new Student(name, address, GPA);
            studentList.add(student);
            displayStudents(); // Update display
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid GPA. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayStudents() {
        StringBuilder builder = new StringBuilder();
        for (Student s : studentList) {
            builder.append(s.toString()).append("\n");
        }
        displayArea.setText(builder.toString());
    }

    private void saveStudentsToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                // Sort the student list by name directly, using a lambda expression for comparison
                List<String> lines = studentList.stream()
                        .sorted((student1, student2) -> student1.getName().compareTo(student2.getName()))
                        .map(Student::toString)
                        .collect(Collectors.toList());
                Files.write(Paths.get(fileToSave.getAbsolutePath()), lines, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(this, "Data saved successfully to " + fileToSave.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}