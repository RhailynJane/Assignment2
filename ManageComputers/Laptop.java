public final class Laptop {
    // Laptop-specific field (immutable)
    private final String screenSize;

    // Composition: Laptop "has-a" Computer
    private final Computer computer;

    // Constructor accepting all parameters
    public Laptop(String CPU, String RAM, String disk, String screenSize) {
        // Validate parameters
        if (screenSize == null || screenSize.trim().isEmpty()) {
            throw new IllegalArgumentException("Screen size cannot be null or empty");
        }

        this.screenSize = screenSize;

        // Create Computer instance using composition
        this.computer = new Computer(CPU, RAM, disk);
    }

    // Getter for laptop-specific field
    public String getScreenSize() {
        return this.screenSize;
    }

    // Delegate Computer-related method calls to the composed Computer object
    public String getCPU() {
        return computer.getCPU();
    }

    public String getRAM() {
        return computer.getRAM();
    }

    public String getDisk() {
        return computer.getDisk();
    }

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
