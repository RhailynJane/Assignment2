public class Desktop extends Computer {
    private String GPUType;

    public Desktop(String company, String CPU, String RAM, String disk, String GPUType) {
        super(company, CPU, RAM, disk);
        this.GPUType = GPUType;
    }

    public String getGPUType() {
        return GPUType;
    }

    @Override
    public String toString() {
        return String.format("Desktop [Company: %s, CPU: %s, RAM: %sGB, Disk: %sGB, GPU: %s]",
                company, CPU, RAM, disk, GPUType);
    }
}