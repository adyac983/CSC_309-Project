package mathleap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import javax.swing.*;


public class BuildingPanel extends JPanel implements ActionListener{
    private static List<Building> buildings;
    private int maxHeight;
    private Image backgroundImage;

    private JComboBox<String> backgroundSelector;
    private static Timer timer;

    private String theme;





    public BuildingPanel(List<Building> buildings,String theme) {
        this.buildings = buildings;
        calculateDimensions();
        setupBackgroundSelector();
        loadBackgroundImage(theme);
        this.timer = new Timer(50,this);
        timer.start();

    }

    void calculateDimensions() {
        maxHeight = 0;
        for (Building building : buildings) {
            if (building.getLength() > maxHeight) {
                maxHeight = (int) building.getLength();
            }
        }
    }






    void setupBackgroundSelector() {
        String[] backgroundOptions = {"City", "CO2", "Forest"};
        backgroundSelector = new JComboBox<>(backgroundOptions);
        backgroundSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBackground = (String) backgroundSelector.getSelectedItem();
                loadBackgroundImage(selectedBackground);
                repaint();
            }
        });
        setLayout(new FlowLayout(FlowLayout.LEFT));
        backgroundSelector.setPreferredSize(new Dimension(200, backgroundSelector.getPreferredSize().height));
        add(backgroundSelector); // Add the dropdown menu to the top of the panel
    }

    void loadDefaultBackgroundImage() {
        loadBackgroundImage("city"); // Default background
    }

    void loadBackgroundImage(String selectedBackground) {
        String bg = selectedBackground.toLowerCase().replace(" ", "_");
        java.net.URL url = getClass().getResource("/images/" + bg + ".jpg");
        if (url != null) {
            ImageIcon bgIcon = new ImageIcon(url);
            backgroundImage = bgIcon.getImage();
        } else {
            // Handle the case when the resource is not found
            System.out.println("Background image not found: " + bg);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        GameData.getInstance().recalculate();
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int imageWidth = getParent().getWidth();
        int imageHeight = getParent().getHeight();
        int numXRepeats = (panelWidth / imageWidth) + 1;
        int numYRepeats = (panelHeight / imageHeight) + 1;

        // Draw background
        for (int y = 0; y < numYRepeats; y++) {
            for (int x = 0; x < numXRepeats; x++) {
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, x * imageWidth, 0, imageWidth, imageHeight, this);
                }
            }
        }

        // Draw the player
        Player player = GameData.getInstance().getCurrentPlayer();
        player.draw(g);

        // Draw buildings
        int scrollPaneHeight = GameData.getInstance().getScrollPaneHeight();
        for (Building building : buildings) {
            int width = building.getBreadth();
            int height = (int) (building.getLength());

            g.setColor(new Color(129, 63, 8));
            g.fillRect(building.getX(), scrollPaneHeight - height, width, height);

            // Draw brick texture
            g.setColor(new Color(160, 82, 45));
            for (int i = 0; i < height / 10 ; i++) {
                for (int j = 0; j < (width / 20) + 1; j++) {
                    if (j == (width / 20)) {
                        g.fillRect(building.getX() + j * 20, scrollPaneHeight - height + i * 10, 10, 10);
                    }
                    else if ((i + j) % 2 == 0) {
                        g.fillRect(building.getX() + j * 20, scrollPaneHeight - height + i * 10, 20, 10);

                    }
                }
            }

            //windows
            int numHorizontalWindows = 3;
            int numVerticalWindows = 40;
            g.setColor(Color.YELLOW);
            int windowWidth = 20;
            int windowHeight = 20;
            int horizontalSpacing = (width - 30 - numHorizontalWindows * windowWidth) / (numHorizontalWindows - 1);
            int verticalSpacing = 10;

            int firstWindowX = building.getX() + ((width - windowWidth * numHorizontalWindows - horizontalSpacing * (numHorizontalWindows - 1)) / 2);
            int firstWindowY = scrollPaneHeight - height + 20;

            for (int i = 0; i < numHorizontalWindows; i++) {
                g.fillRect(firstWindowX + i * (windowWidth + horizontalSpacing), firstWindowY, windowWidth, windowHeight);
            }
            for (int i = 0; i < numVerticalWindows; i++) {
                for (int j = 0; j < numHorizontalWindows; j++) {
                    g.fillRect(firstWindowX + j * (windowWidth + horizontalSpacing), firstWindowY + 30 + (i * (windowHeight + verticalSpacing)), windowWidth, windowHeight);
                }
            }

            //door
            g.setColor(Color.darkGray);
            int doorHeight = Math.min(60, height / 4);
            int doorWidth = Math.min(40, width / 3);
            g.fillRect(building.getX() + width / 2 - doorWidth / 2, scrollPaneHeight - doorHeight, doorWidth, doorHeight);

            //roof
            g.setColor(Color.darkGray);
            g.fillRect(building.getX() - 3, scrollPaneHeight - height, width + 6, 10);

            // Draw building information
            g.setColor(Color.RED);
            g.drawString(building.getCountry(), building.getX() + 10, scrollPaneHeight - height - 20);
            //g.drawString("Length: " + building.getLength(), building.getX() + 10, scrollPaneHeight - height);
            //g.drawString("Breadth: " + building.getBreadth(), building.getX() + 10, scrollPaneHeight - height + 20);


            //String equation = Equations.getEquation(building.getX() / (building.getBreadth() + 70) + 1);
            //if(!Objects.isNull(building.getCountry())){
            //  g.drawString(, building.getX() + 10, scrollPaneHeight - height + 40);
            //}

        }
    }

    @Override
    public Dimension getPreferredSize() {
        int totalWidth = 0;
        for (Building building : buildings) {
            totalWidth += building.getBreadth() + 50;
        }
        return new Dimension(totalWidth, maxHeight + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Player player = GameData.getInstance().getCurrentPlayer();
        GameData.getInstance().getCurrentPlayer().moveTo(player.getX()+1, player.getY());
        repaint();

    }
    public static void stopTimer() {
        timer.stop();
    }
    public static void startTimer() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

}