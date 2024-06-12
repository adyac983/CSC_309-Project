package mathleap;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPanel gameOverPanel;

    private ChoicePanel choicePanel;

    public HomeScreen() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);



        // Home Screen Panel
        JPanel homePanel = new JPanel(new GridLayout(2, 1));
        JButton playSingleButton = new JButton("Play Solo");
        playSingleButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "playerPanel");
            GameData.getInstance().setMultiplayer(1);
        });
        homePanel.add(playSingleButton);
        JButton playMultiButton = new JButton("Play Multiplayer");
        playMultiButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "playerPanel");
            GameData.getInstance().setMultiplayer(0);
        });
        homePanel.add(playMultiButton);

        // Player Selection Panel
        PlayerPanel playerPanel = new PlayerPanel();
        JPanel playerSelectionPanel = new JPanel(new BorderLayout());
        playerSelectionPanel.add(playerPanel, BorderLayout.CENTER);
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> {
            if (playerPanel.getSelectedPlayer() != null) {
                // Create ChoicePanel with selected player
                System.out.println(playerPanel.getSelectedPlayer());
                choicePanel = new ChoicePanel(playerPanel.getSelectedPlayer().toString());
                mainPanel.add(choicePanel, "themePanel");

                cardLayout.show(mainPanel, "levelPanel");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a player before continuing.");
            }
        });
        playerSelectionPanel.add(continueButton, BorderLayout.SOUTH);

        // Level Selection Panel
        JPanel levelPanel = new JPanel(new GridLayout(3, 1));
        JButton level1Button = new JButton("Level 1");
        JButton level2Button = new JButton("Level 2");
        JButton level3Button = new JButton("Level 3");
        level1Button.addActionListener(e -> {
            choicePanel.setLevelChoice(1);
            GameData.getInstance().reset();
            cardLayout.show(mainPanel, "themePanel");
        });
        level2Button.addActionListener(e -> {
            choicePanel.setLevelChoice(2);
            GameData.getInstance().reset();
            cardLayout.show(mainPanel, "themePanel");
        });
        level3Button.addActionListener(e -> {
            choicePanel.setLevelChoice(3);
            GameData.getInstance().reset();
            cardLayout.show(mainPanel, "themePanel");
        });
        levelPanel.add(level1Button);
        levelPanel.add(level2Button);
        levelPanel.add(level3Button);

        // Game Over Screen Panel
        gameOverPanel = new JPanel(new BorderLayout());
        JButton gameOverButton = new JButton("Play again");
        gameOverButton.addActionListener(e -> cardLayout.show(mainPanel, "levelPanel"));
        gameOverPanel.add(gameOverButton, BorderLayout.CENTER);

        mainPanel.add(homePanel, "homePanel");
        mainPanel.add(playerSelectionPanel, "playerPanel");
        mainPanel.add(levelPanel, "levelPanel");
        mainPanel.add(gameOverPanel, "gameOverPanel");

        cardLayout.show(mainPanel, "homePanel");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    public void GameOver() {
        cardLayout.show(mainPanel, "gameOverPanel");
    }
}
