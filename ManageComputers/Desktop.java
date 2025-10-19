// Desktop.java - Immutable Desktop class using COMPOSITION instead of inheritance
// Desktop HAS-A Computer (composition) rather than IS-A Computer (inheritance)
// Adds GPU attribute and delegates core data to its Computer instance.
public final class Desktop implements ComputerItem {
    private final Computer computer; // Composition: contains a Computer object
    private final String gpu;

    // Constructor accepting Computer object directly
    public Desktop(Computer computer, String gpu) {
        if (computer == null || gpu == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.computer = computer;
        this.gpu = gpu;
    }

    // Convenience constructor that creates Computer object internally
    public Desktop(String CPU, String RAM, String disk, String gpu) {
        this(new Computer(CPU, RAM, disk), gpu);
    }

    // Return the composed Computer object
    public Computer getComputer() {
        return computer;
    }

    // Desktop-specific getter
    public String getGpu() {
        return gpu;
    }

    // Format MUST match original exactly: Type:Desktop	CPU:i5	RAM:16	Disk:512	GPU:Nvidia
    @Override
    public String toString() {
        return "Type:Desktop\tCPU:" + computer.getCPU() + 
               "\tRAM:" + computer.getRAM() + 
               "\tDisk:" + computer.getDisk() + 
               "\tGPU:" + gpu;
    }
}