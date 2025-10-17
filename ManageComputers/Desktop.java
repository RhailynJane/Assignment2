/**
 * @author Gabrielle A.
 * @version 1.0
 *          Date: 2024-06-10
 *          Assignment 2 - Manage Computers
 *
 *          Immutable desktop class using composition over inheritance
 * 
 *          Desktop has a Computer and is immutable
 * 
 */

public final class Desktop {
    // desktop specific : immutable
    private final String gpuType;

    // composition where desktop "has-a" Computer
    private final Computer computer;

    /**
     * Constructor for immutable Desktop
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
        if (g.equalsIgnoreCase("Nvidia"))
            return "Nvidia";
        if (g.equalsIgnoreCase("AMD"))
            return "AMD";

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

    // Implement equals and hashCode for proper immutability
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Desktop desktop = (Desktop) o;
        return gpuType.equals(desktop.gpuType) &&
                getCPU().equals(desktop.getCPU()) &&
                getRAM().equals(desktop.getRAM()) &&
                getDisk().equals(desktop.getDisk());
    }

    @Override
    public int hashCode() {
        int result = gpuType.hashCode();
        result = 31 * result + getCPU().hashCode();
        result = 31 * result + getRAM().hashCode();
        result = 31 * result + getDisk().hashCode();
        return result;
    }
}