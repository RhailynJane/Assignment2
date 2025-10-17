/**
 * @author Gabrielle A.
 * @version 1.0
 * Date: 2024-06-10
 * Assignment 2 - Manage Computers
 *
 * Immutable desktop class using composition over inheritance
 * 
 * Desktop has a Computer and is immutable
 * 
 */

public final class Desktop {
    // desktop specific : immutable
    private final String gpuType;

    // composition where desktop "has-a" Computer 
    private final Computer computer;

    /**
     * Constructor for immutable Desktop 
     * 
     * @param CPU - i5 or i7
     * @param RAM - 16 or 32
     * @param disk - 512 or 1024
     * @param GPU - GPU type: Nvidia or AMD
     */

    public Desktop(String CPU, String RAM, String disk, String GPU) {

        // validate inputs ; will throw IllegalArgumentException if invalid
        this.computer = new Computer(CPU, RAM, disk);
        this.gpuType = validatedGPU(GPU);
    }

    // Validations 
    private static String validatedGPU(String GPU) {
        if (GPU == null) {
            throw new IllegalArgumentException("GPU Type cannot be null.");
        }

        String g = GPU.trim();
        if (g.isEmpty()) {
            throw new IllegalArgumentException("GPU Type cannot be empty.");
        }

        // for spelling variations
        if (g.equalsIgnoreCase("Nvidia")) return "Nvidia";
        if (g.equalsIgnoreCase("AMD")) return "AMD";


        throw new IllegalArgumentException("GPU Type must be either Nvidia or AMD");

    }

    // Computer getters
    public String getCPU() {
        return computer.getCPU();
    }

    public String getRAM() {
        return computer.getRAM();
    }

    public String getDisk() {
        return computer.getDisk();
    }


    // Desktop specific getters
    public String getGPUType() {
        return this.gpuType;
    }

    public boolean isNvidia() {
        return "Nvidia".equals(gpuType);
    }

    public boolean isAMD() {
        return "AMD".equals(gpuType);
    }

    @Override
    public String toString() {
        return "Type:Desktop\tCPU:" + getCPU() 
        + "\tRAM:" + getRAM() 
        + "\tDisk:" + getDisk() 
        + "\tGPU:" + getGPUType();
    }

}