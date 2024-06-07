import javax.swing.*;
import java.awt.*;
public class HomeScreen extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPanel gameOverPanel;

    public HomeScreen() {

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        ChoicePanel choicePanel = new ChoicePanel();

        // Home Screen Panel
        JPanel homePanel = new JPanel(new GridLayout(2, 1));
        JButton playSingleButton = new JButton("Play Solo");
        playSingleButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "levelPanel");
            GameData.getInstance().setMultiplayer(1);
        });
        homePanel.add(playSingleButton);
        JButton playMultiButton = new JButton("Play Multiplayer");
        playMultiButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "levelPanel");
            GameData.getInstance().setMultiplayer(0);
        });
        homePanel.add(playMultiButton);

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

        // game over Screen Panel
        gameOverPanel = new JPanel(new BorderLayout());
        JButton gameOverButton = new JButton("Play again");
        gameOverButton.addActionListener(e -> cardLayout.show(mainPanel, "levelPanel"));
        gameOverPanel.add(gameOverButton, BorderLayout.CENTER);

        mainPanel.add(homePanel, "homePanel");
        mainPanel.add(levelPanel, "levelPanel");
        mainPanel.add(choicePanel, "themePanel");
        mainPanel.add(gameOverPanel, "gameOverPanel");

        cardLayout.show(mainPanel, "homePanel");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

    }
    public void GameOver() {
        cardLayout.show(mainPanel, "gameOverPanel");
    }
}
