import java.util.InputMismatchException;
import java.util.Scanner;

public class w2051679_PlaneManagement {
    private static final int rows = 4;
    private static final int[] seats_per_row = {14, 12, 12, 14}; // 2D array to represent the seats on the plane
    private static final int[][] seats = new int[rows][];
    private static final Ticket[] tickets = new Ticket[rows * seats_per_row[0]+ seats_per_row[1] + seats_per_row[2] + seats_per_row[3]];   //Array to store Ticket objects.

    private static int ticketCount = 0; //Variable to keep track of the total number of tickets bought


    public static void main(String[] args) {
        initialize_seats();

        System.out.println("Welcome to the Plane Management application");

        //Creating a scanner object for user input
        Scanner scanner = new Scanner(System.in);
        int option ;

        do {
            System.out.println("**************************************************");
            System.out.println("*               Menu  Options                    *");
            System.out.println("**************************************************");
            System.out.println("1) Buy a seat");
            System.out.println("2) Cancel a seat");
            System.out.println("3) Find first available seat");
            System.out.println("4) Show seating plan");
            System.out.println("5) Print tickets information and total sales");
            System.out.println("6) Search ticket");
            System.out.println("0) Quit");
            System.out.println("**************************************************");

            try {
                System.out.println("Please select an option: ");
                option = scanner.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                return;
            }

            switch (option) {
                case 1:
                    buy_seat();
                    break;

                case 2:
                    cancel_seats();
                    break;

                case 3:
                    find_first_available();
                    break;

                case 4:
                    show_seating_plan();
                    break;

                case 5:
                    print_tickets_info();
                    break;

                case 6:
                    search_ticket();
                    break;

                case 0:
                    System.out.println("Quitting the program....");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

            System.out.println();
        } while (option != 0);
    }

    //Method to initialize the seats array
    private static void initialize_seats() {
        for (int i = 0; i < rows; i++) {
            //Create an array for each row with the specified number of seats.
            seats[i] = new int[seats_per_row[i]];
        }
    }


    private static void buy_seat() {
        Scanner scanner = new Scanner(System.in);

        // Asking user information
        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.print("Enter your surname: ");
        String surname = scanner.next();
        String email;
        while (true) {
            System.out.println("Enter your email : ");
            email = scanner.next().toLowerCase();
            if (!email.contains("@")) {
                System.out.println("email invalid");
            } else {
                break;
            }
        }

        try{
            char rowLetter ;
            //Asking user for valid row letter
            System.out.print("Enter row letter (A, B, C, or D): ");
            rowLetter = scanner.next().toUpperCase().charAt(0);

            int rowNumber = rowLetter - 'A'; // Convert row letter to array index

            if (rowNumber < 0 || rowNumber >= rows) {
                System.out.println("Invalid row.");
                return;
            }
            Person person = new Person(name, surname, email);
            String rowString = String.valueOf(rowLetter);
            //Display ticket prices based on rows
            if (rowString.equals("A") | rowString.equals("B") | rowString.equals("C") | rowString.equals("D")) {
                if (rowString.equals("A") || rowString.equals("D")) {
                    System.out.println("Tickets Prices");
                    System.out.println("Seat no    price");
                    System.out.println("  1-5      £200 ");
                    System.out.println("  6-9      £150 ");
                    System.out.println(" 10-14     £180 ");
                } else {
                    System.out.println("Tickets Prices");
                    System.out.println("Seat no    price");
                    System.out.println("  1-5      £200 ");
                    System.out.println("  6-9      £150 ");
                    System.out.println(" 10-12     £180 ");
                }
            }

            //Asking a valid seat number from the user.
            System.out.println("Enter a seat number: ");
            int seatNumber = scanner.nextInt();

            if (seatNumber < 1 || seatNumber > seats_per_row[rowNumber]) {
                System.out.println("Invalid seat number.");
                return;
            }

            if (seats[rowNumber][seatNumber - 1] == 1) {
                System.out.println("Seat is already sold.");
                return;
            }

            //Determining ticket price based on the seat number
            int price;
            if (seatNumber <= 5) {
                price = 200;
            } else if (seatNumber <= 9) {
                price = 150;
            } else {
                price = 180;
            }


            //Creating a ticket object and associate it with the person
            Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);    // Creating a Ticket object and associate it with the Person
            tickets[ticketCount++] = ticket; //Add ticket to the tickets array
            ticket.save(); //Save ticket information
            ticket.printInfo(); //Print ticket details
            seats[rowNumber][seatNumber - 1] = 1; //Mark seat as sold
            System.out.println("Seat " + (rowLetter + "" + seatNumber) + " purchased successfully.");
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input type!");
        }
    }


