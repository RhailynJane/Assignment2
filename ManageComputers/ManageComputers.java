//Manage Computers program: maintains an ArrayList of Computer objects, 
//can be either Laptop or Desktop, but never just Computer-type objects themselves

import java.util.ArrayList;
import java.util.Scanner;

public class ManageComputers {

    // Added constants to ManageComputers class
    private static final String[] CPU_WHITELIST = {"i5", "i7"};
    private static final String[] RAM_WHITELIST = {"16", "32"};
    private static final String[] DISK_WHITELIST = {"512", "1024"};
    private static final String[] GPU_WHITELIST = {"Nvidia", "AMD"};
    private static final String[] SCREEN_WHITELIST = {"13", "14"};

    public static void main(String args[]) {

        //This ArrayList will hold all the computers in the system. Note that the type of objects expected in this
        //ArrayList are Computer, not Laptop or Desktop, but since those are subclasses of Computer they can be
        //stored in an ArrayLiust<Computer> anyway.
        ArrayList<Computer> computers = new ArrayList<Computer>(); 

        Scanner s = new Scanner(System.in);
        String menuOption="";

        do { //Start of main program loop

            //Show computer data in ArrayList<Computer>
            showComputers(computers); 

            //Display menu and return menu option selected by the user
            menuOption = getMenuSelection(s);

            switch(menuOption) {
                //Add new computer
                case "a": 

                    addComputer(computers,s);

                    break;

                //Delete a computer    
                case "d": 

                    deleteComputer(computers,s);

                    break;

                //Edit a computer    
                case "e": 

                    editComputer(computers, s);

                    break;

            }

        } while ( ! menuOption.equals("x") ); //Stop when "x" is entered

        s.close(); //Close keyboard scanner

    } //End of main

    //-----------------------------
    //Display menu and get user selection, return it
    private static String getMenuSelection(Scanner s) {
        String menuOption="";

        //Display menu options on-screen
        System.out.println("----------");
        System.out.println("A) Add Computer");
        System.out.println("D) Delete Computer");
        System.out.println("E) Edit Computer");
        System.out.println("X) eXit");
        System.out.println("----------");

        //Get menu selection from keyboard
        System.out.print("Enter menu selection:");
        menuOption = s.nextLine();

        menuOption = menuOption.toLowerCase(); //Make lower case for comparison purposes

        return menuOption;
    } //End of getMenuSelection

    //-----------------------------
    //Show data for all laptops and desktops stored in ArrayList<Computer> create in main() method
    private static void showComputers(ArrayList<Computer> computers) {
        int computerListNumber=0; //This variable is used to hold the "list number" for each computer, starting at 1.

        System.out.println("=========");

        System.out.println("LIST OF COMPUTERS:-");

        for (Computer c: computers) {

            computerListNumber++; //Increment list number for each computer

            //Call overridden toString() method for current object to get and display its data
            System.out.println(computerListNumber + ": " + c.toString());
        }

        System.out.println("=========");

    } //End of showComputers

    //-----------------------------
    // Add a new computer (immutable)
    private static void addComputer(ArrayList<ComputerType> computers, Scanner s) {
        System.out.println("ADDING COMPUTER:");
        System.out.print("Enter type ('L' for Laptop, 'D' for Desktop): ");
        String type = s.nextLine().trim().toLowerCase();

        String CPU = getValidatedInput(s, "Enter CPU (i5/i7): ", CPU_WHITELIST);
        String RAM = getValidatedInput(s, "Enter RAM (16/32): ", RAM_WHITELIST);
        String disk = getValidatedInput(s, "Enter Disk (512/1024): ", DISK_WHITELIST);

        switch (type) {
            case "l" -> {
                System.out.print("Enter screen size (e.g. 15\" IPS): ");
                String screen = s.nextLine();
                computers.add(new Laptop(CPU, RAM, disk, screen));
            }
            case "d" -> {
                String GPU = getValidatedInput(s, "Enter GPU (Nvidia/AMD): ", GPU_WHITELIST);
                computers.add(new Desktop(CPU, RAM, disk, GPU));
            }
            default -> System.out.println("Invalid type entered!");
        }
    }

    //-----------------------------
    // Delete a computer
    private static void deleteComputer(ArrayList<ComputerType> computers, Scanner s) {
        System.out.println("DELETE COMPUTER:");
        System.out.print("Enter number of computer to delete: ");

        try {
            int num = Integer.parseInt(s.nextLine());
            if (num >= 1 && num <= computers.size()) {
                computers.remove(num - 1);
                System.out.println("Computer deleted.");
            } else {
                System.out.println("Invalid number!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    //-----------------------------
    // Edit a computer (replace the immutable object)
    private static void editComputer(ArrayList<ComputerType> computers, Scanner s) {
        System.out.println("EDIT COMPUTER:");
        System.out.print("Enter number of computer to edit: ");

        try {
            int num = Integer.parseInt(s.nextLine());
            if (num < 1 || num > computers.size()) {
                System.out.println("Invalid number!");
                return;
            }

            ComputerType old = computers.get(num - 1);
            System.out.println("Editing: " + old);

            // Gather new values
            String CPU = getValidatedInput(s, "Enter new CPU (i5/i7): ", CPU_WHITELIST);
            String RAM = getValidatedInput(s, "Enter new RAM (16/32): ", RAM_WHITELIST);
            String disk = getValidatedInput(s, "Enter new Disk (512/1024): ", DISK_WHITELIST);

            if (old instanceof Laptop) {
                System.out.print("Enter new screen size: ");
                String screen = s.nextLine();
                computers.set(num - 1, new Laptop(CPU, RAM, disk, screen));
            } else if (old instanceof Desktop) {
                String GPU = getValidatedInput(s, "Enter new GPU (Nvidia/AMD): ", GPU_WHITELIST);
                computers.set(num - 1, new Desktop(CPU, RAM, disk, GPU));
            }

            System.out.println("Computer updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    } //End of editComputer

    //-----------------------------
    //Helper method to get data common to Laptop and Desktop (CPU, RAM and disk) objects. Returns a Computer-type object
    //holding these values as attribues
    private static Computer getComputerData(Scanner s) {
        String CPU="";
        String RAM="";
        String disk="";

        System.out.print("Enter CPU:");
        CPU = s.nextLine();

        System.out.print("Enter RAM:");
        RAM = s.nextLine();

        System.out.print("Enter Disk:");
        disk = s.nextLine();

        return new Computer(CPU,RAM,disk);

    } //End of getComputerData

    /**
     * Validates input against a whitelist array
     * @param input The user input to validate
     * @param whitelist Array of valid values
     * @return true if input is in whitelist, false otherwise
     */
    private static boolean isValidInput(String input, String[] whitelist) {
        for (String validValue : whitelist) {
            if (input.equals(validValue)) {
                return true;
            }
        }
        return false;
    } //End of isValidInput

    /**
     * Gets validated input from user, keeps prompting until valid
     * @param s Scanner object
     * @param prompt Message to display to user
     * @param whitelist Array of valid values
     * @return Valid user input
     */
    private static String getValidatedInput(Scanner s, String prompt, String[] whitelist) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            input = s.nextLine();

            if (isValidInput(input, whitelist)) {
                valid = true;
            } else {
                System.out.println("Invalid input! Please enter one of the following:");
                System.out.print("Valid options: ");
                for (int i = 0; i < whitelist.length; i++) {
                    System.out.print(whitelist[i]);
                    if (i < whitelist.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }
        return input;
    } //End of getValidatedInput

} //End of ManageComputer class