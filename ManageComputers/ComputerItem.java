// ComputerItem.java
/**
 * Interface for all computer item types (Desktop, Laptop).
 * Ensures each provides access to its composed Computer object.
 */
public interface ComputerItem {
    /**
     * Returns a user-friendly single-line description (used in listing).
     */
    String toString();

    /**
     * Returns the composed Computer object for accessing common properties.
     */
    Computer getComputer();
}
