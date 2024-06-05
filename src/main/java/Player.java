import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Player{
    private int x;
    private int y;
    private Image image;
    private int hp;

    public Player(int x, int y) {
        this.x = x;
        this. y = y;
        this.hp = 3;

        try {
            URL url = getClass().getResource("/images/boy.png");
            if (url != null) {
                this.image = ImageIO.read(url);
                this.image = this.image.getScaledInstance(50, 100, Image.SCALE_SMOOTH);
            } else {
                System.out.println("Image not found: player.png");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void draw(Graphics g) {

        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.fillRect(x, y, 50, 100); // Default rectangle if image not found
        }
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}