    public static void cancel_seats() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter row letter (A, B, C, or D): ");
            char row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row.");
                return;
            }

            System.out.print("Enter seat number: ");
            int seatNumber = scanner.nextInt();

            int rowIndex = row - 'A';
            boolean found = false;

            //Validating row and seat number
            if (seatNumber > 0 && seatNumber <= seats[rowIndex].length) {
                for (int i = 0; i < ticketCount; i++) {
                    Ticket ticket = tickets[i];
                    //Check if ticket matches the input row and seat number
                    if (ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                        //Remove ticket from the ticket array
                        for (int j = i; j < ticketCount - 1; j++) {
                            tickets[j] = tickets[j + 1];
                        }
                        tickets[ticketCount - 1] = null;
                        ticketCount--;
                        //Marking seat as available
                        seats[rowIndex][seatNumber - 1] = 0;
                        System.out.println("Your seat " + row + seatNumber + " was successfully canceled.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("No such seat was booked.");
                }
            } else {
                System.out.println("Invalid  seat number.");
            }
        }catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input type !");
        }
    }


    private static void find_first_available() {
        //Iterate through the array to find the first available seat
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats_per_row[i]; j++) {
                if (seats[i][j] == 0) {
                    char row = (char) ('A' + i);
                    System.out.println("First available seat: " + row + " " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }



    private static void show_seating_plan() {
        //Printing the seating plan,marking unsold seats with '0' and sold seats with 'X'
        System.out.println("Seating Plan:");
        for (int i = 0; i < rows; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < seats_per_row[i]; j++) {
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }


    public static void print_tickets_info() {
        double totalSales = 0.0;

        //Printing information for each ticket and calculating the total sales
        System.out.println("Tickets Information:");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                System.out.println("Ticket: " + ticket.getRow() + ticket.getSeat());
                System.out.println("Price: £" + ticket.getPrice());
                System.out.println("Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                System.out.println("Email: " + ticket.getPerson().getEmail());
                System.out.println();

                totalSales += ticket.getPrice();
            }
        }
        System.out.println("Total Sales: £" + totalSales);
    }


    public static void search_ticket() {
        Scanner scanner = new Scanner(System.in);

        try {
            //Asking user for row and seat number that he/she wants to search
            System.out.print("Enter a row letter (A, B, C, or D): ");
            char row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row.");
                return;
            }

            System.out.print("Enter a seat number: ");
            int seatNumber = scanner.nextInt();

            int rowNumber = row - 'A';
            boolean found = false;
            //Validating row and seat number
            if (seatNumber > 0 && seatNumber <= seats[rowNumber].length) {
                for (Ticket ticket : tickets) {
                    //Checking if ticket matches the input row and seat number
                    if (ticket != null && ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                        //Printing ticket information
                        System.out.println("\n This seat has already been sold!");
                        System.out.println("\n Ticket Information:");
                        System.out.println("Seat: " + row + ticket.getSeat());
                        System.out.println("Price: £" + ticket.getPrice());
                        System.out.println("Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                        System.out.println("Email: " + ticket.getPerson().getEmail());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("This seat is available");
                }

            } else {
                System.out.println("Enter a valid seat number!");

            }
        }catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input type !");
        }
    }
}