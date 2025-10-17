//Manage Computers program: maintains an ArrayList of Computer objects, 
//can be either Laptop or Desktop, but never just Computer-type objects themselves

import java.util.ArrayList;
import java.util.Scanner;

public class ManageComputers {

    // Added constants to ManageComputers class
    private static final String[] CPU_WHITELIST = { "i5", "i7" };
    private static final String[] RAM_WHITELIST = { "16", "32" };
    private static final String[] DISK_WHITELIST = { "512", "1024" };
    private static final String[] GPU_WHITELIST = { "Nvidia", "AMD" };
    private static final String[] SCREEN_WHITELIST = { "13", "14", "15", "17" };

    public static void main(String args[]) {
        // This ArrayList will hold all the computers in the system
        ArrayList<Object> computers = new ArrayList<Object>();

        Scanner s = new Scanner(System.in);
        String menuOption = "";

        do {
            // Show computer data in ArrayList
            showComputers(computers);

            // Display menu and return menu option selected by the user
            menuOption = getMenuSelection(s);

            switch (menuOption) {
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

    // -----------------------------
    // Display menu and get user selection, return it
    private static String getMenuSelection(Scanner s) {
        String menuOption = "";

        // Display menu options on-screen
        System.out.println("----------");
        System.out.println("A) Add Computer");
        System.out.println("D) Delete Computer");
        System.out.println("E) Edit Computer");
        System.out.println("X) eXit");
        System.out.println("----------");

        // Get menu selection from keyboard
        System.out.print("Enter menu selection:");
        menuOption = s.nextLine().toLowerCase();
        return menuOption;
    }

    private static void showComputers(ArrayList<Object> computers) {
        int computerListNumber = 0;

        System.out.println("=========");

        System.out.println("LIST OF COMPUTERS:-");

        for (Object computer : computers) {
            computerListNumber++;
            System.out.println(computerListNumber + ": " + computer.toString());
        }

        System.out.println("=========");
    }

    private static void addComputer(ArrayList<Object> computers, Scanner s) {
        System.out.println("ADDING COMPUTER:-");

        System.out.println("Enter type of computer to add ('L' for Laptop, 'D' for Desktop):");
        String computerType = s.nextLine().toLowerCase();

        switch (computerType) {
            case "l":
                addLaptop(computers, s);
                break;
            case "d":
                addDesktop(computers, s);
                break;

            // Invalid computer type to add entered
            default:

                System.out.println("Invalid computer type entered!");

        }
    }

    private static void addLaptop(ArrayList<Object> computers, Scanner s) {
        try {
            String cpu = getValidatedInput(s, "Enter CPU (i5, i7): ", CPU_WHITELIST);
            String ram = getValidatedInput(s, "Enter RAM (16, 32): ", RAM_WHITELIST);
            String disk = getValidatedInput(s, "Enter Disk (512, 1024): ", DISK_WHITELIST);
            String screenSize = getValidatedInput(s, "Enter screen size (13, 14, 15, 17): ", SCREEN_WHITELIST);

            Laptop laptop = new Laptop(cpu, ram, disk, screenSize);
            computers.add(laptop);
            System.out.println("Laptop added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding laptop: " + e.getMessage());
        }
    }

    private static void addDesktop(ArrayList<Object> computers, Scanner s) {
        try {
            String cpu = getValidatedInput(s, "Enter CPU (i5, i7): ", CPU_WHITELIST);
            String ram = getValidatedInput(s, "Enter RAM (16, 32): ", RAM_WHITELIST);
            String disk = getValidatedInput(s, "Enter Disk (512, 1024): ", DISK_WHITELIST);
            String gpu = getValidatedInput(s, "Enter GPU (Nvidia, AMD): ", GPU_WHITELIST);

            Desktop desktop = new Desktop(cpu, ram, disk, gpu);
            computers.add(desktop);
            System.out.println("Desktop added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding desktop: " + e.getMessage());
        }
    }

    private static void deleteComputer(ArrayList<Object> computers, Scanner s) {
        System.out.println("DELETE COMPUTER:-");

        if (computers.isEmpty()) {
            System.out.println("No computers to delete!");
            return;
        }

        System.out.print("Enter number of computer to delete:");
        try {
            int computerListNumberToDelete = Integer.parseInt(s.nextLine());

            if (computerListNumberToDelete >= 1 && computerListNumberToDelete <= computers.size()) {
                Object computerToDelete = computers.get(computerListNumberToDelete - 1);
                computers.remove(computerListNumberToDelete - 1);
                System.out.println("Computer deleted successfully: " + computerToDelete.toString());
            } else {
                System.out.println(
                        "Invalid computer number entered! Please enter a number between 1 and " + computers.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    private static void editComputer(ArrayList<Object> computers, Scanner s) {
        System.out.println("EDIT COMPUTER:-");

        if (computers.isEmpty()) {
            System.out.println("No computers to edit!");
            return;
        }

        System.out.print("Enter number of computer to edit:");
        try {
            int computerListNumberToEdit = Integer.parseInt(s.nextLine());

            if (computerListNumberToEdit >= 1 && computerListNumberToEdit <= computers.size()) {
                Object oldComputer = computers.get(computerListNumberToEdit - 1);
                Object newComputer = null;

                if (oldComputer instanceof Laptop) {
                    newComputer = editLaptop((Laptop) oldComputer, s);
                } else if (oldComputer instanceof Desktop) {
                    newComputer = editDesktop((Desktop) oldComputer, s);
                }

                if (newComputer != null) {
                    computers.set(computerListNumberToEdit - 1, newComputer);
                    System.out.println("Computer updated successfully!");
                }
            } else {
                System.out.println(
                        "Invalid computer number entered! Please enter a number between 1 and " + computers.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    private static Laptop editLaptop(Laptop oldLaptop, Scanner s) {
        System.out.println("Editing a Laptop (current values shown):");
        System.out.println("Current: " + oldLaptop.toString());
        System.out.println("Enter new values (press Enter to keep current value):");

        String currentCPU = oldLaptop.getCPU();
        String currentRAM = oldLaptop.getRAM();
        String currentDisk = oldLaptop.getDisk();
        String currentScreen = oldLaptop.getScreenSize();

        String newCPU = getInputWithDefault(s, "Enter CPU [" + currentCPU + "]: ", currentCPU, CPU_WHITELIST);
        String newRAM = getInputWithDefault(s, "Enter RAM [" + currentRAM + "]: ", currentRAM, RAM_WHITELIST);
        String newDisk = getInputWithDefault(s, "Enter Disk [" + currentDisk + "]: ", currentDisk, DISK_WHITELIST);
        String newScreen = getInputWithDefault(s, "Enter Screen Size [" + currentScreen + "]: ", currentScreen,
                SCREEN_WHITELIST);

        try {
            return new Laptop(newCPU, newRAM, newDisk, newScreen);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating laptop: " + e.getMessage());
            return null;
        }
    }

    private static Desktop editDesktop(Desktop oldDesktop, Scanner s) {
        System.out.println("Editing a Desktop (current values shown):");
        System.out.println("Current: " + oldDesktop.toString());
        System.out.println("Enter new values (press Enter to keep current value):");

        String currentCPU = oldDesktop.getCPU();
        String currentRAM = oldDesktop.getRAM();
        String currentDisk = oldDesktop.getDisk();
        String currentGPU = oldDesktop.getGPUType();

        String newCPU = getInputWithDefault(s, "Enter CPU [" + currentCPU + "]: ", currentCPU, CPU_WHITELIST);
        String newRAM = getInputWithDefault(s, "Enter RAM [" + currentRAM + "]: ", currentRAM, RAM_WHITELIST);
        String newDisk = getInputWithDefault(s, "Enter Disk [" + currentDisk + "]: ", currentDisk, DISK_WHITELIST);
        String newGPU = getInputWithDefault(s, "Enter GPU [" + currentGPU + "]: ", currentGPU, GPU_WHITELIST);

        try {
            return new Desktop(newCPU, newRAM, newDisk, newGPU);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating desktop: " + e.getMessage());
            return null;
        }
    }

    private static String getInputWithDefault(Scanner s, String prompt, String defaultValue, String[] whitelist) {
        while (true) {
            System.out.print(prompt);
            String input = s.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            if (isValidInput(input, whitelist)) {
                return input;
            } else {
                System.out.println("Invalid input! Please enter one of: " + String.join(", ", whitelist));
            }
        }
    }

    private static String getValidatedInput(Scanner s, String prompt, String[] whitelist) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            input = s.nextLine().trim();

            if (isValidInput(input, whitelist)) {
                valid = true;
            } else {
                System.out.println("Invalid input! Please enter one of: " + String.join(", ", whitelist));
            }
        }
        return input;
    }

    private static boolean isValidInput(String input, String[] whitelist) {
        for (String validValue : whitelist) {
            if (input.equals(validValue)) {
                return true;
            }
        }
        return false;
    }
}

// Simple Laptop class so ManageComputers can compile
class Laptop {
    private String cpu;
    private String ram;
    private String disk;
    private String screenSize;

    public Laptop(String cpu, String ram, String disk, String screenSize) {
        if (cpu == null || ram == null || disk == null || screenSize == null) {
            throw new IllegalArgumentException("Laptop parameters cannot be null");
        }
        this.cpu = cpu;
        this.ram = ram;
        this.disk = disk;
        this.screenSize = screenSize;
    }

    public String getCPU() {
        return cpu;
    }

    public String getRAM() {
        return ram;
    }

    public String getDisk() {
        return disk;
    }

    public String getScreenSize() {
        return screenSize;
    }

    @Override
    public String toString() {
        return String.format("Laptop [CPU=%s, RAM=%s, Disk=%s, Screen=%s]", cpu, ram, disk, screenSize);
    }
}

// Simple Desktop class so ManageComputers can compile
class Desktop {
    private String cpu;
    private String ram;
    private String disk;
    private String gpuType;

    public Desktop(String cpu, String ram, String disk, String gpuType) {
        if (cpu == null || ram == null || disk == null || gpuType == null) {
            throw new IllegalArgumentException("Desktop parameters cannot be null");
        }
        this.cpu = cpu;
        this.ram = ram;
        this.disk = disk;
        this.gpuType = gpuType;
    }

    public String getCPU() {
        return cpu;
    }

    public String getRAM() {
        return ram;
    }

    public String getDisk() {
        return disk;
    }

    public String getGPUType() {
        return gpuType;
    }

    @Override
    public String toString() {
        return String.format("Desktop [CPU=%s, RAM=%s, Disk=%s, GPU=%s]", cpu, ram, disk, gpuType);
    }
}