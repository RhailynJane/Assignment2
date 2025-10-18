import java.util.ArrayList;
import java.util.Scanner;

public class ManageComputers {

    // Enhanced validation constants
    private static final String[] COMPANY_WHITELIST = { "Acer", "Asus", "HP", "Dell", "Lenovo" };
    private static final String[] CPU_WHITELIST = { "i3", "i5", "i7", "i9", "Ryzen 5", "Ryzen 7", "Ryzen 9" };
    private static final String[] RAM_WHITELIST = { "8", "16", "32", "64" };
    private static final String[] DISK_WHITELIST = { "256", "512", "1024", "2048" };
    private static final String[] GPU_WHITELIST = { "Nvidia RTX 3060", "Nvidia RTX 4070", "AMD RX 6700", "Intel Arc" };
    private static final String[] SCREEN_WHITELIST = { "13", "14", "15", "16", "17", "27", "32" };

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
    // DELETE OPERATION
    // -----------------------------
    private static void deleteComputer(ArrayList<Computer> computers, Scanner s) {
        System.out.println("DELETE COMPUTER:-");

        if (computers.isEmpty()) {
            System.out.println("No computers to delete!");
            return;
        }

        System.out.print("Enter number of computer to delete: ");
        try {
            int computerListNumberToDelete = Integer.parseInt(s.nextLine());

            if (computerListNumberToDelete >= 1 && computerListNumberToDelete <= computers.size()) {
                Computer computerToDelete = computers.get(computerListNumberToDelete - 1);
                computers.remove(computerListNumberToDelete - 1);
                System.out.println("Computer deleted successfully: " + computerToDelete.toString());
            } else {
                System.out.println("Invalid computer number! Please enter a number between 1 and " + computers.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    // -----------------------------
    // EDIT OPERATION
    // -----------------------------
    private static void editComputer(ArrayList<Computer> computers, Scanner s) {
        System.out.println("EDIT COMPUTER:-");

        if (computers.isEmpty()) {
            System.out.println("No computers to edit!");
            return;
        }

        System.out.print("Enter number of computer to edit: ");
        try {
            int computerListNumberToEdit = Integer.parseInt(s.nextLine());

            if (computerListNumberToEdit >= 1 && computerListNumberToEdit <= computers.size()) {
                Computer oldComputer = computers.get(computerListNumberToEdit - 1);
                Computer newComputer = null;

                if (oldComputer instanceof Laptop) {
                    newComputer = createNewLaptopFromEdit((Laptop) oldComputer, s);
                } else if (oldComputer instanceof Desktop) {
                    newComputer = createNewDesktopFromEdit((Desktop) oldComputer, s);
                } else {
                    System.out.println("Unknown computer type!");
                    return;
                }

                if (newComputer != null) {
                    computers.set(computerListNumberToEdit - 1, newComputer);
                    System.out.println("Computer updated successfully!");
                    System.out.println("Old: " + oldComputer.toString());
                    System.out.println("New: " + newComputer.toString());
                }
            } else {
                System.out.println("Invalid computer number! Please enter a number between 1 and " + computers.size());
            }
        } catch (NumberFormatException e) {
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
        String currentCompany = oldLaptop.getCompany();
        String currentCPU = oldLaptop.getCPU();
        String currentRAM = oldLaptop.getRAM();
        String currentDisk = oldLaptop.getDisk();
        String currentScreen = oldLaptop.getScreenSize();

        // Get new values with validation and default handling
        String newCompany = getValidatedInputWithDefault(s, "Company", currentCompany, COMPANY_WHITELIST);
        String newCPU = getValidatedInputWithDefault(s, "CPU", currentCPU, CPU_WHITELIST);
        String newRAM = getValidatedInputWithDefault(s, "RAM (GB)", currentRAM, RAM_WHITELIST);
        String newDisk = getValidatedInputWithDefault(s, "Disk (GB)", currentDisk, DISK_WHITELIST);
        String newScreen = getValidatedInputWithDefault(s, "Screen Size (inches)", currentScreen, SCREEN_WHITELIST);

        try {
            return new Laptop(newCompany, newCPU, newRAM, newDisk, newScreen);
        } catch (IllegalArgumentException e) {
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
        String currentCompany = oldDesktop.getCompany();
        String currentCPU = oldDesktop.getCPU();
        String currentRAM = oldDesktop.getRAM();
        String currentDisk = oldDesktop.getDisk();
        String currentGPU = oldDesktop.getGPUType();

        // Get new values with validation and default handling
        String newCompany = getValidatedInputWithDefault(s, "Company", currentCompany, COMPANY_WHITELIST);
        String newCPU = getValidatedInputWithDefault(s, "CPU", currentCPU, CPU_WHITELIST);
        String newRAM = getValidatedInputWithDefault(s, "RAM (GB)", currentRAM, RAM_WHITELIST);
        String newDisk = getValidatedInputWithDefault(s, "Disk (GB)", currentDisk, DISK_WHITELIST);
        String newGPU = getValidatedInputWithDefault(s, "GPU", currentGPU, GPU_WHITELIST);

        try {
            return new Desktop(newCompany, newCPU, newRAM, newDisk, newGPU);
        } catch (IllegalArgumentException e) {
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

            if (input.isEmpty()) {
                return defaultValue;
            }

            if (isValidInput(input, whitelist)) {
                return input;
            } else {
                System.out.println("Invalid " + fieldName + "! Must be one of: " + String.join(", ", whitelist));
            }
        }
    }

    // -----------------------------
    // MENU AND DISPLAY METHODS
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

    private static boolean isValidInput(String input, String[] whitelist) {
        for (String validValue : whitelist) {
            if (input.equals(validValue)) {
                return true;
            }
        }
        return false;
    }
}