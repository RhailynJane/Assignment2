// Computer.java - Immutable class representing common computer specifications.
//  Contains CPU, RAM, and Disk information used by both Desktop and Laptop.
// This class is immutable: all fields are final and there are no setters
public final class Computer {
    private final String CPU;
    private final String RAM;
    private final String disk;

    // Constructor - all fields must be set at creation time
    public Computer(String CPU, String RAM, String disk) {
        if (CPU == null || RAM == null || disk == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.CPU = CPU;
        this.RAM = RAM;
        this.disk = disk;
    }

    // Getters only - no setters to maintain immutability
    public String getCPU() {
        return CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public String getDisk() {
        return disk;
    }
}