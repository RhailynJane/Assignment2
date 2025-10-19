// Laptop.java
/**
 * Immutable Laptop class using composition with Computer.
 * Adds Screen Size attribute and delegates core data to its Computer instance.
 */
public final class Laptop implements ComputerItem {
    private final Computer computer;
    private final String screenSize;

    public Laptop(Computer computer, String screenSize) {
        if (computer == null || screenSize == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.computer = computer;
        this.screenSize = screenSize;
    }

    public Laptop(String CPU, String RAM, String disk, String screenSize) {
        this(new Computer(CPU, RAM, disk), screenSize);
    }

    public Computer getComputer() {
        return computer;
    }

    public String getScreenSize() {
        return screenSize;
    }

    @Override
    public String toString() {
        return "Type:Laptop\tCPU:" + computer.getCPU() + 
               "\tRAM:" + computer.getRAM() + 
               "\tDisk:" + computer.getDisk() + 
               "\tScreen:" + screenSize;
    }
}