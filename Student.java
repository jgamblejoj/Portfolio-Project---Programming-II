public class Student {
	// Attributes
	private String name;
	private String address;
	private double GPA;
	
	// Constructor
	public Student(String name, String address, double GPA) {
		this.name = name;
		this.address = address;
		this.GPA = GPA;
	}
	
	// Methods
	// This will be a toString method to view the contents of the student object
	public String toString() {
	    return name + "-- GPA: " + String.format("%.2f", GPA) + ", " + address + "\n";
	}	
	// Getter for Student name
	public String getName() {
		return name;
	}
	
}