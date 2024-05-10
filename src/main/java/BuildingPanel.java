import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.*;

public class BuildingPanel extends JPanel {
    private List <Building> buildings;
    private int maxHeight;

    private JFrame frame;

    public BuildingPanel(List <Building> buildings, JFrame frame) {
        this.buildings = buildings;
        calculateDimensions();
        this.frame =frame;
    }

    void calculateDimensions() {
        maxHeight = 0;
        for (Building building : buildings) {
            if (building.getLength() > maxHeight) {
                maxHeight = (int) building.getLength();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 20;
        int x1 = 1;
        // Draw the player
        Player player = GameData.getInstance().getPlayer();
        player.draw(g);
        System.out.println("frame height: " + frame.getHeight());
        System.out.println("first building height: " + (int)buildings.get(0).getLength());
        System.out.println("player: " + player.getY());
        for (Building building : buildings) {

            int width = building.getBreadth();
            int height = (int) (building.getLength());
            g.setColor(Color.YELLOW);
            g.fillRect(x, getHeight() - height , width, height);
            g.setColor(Color.RED);
            g.drawString("Building", x + 10, frame.getHeight() - height - 20);
            g.drawString("Length: " + building.getLength(), x + 10, getHeight() - height);
            g.drawString("Breadth: " + building.getBreadth(), x + 10, getHeight() - height + 20);


            if (x1<131){
                String equation = Equations.getEquation(x1);
                g.drawString(equation,  x + 10, getHeight() - height + 40);
            }
            x1++;
            x += width + 10; // Add a gap between buildings
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int totalWidth = 0;
        for (Building building : buildings) {
            totalWidth += building.getBreadth() + 10;
        }
        return new Dimension(totalWidth, maxHeight + 50);
    }
}