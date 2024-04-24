import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

public class BuildingPanel extends JPanel {
    private List <Building> buildings;
    private int x = 10;
    private int y = 10;
    private int maxHeight;
    private double scaleFactor;

    public BuildingPanel(List <Building> buildings) {
        this.buildings = buildings;
        calculateDimensions();
    }

    void calculateDimensions() {
        maxHeight = 0;
        for (Building building : buildings) {
            if (building.getLength() > maxHeight) {
                maxHeight = (int) building.getLength();
            }
        }
        scaleFactor = 10000; // Adjust the 200 value as needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Building building : buildings) {
            // Calculate the width and height of the building based on the scale factor
            int width = (int) (building.getBreadth() * scaleFactor);
            int height = (int) (building.getLength() * scaleFactor);
            g.setColor(Color.BLUE);
            g.fillRect(x, getHeight() - height - y, width * 10, height);
            g.setColor(Color.RED);
            g.drawString("Building", x + 10, getHeight() - height - 10 - y);
            g.drawString("Length: " + building.getLength(), x + 10, getHeight() - height - y);
            g.drawString("Breadth: " + building.getBreadth(), x + 10, getHeight() - height + 20 - y);
            x += width * 10 + 10; // Add a gap between buildings
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int totalWidth = 0;
        for (Building building : buildings) {
            totalWidth += (int) (building.getBreadth() * scaleFactor) * 10 + 10; // Add a gap between buildings
        }
        return new Dimension(totalWidth, maxHeight + 50); // Adjust the size as needed
    }
}