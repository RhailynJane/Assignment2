/**
 * Immutable Computer class.
 *
 * <p>
 * This class uses the <b>Immutable Design Pattern</b>, meaning that
 * once a Computer object is created, its state cannot be changed.
 *
 * <p>
 * <b>Immutable Design Rules Applied:</b>
 * <ul>
 * <li>The class is declared as {@code final} to prevent subclassing.</li>
 * <li>All fields are declared {@code private final} so their values cannot
 * change after initialization.</li>
 * <li>No setter methods are provided — data is assigned only once via the
 * constructor.</li>
 * <li>Getter methods provide read-only access to field values.</li>
 * </ul>
 *
 * <p>
 * <b>Benefits of Immutability:</b>
 * <ul>
 * <li>Thread-safe without synchronization.</li>
 * <li>Reliable and easy to reason about ,state never changes.</li>
 * <li>Ideal for caching and sharing between objects.</li>
 * </ul>
 */
public class Computer {

    // All fields are private and final
    private final String CPU;
    private final String RAM;
    private final String disk;

    /**
     * Constructor that accepts all required parameters.
     * Initializes all fields once and makes the object immutable.
     *
     * @param CPU  the CPU type ( "Intel i7")
     * @param RAM  the RAM size ( "16GB")
     * @param disk the disk capacity ( "512GB SSD")
     */
    public Computer(String CPU, String RAM, String disk) {
        this.CPU = CPU;
        this.RAM = RAM;
        this.disk = disk;
    }

    // Getter methods only (no setters)
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
