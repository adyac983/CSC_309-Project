public class Building {
    private double length;
    private int breadth;

    public Building(double length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public double getLength() {
        return length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    @Override
    public String toString() {
        return "Building{" +
                "length=" + length +
                ", breadth=" + breadth +
                '}';
    }
}

