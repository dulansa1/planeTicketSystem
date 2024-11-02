import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ticket {
    private final char row;
    private final int seat;
    private final double price;
    private final Person person;

    // Constructor
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters and setters
    public char getRow() {
        return row;
    }



    public int getSeat() {
        return seat;
    }



    public double getPrice() {
        return price;
    }



    public Person getPerson() {
        return person;
    }



    // Method to print ticket information
    public void printInfo() {
        System.out.println("Ticket Info:");
        System.out.println("Row: "+ row + ", Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Holder: ");
        person.printInfo();
    }

    public void save() {
        String filename = row + Integer.toString(seat) + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Ticket Information:");
            writer.println("Row: " + row);
            writer.println("Seat: " + seat);
            writer.println("Price: £" + price);
            writer.println("Passenger: " + person.getName() + " " + person.getSurname());
            writer.println("Email: " + person.getEmail());
        } catch (IOException e) {
            System.out.println("Error occurred while saving the ticket: " + e.getMessage());
        }
    }
}