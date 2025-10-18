public abstract class Computer {
    protected String company;
    protected String CPU;
    protected String RAM;
    protected String disk;

    public Computer(String company, String CPU, String RAM, String disk) {
        this.company = company;
        this.CPU = CPU;
        this.RAM = RAM;
        this.disk = disk;
    }

    public String getCompany() {
        return company;
    }

    public String getCPU() {
        return CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public String getDisk() {
        return disk;
    }

    public abstract String toString();
}