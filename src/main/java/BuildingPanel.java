import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;


public class BuildingPanel extends JPanel {
    private List<Building> buildings;
    private int maxHeight;
    private Image backgroundImage;

    private JComboBox<String> backgroundSelector;

    public BuildingPanel(List<Building> buildings) {
        this.buildings = buildings;
        calculateDimensions();
        setupBackgroundSelector();
        loadDefaultBackgroundImage();
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
        loadBackgroundImage("Background 1"); // Default background
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
        Player player = GameData.getInstance().getPlayer();
        player.draw(g);

        // Draw buildings
        int x = 20;
        int scrollPaneHeight = GameData.getInstance().getScrollPaneHeight();
        for (Building building : buildings) {
            int width = building.getBreadth();
            int height = (int) (building.getLength());


            g.setColor(Color.YELLOW);
            g.fillRect(x, scrollPaneHeight - height, width, height);

            g.setColor(Color.RED);
            g.drawString("Building", x + 10, scrollPaneHeight - height - 20);
            g.drawString("Length: " + building.getLength(), x + 10, scrollPaneHeight - height);
            g.drawString("Breadth: " + building.getBreadth(), x + 10, scrollPaneHeight - height + 20);

            String equation = Equations.getEquation(x / (building.getBreadth() + 70) + 1);
            g.drawString(equation, x + 10, scrollPaneHeight - height + 40);

            x += width + 70; // Add a gap between buildings
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
}
