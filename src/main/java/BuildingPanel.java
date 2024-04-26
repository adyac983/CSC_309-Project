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
        scaleFactor = 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        super.paintComponent(g);
        int x = 20;
        int y = 20;
        int x1 = 1;
        for (Building building : buildings) {

            int width = (int) (building.getBreadth() * scaleFactor);
            int height = (int) (building.getLength() * scaleFactor);
            g.setColor(Color.BLUE);
            g.fillRect(x, getHeight() - height - 10, width * 10, height);
            g.setColor(Color.RED);
            g.drawString("Building", x + 10, getHeight() - height - 20);
            g.drawString("Length: " + building.getLength(), x + 10, getHeight() - height);
            g.drawString("Breadth: " + building.getBreadth(), x + 10, getHeight() - height + 20);
            if (x1<9){
                String equation = Equations.getEquation(x1);
                g.drawString(equation,  x + 10, getHeight() - height + 40);
            }
            x1++;
            x += width * 10 + 10; // Add a gap between buildings
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int totalWidth = 0;
        for (Building building : buildings) {
            totalWidth += (int) (building.getBreadth() * scaleFactor) * 10 + 10;
        }
        return new Dimension(totalWidth, maxHeight + 50);
    }
}