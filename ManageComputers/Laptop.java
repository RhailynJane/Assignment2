public final class Laptop extends Computer {
    // Laptop-specific field (immutable)
    private final String screenSize;

    // Constructor accepting all parameters
    public Laptop(String CPU, String RAM, String disk, String screenSize) {
        // Initialize computer fields via super - must be first statement
        super(CPU, RAM, disk);

        // Validate parameters after super call
        if (screenSize == null || screenSize.trim().isEmpty()) {
            throw new IllegalArgumentException("Screen size cannot be null or empty");
        }

        this.screenSize = screenSize;
    }

    // Getter for laptop-specific field
    public String getScreenSize() {
        return this.screenSize;
    }

    // Delegate Computer-related method calls to the composed Computer object
    // getCPU/getRAM/getDisk are inherited from Computer

    // Screen-size-specific getter methods
    public boolean isLargeScreen() {
        if (screenSize == null)
            return false;
        try {
            double size = Double.parseDouble(screenSize.replace("\"", "").trim());
            return size >= 15.0;
        } catch (NumberFormatException e) {
            return screenSize.toLowerCase().contains("large") ||
                    screenSize.toLowerCase().contains("15") ||
                    screenSize.toLowerCase().contains("17");
        }
    }

    public boolean isSmallScreen() {
        if (screenSize == null)
            return false;
        try {
            double size = Double.parseDouble(screenSize.replace("\"", "").trim());
            return size < 14.0;
        } catch (NumberFormatException e) {
            return screenSize.toLowerCase().contains("small") ||
                    screenSize.toLowerCase().contains("13") ||
                    screenSize.toLowerCase().contains("compact");
        }
    }

    public String getScreenType() {
        if (screenSize == null)
            return "Unknown";
        String lowerScreen = screenSize.toLowerCase();
        if (lowerScreen.contains("oled") || lowerScreen.contains("amoled")) {
            return "OLED";
        } else if (lowerScreen.contains("ips")) {
            return "IPS";
        } else if (lowerScreen.contains("tn")) {
            return "TN";
        } else {
            return "Standard LCD";
        }
    }

    // Return formatted version of data
    public String toString() {
        return "Type:Laptop\tCPU:" + getCPU() + "\tRAM:" + getRAM() +
                "\tDisk:" + getDisk() + "\tScreen:" + this.screenSize;
    }

    // Implement equals and hashCode for proper immutability
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Laptop laptop = (Laptop) o;
        return screenSize.equals(laptop.screenSize) &&
                getCPU().equals(laptop.getCPU()) &&
                getRAM().equals(laptop.getRAM()) &&
                getDisk().equals(laptop.getDisk());
    }

    @Override
    public int hashCode() {
        int result = screenSize.hashCode();
        result = 31 * result + getCPU().hashCode();
        result = 31 * result + getRAM().hashCode();
        result = 31 * result + getDisk().hashCode();
        return result;
    }
}
