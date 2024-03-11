import java.util.Scanner;
import java.util.LinkedList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        // Scanner object
        Scanner scr = new Scanner(System.in);
        // Student object linked list
        LinkedList<Student> studentList = new LinkedList<>();
        // Attributes
        boolean mainLoop = true;
        double userNumInput;

        /* Main Loop -- this loop prompts the user for the input of student data, which 
         * instantiates new student objects, until '' is pressed, stopping the loop
         */
        System.out.println("Hello and welcome to the Student Directory System");
        System.out.println();
        while (mainLoop) {
            // System message -- prompts user for input to begin or quit
            System.out.print("MAIN MENU --\n\n" +
            				"\t'1' to ENTER NEW STUDENT DATA\n " +
            				"\t'2' to VIEW the updated directory\n " +
            				"\t'3' to SAVE data to .txt file\n " +
            				"\t'0' to QUIT");
            userNumInput = scr.nextInt();
            
            // Switch Statement to handle Main Menu options
            switch ((int) userNumInput) {
	            case 0: // Quit
	                // Prompt user to respond and ensure they want to quit
	                System.out.println("Are you sure?");
	                System.out.println("(Y)es or (N)o");
	                scr.nextLine(); // Consume newline left-over
	                String quitPrompt = scr.nextLine();
	                if (quitPrompt.equalsIgnoreCase("Y")) {
	                    System.out.println("Goodbye.");
	                    mainLoop = false;
	                    scr.close();
	                }
	                break;
	            case 1: // Enter new student data
	                scr.nextLine(); // Consume any leftover newline characters
	                System.out.println("Let's begin.");

	                System.out.println("Enter student name: ");
	                String name = scr.nextLine();

	                System.out.println("Enter student address: ");
	                String address = scr.nextLine();

	                double GPA = -1; // Initialize GPA with an invalid value to enter the loop
	                // Loop until a valid GPA is entered
	                while (GPA < 0.0 || GPA > 4.0) {
	                    System.out.println("Enter student GPA (0.0 to 4.0): ");
	                    while (!scr.hasNextDouble()) { // Check if the next token is a double
	                        System.out.println("Invalid input. Please enter a numeric value for GPA.");
	                        scr.next(); // Consume the non-double input
	                    }
	                    GPA = scr.nextDouble(); // Read the numeric input
	                    // Check if GPA is out of bounds
	                    if (GPA < 0.0 || GPA > 4.0) {
	                        System.out.println("GPA must be between 0.0 and 4.0. Please try again.");
	                    }
	                }
	                scr.nextLine(); // Consume the newline after the double input

	                // GPA is now guaranteed to be valid
	                Student student = new Student(name, address, GPA);
	                studentList.add(student);
	                System.out.println("\nThe student has been successfully created and added to the directory.\n");
	                break;
                case 2: // View the updated directory
                	System.out.println("Student List --");
                    System.out.println("\n" + studentList + "\n");
                    break;
                case 3: // Save sorted data to .txt file on Desktop
                    // Sort the LinkedList by student name in ascending order
                    studentList.sort(Comparator.comparing((Student s) -> s.getName()));

                    // Convert the sorted list to a LinkedList of Strings
                    LinkedList<String> sortedStudentStrings = studentList.stream()
                        .map(Student::toString)
                        .collect(Collectors.toCollection(LinkedList::new));

                    // Define the path to the desktop or any specific location
                    // Update the path according to your OS and username
                    String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "sortedLinkedList.txt";
                    Path path = Paths.get(desktopPath);
                    
                    try {
                        Files.write(path, sortedStudentStrings, StandardCharsets.UTF_8);
                        System.out.println("Successfully wrote the sorted list to the file on the Desktop.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    break;               
                // Default case handles erroneous user input     	
                default:
                    System.out.println("Invalid input. Please try again...");
                    break;
            }
        }
    }
}
