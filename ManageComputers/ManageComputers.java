// ManageComputers.java
/*
 * ManageComputers - Main class to manage a list of computers (Laptops and Desktops).
 * Provides a console menu to add, delete, and edit computers.
 * Uses composition to handle common computer properties via the Computer class.
 * Implements input validation against predefined whitelists.
 * Maintains immutability of computer objects.
 */
import java.util.ArrayList;
import java.util.Scanner;

public class ManageComputers {

    private static final String[] CPU_WHITELIST = {"i5", "i7"};
    private static final String[] RAM_WHITELIST = {"16", "32"};
    private static final String[] DISK_WHITELIST = {"512", "1024"};
    private static final String[] GPU_WHITELIST = {"Nvidia", "AMD"};
    private static final String[] SCREEN_WHITELIST = {"13", "14"};

    public static void main(String args[]) {
        ArrayList<ComputerItem> computers = new ArrayList<ComputerItem>();
        Scanner s = new Scanner(System.in);
        String menuOption = "";

        do {
            showComputers(computers);
            menuOption = getMenuSelection(s);

            switch(menuOption) {
                case "a": 
                    addComputer(computers, s);
                    break;
                case "d": 
                    deleteComputer(computers, s);
                    break;
                case "e": 
                    editComputer(computers, s);
                    break;
            }

        } while (!menuOption.equals("x"));

        s.close();
    }

    private static String getMenuSelection(Scanner s) {
        String menuOption = "";

        System.out.println("----------");
        System.out.println("A) Add Computer");
        System.out.println("D) Delete Computer");
        System.out.println("E) Edit Computer");
        System.out.println("X) eXit");
        System.out.println("----------");

        System.out.print("Enter menu selection:");
        menuOption = s.nextLine();
        menuOption = menuOption.toLowerCase();

        return menuOption;
    }

    // Displays the list of computers with numbering
    private static void showComputers(ArrayList<ComputerItem> computers) {
        int computerListNumber = 0;

        System.out.println("=========");
        System.out.println("LIST OF COMPUTERS:-");

        for (ComputerItem c : computers) {
            computerListNumber++;
            System.out.println(computerListNumber + ": " + c.toString());
        }

        System.out.println("=========");
    }

    // Adds a new computer (Laptop or Desktop) based on user input
    private static void addComputer(ArrayList<ComputerItem> computers, Scanner s) {
        String computerType = "";

        System.out.println("ADDING COMPUTER:-");

        System.out.println("Enter type of computer to add ('L' for Laptop, 'D' for Desktop):");
        computerType = s.nextLine();
        computerType = computerType.toLowerCase();

        String CPU = getValidatedInput(s, "Enter CPU:", CPU_WHITELIST);
        String RAM = getValidatedInput(s, "Enter RAM:", RAM_WHITELIST);
        String disk = getValidatedInput(s, "Enter Disk:", DISK_WHITELIST);

        switch(computerType) {
            case "l": 
                String screenSize = getValidatedInput(s, "Enter screen size:", SCREEN_WHITELIST);
                computers.add(new Laptop(CPU, RAM, disk, screenSize)); 
                break;
            
            case "d": 
                String GPUType = getValidatedInput(s, "Enter GPU:", GPU_WHITELIST);
                computers.add(new Desktop(CPU, RAM, disk, GPUType)); 
                break;

            default:
                System.out.println("Invalid computer type entered!");
        }
    }


    // Deletes a computer based on user input after whitelist validation
    private static void deleteComputer(ArrayList<ComputerItem> computers, Scanner s) {
        int computerListNumberToDelete = 0;

        System.out.println("DELETE COMPUTER:-");

        System.out.print("Enter number of computer to delete:");
        computerListNumberToDelete = Integer.parseInt(s.nextLine());

        if (computerListNumberToDelete >= 1 && computerListNumberToDelete <= computers.size()) {
            computers.remove(computerListNumberToDelete - 1); 
        }   
        else {
            System.out.println("Invalid computer number entered!");
        }
    }

    // Edits a computer based on user input
    private static void editComputer(ArrayList<ComputerItem> computers, Scanner s) {
        int computerListNumberToEdit = 0;
        String computerType = "";

        System.out.println("EDIT COMPUTER:-");

        System.out.print("Enter number of computer to edit:");
        computerListNumberToEdit = Integer.parseInt(s.nextLine());

        if (computerListNumberToEdit >= 1 && computerListNumberToEdit <= computers.size()) {

            if (computers.get(computerListNumberToEdit - 1) instanceof Laptop) { 
                computerType = "laptop";
            }
            else if (computers.get(computerListNumberToEdit - 1) instanceof Desktop) { 
                computerType = "desktop";
            }

            switch(computerType) {
                case "laptop": 
                    System.out.println("Editing a Laptop:");

                    String cpuLaptop = getValidatedInput(s, "Enter CPU:", CPU_WHITELIST);
                    String ramLaptop = getValidatedInput(s, "Enter RAM:", RAM_WHITELIST);
                    String diskLaptop = getValidatedInput(s, "Enter Disk:", DISK_WHITELIST);
                    String screenSize = getValidatedInput(s, "Enter screen size:", SCREEN_WHITELIST);

                    computers.set(computerListNumberToEdit - 1, 
                                new Laptop(cpuLaptop, ramLaptop, diskLaptop, screenSize));
                    break;

                case "desktop": 
                    System.out.println("Editing a Desktop:");

                    String cpuDesktop = getValidatedInput(s, "Enter CPU:", CPU_WHITELIST);
                    String ramDesktop = getValidatedInput(s, "Enter RAM:", RAM_WHITELIST);
                    String diskDesktop = getValidatedInput(s, "Enter Disk:", DISK_WHITELIST);
                    String GPUType = getValidatedInput(s, "Enter GPU:", GPU_WHITELIST);

                    computers.set(computerListNumberToEdit - 1, 
                                new Desktop(cpuDesktop, ramDesktop, diskDesktop, GPUType));
                    break;
            }
        }
        else {
            System.out.println("Invalid computer number entered!");
        }
    }

    private static String getValidatedInput(Scanner s, String prompt, String[] whitelist) {
        String input;
        
        while (true) {
            System.out.print(prompt);
            input = s.nextLine();
            
            if (isValidInput(input, whitelist)) {
                return input;
            }
            
            System.out.println("Invalid input! Please enter one of: " + 
                             String.join(", ", whitelist));
        }
    }

    private static boolean isValidInput(String input, String[] whitelist) {
        for (String validValue : whitelist) {
            if (validValue.equals(input)) {
                return true;
            }
        }
        return false;
    }

}