public class Building {
    private double length;
    private int breadth;

    private int x;

    private int y;

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

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Building{" +
                "length=" + length +
                ", breadth=" + breadth +
                '}';
    }
}

