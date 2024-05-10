import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Player{
    private int x;
    private int y;

    public Player(int x, int y) {
        this.x = x;
        this. y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 50, 100);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void moveTo(int x, int y) {
    } // ideally can get the next building's x and y and move to there

}