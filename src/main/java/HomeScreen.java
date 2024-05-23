import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JFrame frame;


    public HomeScreen() {

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        ChoicePanel choicePanel = new ChoicePanel();

        // Home Screen Panel
        JPanel homePanel = new JPanel(new BorderLayout());
        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> cardLayout.show(mainPanel, "levelPanel"));
        homePanel.add(playButton, BorderLayout.CENTER);

        // Level Selection Panel
        JPanel levelPanel = new JPanel(new GridLayout(3, 1));
        JButton level1Button = new JButton("Level 1");
        JButton level2Button = new JButton("Level 2");
        JButton level3Button = new JButton("Level 3");
        level1Button.addActionListener(e -> {
            choicePanel.setLevelChoice(1);
            cardLayout.show(mainPanel, "themePanel");
        });
        level2Button.addActionListener(e -> {
            choicePanel.setLevelChoice(2);
            cardLayout.show(mainPanel, "themePanel");
        });
        level3Button.addActionListener(e -> {
            choicePanel.setLevelChoice(3);
            cardLayout.show(mainPanel, "themePanel");
        });
        levelPanel.add(level1Button);
        levelPanel.add(level2Button);
        levelPanel.add(level3Button);

        mainPanel.add(homePanel, "homePanel");
        mainPanel.add(levelPanel, "levelPanel");
        mainPanel.add(choicePanel, "themePanel");

        cardLayout.show(mainPanel, "homePanel");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

    }
}