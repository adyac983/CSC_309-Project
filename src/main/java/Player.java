import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Player extends JPanel{
    private Rectangle bounds; // dummy player, just a rectangle
    private int x;
    private int y;

    public Player(int x, int y, int width, int height) {
        this.x = x;
        this. y = y;
        this.bounds = new Rectangle(x, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the player
        g.setColor(Color.PINK);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void moveTo(int x, int y) {
        bounds.setLocation(x, y);
    } // ideally can get the next building's x and y and move to there

    public Rectangle getBounds() {
        return bounds;
    }
}