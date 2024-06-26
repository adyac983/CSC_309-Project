package mathleap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class ChoicePanel extends JPanel {
    static int choice;
    private JFrame frame;
    private int levelchoice;
    private static List<DataRecord> dr;
    private static JLabel hpLabel;
    private static JLabel scoreLabel;

    private static JLabel opponentScoreLabel;

    private String playerchoice;
    public ChoicePanel(String player) {

        this.playerchoice= player;
        GameData.getInstance().setPlayerchoice(playerchoice);
        setLayout(new GridLayout(3, 1));

        JButton button1 = new JButton("Air Pollution");
        JButton button2 = new JButton("CO2 Emissions");
        JButton button3 = new JButton("Forest Area");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                handleButtonAction(1);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                handleButtonAction(2);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 3;
                handleButtonAction(3);
            }
        });

        add(button1);
        add(button2);
        add(button3);


    }

    private void handleButtonAction(int selectedSource) {
        if (GameData.getInstance().getMultiplayer() == 0) {
            if (selectedSource == 1) {
                newGame("city");
            }
            if (selectedSource == 2) {
                newGame("co2");
            }
            if (selectedSource == 3) {
                newGame("forest");
            }
        } else {
            startMultiPlayer(selectedSource);
        }
    }

    public static int getChoice() {
        return choice;
    }

    public void setLevelChoice(int i) {
        levelchoice = i;
    }

    public static List<DataRecord> getDataRecord() {
        return dr;
    }

    private void newGame(String theme) {
        dr = WebDataExtractor.extractWebTableData(choice);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dr);


        frame = new JFrame("Math Leap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GameData.getInstance().reset();

        GameData.getInstance().setFrame(frame);
        BuildingPanel buildingPanel = new BuildingPanel(buildings, theme);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setViewportView(buildingPanel);
        GameData.getInstance().setScrollPane(scrollPane);
        GameData.getInstance().setBuildings(buildings);
        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Player player = new Player(firstBuilding.getX(), frame.getHeight()-(int)firstBuilding.getLength()-100);
        if (playerchoice=="boy"){
            player.setImage("boy.png");
        }
        else if (playerchoice=="girl"){
            player.setImage("girl.png");
        }
        else if (playerchoice=="nezuko"){
            player.setImage("nezuko.png");
        }
        else if (playerchoice=="pikachu"){
            player.setImage("pikachu.png");
        }
        else if (playerchoice=="amongus"){
            player.setImage("amongus.png");
        }
        else if (playerchoice=="dinosaur"){
            player.setImage("dinosaur.png");
        }
        else if (playerchoice=="gojo"){
            player.setImage("gojo.png");
        }
        GameData.getInstance().setPlayer(player);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Feedback panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        Feedback feedback = new Feedback(levelchoice);
        feedbackPanel.add(feedback, BorderLayout.SOUTH);
        frame.add(feedbackPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Create HP and Score panel
        JPanel hpScorePanel = new JPanel(new GridLayout(1, 1));
        hpLabel = new JLabel("HP: ");
        scoreLabel = new JLabel("Score: 0");
        opponentScoreLabel = new JLabel("Opponent Score: 0");

        hpLabel.setForeground(Color.RED);
        opponentScoreLabel.setForeground(Color.RED);
        scoreLabel.setForeground(Color.RED);
        hpScorePanel.add(hpLabel);
        hpScorePanel.add(scoreLabel);
        GameData.getInstance().setScoreLabel(scoreLabel);
        GameData.getInstance().setHpLabel(hpLabel);

        if (GameData.getInstance().getMultiplayer() == 1)
            hpScorePanel.add(opponentScoreLabel);

        frame.add(hpScorePanel, BorderLayout.NORTH);
        frame.setVisible(true);

        GameData.getInstance().changeHpLabelText();
        GameData.getInstance().changeScoreLabelText();
    }
    private void startMultiPlayer(int selectedSource) {
        dr = WebDataExtractor.extractWebTableData(choice);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dr);
        GameData.getInstance().setBuildings(buildings);

        GameData.getInstance().reset();

        MultiplayerClient client = new MultiplayerClient();
        MultiplayerServer server = MultiplayerServer.getInstance();
        client.simulateGame();
        server.simulateGame();

    }
}
