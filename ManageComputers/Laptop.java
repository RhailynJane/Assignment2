public class Laptop extends Computer {
    private String screenSize;

    public Laptop(String company, String CPU, String RAM, String disk, String screenSize) {
        super(company, CPU, RAM, disk);
        this.screenSize = screenSize;
    }

    public String getScreenSize() {
        return screenSize;
    }

    @Override
    public String toString() {
        return String.format("Laptop [Company: %s, CPU: %s, RAM: %sGB, Disk: %sGB, Screen: %s inches]",
                company, CPU, RAM, disk, screenSize);
    }
}