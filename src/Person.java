public class Person {
    private final String name;
    private final String surname;
    private final String email;

    // Constructor
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getters and setters
    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public String getEmail() {
        return email;
    }



    // Method to print person information
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }
}

