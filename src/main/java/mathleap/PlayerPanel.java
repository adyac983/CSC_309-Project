package mathleap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PlayerPanel extends JPanel {
    private String selectedPlayerName;
    private Image[] playerImages;
    private String[] playerNames = {"boy", "amongus", "girl", "nezuko", "pikachu"};
    private int playerCount = playerNames.length;
    private int imageWidth = 50;
    private int imageHeight = 100;
    private int selectedIndex = -1;

    public PlayerPanel() {
        playerImages = new Image[playerCount];
        for (int i = 0; i < playerCount; i++) {
            try {
                URL url = getClass().getResource("/images/" + playerNames[i] + ".png");
                if (url != null) {
                    playerImages[i] = ImageIO.read(url);
                    playerImages[i] = playerImages[i].getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                } else {
                    System.out.println("Image not found: " + playerNames[i] + ".png");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = e.getX() / imageWidth;
                if (index >= 0 && index < playerCount) {
                    selectedPlayerName = playerNames[index];
                    selectedIndex = index;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < playerCount; i++) {
            if (playerImages[i] != null) {
                if (i == selectedIndex) {
                    g.setColor(Color.PINK);
                    g.fillRect(i * imageWidth, 0, imageWidth, imageHeight);
                    g.drawImage(playerImages[i], i * imageWidth, 0, this);
                } else {

                    g.drawImage(playerImages[i], i * imageWidth, 0, this);
                }
            }
        }


        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Select player and hit continue", 10, imageHeight + 20);
    }

    public String getSelectedPlayer() {
        return selectedPlayerName;
    }
}
