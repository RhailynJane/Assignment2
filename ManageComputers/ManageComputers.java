import java.util.ArrayList;
import java.util.Scanner;

public class ManageComputers {

    // Validation constants
    private static final String[] CPU_WHITELIST = { "i5", "i7" };
    private static final String[] RAM_WHITELIST = { "16", "32" };
    private static final String[] DISK_WHITELIST = { "512", "1024" };
    private static final String[] GPU_WHITELIST = { "Nvidia", "AMD" };
    private static final String[] SCREEN_WHITELIST = { "13", "14", "15", "17" };

    public static void main(String args[]) {
        ArrayList<Computer> computers = new ArrayList<Computer>();
        Scanner s = new Scanner(System.in);
        String menuOption = "";

        do {
            showComputers(computers);
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
    // DELETE OPERATION - Refactored for new data structure
    // -----------------------------
    private static void deleteComputer(ArrayList<Computer> computers, Scanner s) {
        System.out.println("DELETE COMPUTER:-");

        // Edge Case 1: Empty list
        if (computers.isEmpty()) {
            System.out.println("No computers to delete!");
            return;
        }

        System.out.print("Enter number of computer to delete: ");
        try {
            int computerListNumberToDelete = Integer.parseInt(s.nextLine());

            // Edge Case 2: Invalid number (negative, zero, or beyond list size)
            if (computerListNumberToDelete >= 1 && computerListNumberToDelete <= computers.size()) {
                Computer computerToDelete = computers.get(computerListNumberToDelete - 1);

                // Remove the computer and confirm deletion
                computers.remove(computerListNumberToDelete - 1);
                System.out.println("Computer deleted successfully: " + computerToDelete.toString());
            } else {
                // Edge Case 3: Number out of valid range
                System.out.println("Invalid computer number! Please enter a number between 1 and " + computers.size());
            }
        } catch (NumberFormatException e) {
            // Edge Case 4: Non-numeric input
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    // -----------------------------
    // EDIT OPERATION - Redesigned for immutable objects (Create-New-Object
    // Approach)
    // -----------------------------
    private static void editComputer(ArrayList<Computer> computers, Scanner s) {
        System.out.println("EDIT COMPUTER:-");

        // Edge Case 1: Empty list
        if (computers.isEmpty()) {
            System.out.println("No computers to edit!");
            return;
        }

        System.out.print("Enter number of computer to edit: ");
        try {
            int computerListNumberToEdit = Integer.parseInt(s.nextLine());

            // Edge Case 2: Invalid number
            if (computerListNumberToEdit >= 1 && computerListNumberToEdit <= computers.size()) {
                Computer oldComputer = computers.get(computerListNumberToEdit - 1);
                Computer newComputer = null;

                // Create new object based on type (immutable approach)
                if (oldComputer instanceof Laptop) {
                    newComputer = createNewLaptopFromEdit((Laptop) oldComputer, s);
                } else if (oldComputer instanceof Desktop) {
                    newComputer = createNewDesktopFromEdit((Desktop) oldComputer, s);
                } else {
                    System.out.println("Unknown computer type!");
                    return;
                }

                // Replace old object with new one
                if (newComputer != null) {
                    computers.set(computerListNumberToEdit - 1, newComputer);
                    System.out.println("Computer updated successfully!");
                    System.out.println("Old: " + oldComputer.toString());
                    System.out.println("New: " + newComputer.toString());
                }
            } else {
                // Edge Case 3: Number out of range
                System.out.println("Invalid computer number! Please enter a number between 1 and " + computers.size());
            }
        } catch (NumberFormatException e) {
            // Edge Case 4: Non-numeric input
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    // -----------------------------
    // CREATE-NEW-OBJECT APPROACH FOR LAPTOP EDITING
    // -----------------------------
    private static Laptop createNewLaptopFromEdit(Laptop oldLaptop, Scanner s) {
        System.out.println("EDITING LAPTOP (Create New Object Approach):");
        System.out.println("Current: " + oldLaptop.toString());
        System.out.println("Enter new values (press Enter to keep current value):");

        // Get current values
        String currentCPU = oldLaptop.getCPU();
        String currentRAM = oldLaptop.getRAM();
        String currentDisk = oldLaptop.getDisk();
        String currentScreen = oldLaptop.getScreenSize();

        // Get new values with validation and default handling
        String newCPU = getValidatedInputWithDefault(s, "CPU", currentCPU, CPU_WHITELIST);
        String newRAM = getValidatedInputWithDefault(s, "RAM", currentRAM, RAM_WHITELIST);
        String newDisk = getValidatedInputWithDefault(s, "Disk", currentDisk, DISK_WHITELIST);
        String newScreen = getValidatedInputWithDefault(s, "Screen Size", currentScreen, SCREEN_WHITELIST);

        try {
            // Create new immutable Laptop object
            return new Laptop(newCPU, newRAM, newDisk, newScreen);
        } catch (IllegalArgumentException e) {
            // Edge Case: Object creation failure
            System.out.println("Error creating new laptop: " + e.getMessage());
            return null;
        }
    }

    // -----------------------------
    // CREATE-NEW-OBJECT APPROACH FOR DESKTOP EDITING
    // -----------------------------
    private static Desktop createNewDesktopFromEdit(Desktop oldDesktop, Scanner s) {
        System.out.println("EDITING DESKTOP (Create New Object Approach):");
        System.out.println("Current: " + oldDesktop.toString());
        System.out.println("Enter new values (press Enter to keep current value):");

        // Get current values
        String currentCPU = oldDesktop.getCPU();
        String currentRAM = oldDesktop.getRAM();
        String currentDisk = oldDesktop.getDisk();
        String currentGPU = oldDesktop.getGPUType();

        // Get new values with validation and default handling
        String newCPU = getValidatedInputWithDefault(s, "CPU", currentCPU, CPU_WHITELIST);
        String newRAM = getValidatedInputWithDefault(s, "RAM", currentRAM, RAM_WHITELIST);
        String newDisk = getValidatedInputWithDefault(s, "Disk", currentDisk, DISK_WHITELIST);
        String newGPU = getValidatedInputWithDefault(s, "GPU", currentGPU, GPU_WHITELIST);

        try {
            // Create new immutable Desktop object
            return new Desktop(newCPU, newRAM, newDisk, newGPU);
        } catch (IllegalArgumentException e) {
            // Edge Case: Object creation failure
            System.out.println("Error creating new desktop: " + e.getMessage());
            return null;
        }
    }

    // -----------------------------
    // INPUT VALIDATION INTEGRATION FOR EDIT PROCESS
    // -----------------------------
    private static String getValidatedInputWithDefault(Scanner s, String fieldName, String defaultValue,
            String[] whitelist) {
        while (true) {
            System.out.print(fieldName + " [" + defaultValue + "]: ");
            String input = s.nextLine().trim();

            // Allow empty input to keep current value
            if (input.isEmpty()) {
                return defaultValue;
            }

            // Validate against whitelist
            if (isValidInput(input, whitelist)) {
                return input;
            } else {
                System.out.println("Invalid " + fieldName + "! Must be one of: " + String.join(", ", whitelist));
            }
        }
    }

    // -----------------------------
    // EXISTING METHODS (Keep these as they are)
    // -----------------------------
    private static String getMenuSelection(Scanner s) {
        System.out.println("----------");
        System.out.println("A) Add Computer");
        System.out.println("D) Delete Computer");
        System.out.println("E) Edit Computer");
        System.out.println("X) exit");
        System.out.println("----------");
        System.out.print("Enter menu selection: ");
        return s.nextLine().toLowerCase();
    }

    private static void showComputers(ArrayList<Computer> computers) {
        System.out.println("=========");
        System.out.println("LIST OF COMPUTERS:-");

        if (computers.isEmpty()) {
            System.out.println("No computers in the system.");
        } else {
            for (int i = 0; i < computers.size(); i++) {
                System.out.println((i + 1) + ": " + computers.get(i).toString());
            }
        }
        System.out.println("=========");
    }

    private static void addComputer(ArrayList<Computer> computers, Scanner s) {
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
            default:
                System.out.println("Invalid computer type entered!");
        }
    }

    private static void addLaptop(ArrayList<Computer> computers, Scanner s) {
        try {
            String cpu = getValidatedInput(s, "Enter CPU (i5, i7): ", CPU_WHITELIST);
            String ram = getValidatedInput(s, "Enter RAM (16, 32): ", RAM_WHITELIST);
            String disk = getValidatedInput(s, "Enter Disk (512, 1024): ", DISK_WHITELIST);
            String screenSize = getValidatedInput(s, "Enter screen size (13, 14, 15, 17): ", SCREEN_WHITELIST);

            computers.add(new Laptop(cpu, ram, disk, screenSize));
            System.out.println("Laptop added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding laptop: " + e.getMessage());
        }
    }

    private static void addDesktop(ArrayList<Computer> computers, Scanner s) {
        try {
            String cpu = getValidatedInput(s, "Enter CPU (i5, i7): ", CPU_WHITELIST);
            String ram = getValidatedInput(s, "Enter RAM (16, 32): ", RAM_WHITELIST);
            String disk = getValidatedInput(s, "Enter Disk (512, 1024): ", DISK_WHITELIST);
            String gpu = getValidatedInput(s, "Enter GPU (Nvidia, AMD): ", GPU_WHITELIST);

            computers.add(new Desktop(cpu, ram, disk, gpu));
            System.out.println("Desktop added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding desktop: " + e.getMessage());
        }
    }

    private static String getValidatedInput(Scanner s, String prompt, String[] whitelist) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = s.nextLine().trim();
            if (isValidInput(input, whitelist)) {
                return input;
            } else {
                System.out.println("Invalid input! Please enter one of: " + String.join(", ", whitelist));
            }
        }
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