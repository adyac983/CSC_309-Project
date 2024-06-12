package mathleap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Player {
    private int x;
    private int y;
    private Image image;
    private int hp;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.hp = 3;
    }

    private Image loadImageFromFile(String fileName) {
        Image img = null;
        try {
            URL url = getClass().getResource("/images/" + fileName);
            if (url != null) {
                img = ImageIO.read(url);
            } else {
                System.err.println("Resource not found: /images/" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }


    public void setImage(String fileName) {
        Image img = loadImageFromFile(fileName);
        if (img != null) {
            this.image = img.getScaledInstance(50, 100, Image.SCALE_SMOOTH);
        } else {
            System.err.println("Failed to load image: " + fileName);
        }
    }

    public void setImage(Image image) {
        this.image = image.getScaledInstance(50, 100, Image.SCALE_SMOOTH);
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
