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
    public ChoicePanel() {
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
        if (GameData.getInstance().getMultiplayer() == 1) {
            newGame();
        }
        else {
            startMultiPlayer();
        }
    }
    public static int getChoice(){
        return choice;
    }

    public void setLevelChoice(int i) {
        levelchoice=i;
    }
    public static List<DataRecord> getDataRecord(){return dr;}
    private void newGame() {
        dr = WebDataExtractor.extractWebTableData(choice);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dr);


        frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GameData.getInstance().reset();

        GameData.getInstance().setFrame(frame);
        BuildingPanel buildingPanel = new BuildingPanel(buildings);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setViewportView(buildingPanel);
        GameData.getInstance().setScrollPane(scrollPane);
        GameData.getInstance().setBuildings(buildings);
        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Player player = new Player(firstBuilding.getX(), frame.getHeight()-(int)firstBuilding.getLength()-100);
        GameData.getInstance().setSoloPlayer(player);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Feedback panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        Feedback feedback = new Feedback(levelchoice);
        feedbackPanel.add(feedback, BorderLayout.SOUTH);
        frame.add(feedbackPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    private void startMultiPlayer() {
        GameData.getInstance().reset();

        dr = WebDataExtractor.extractWebTableData(choice);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dr);
        GameData.getInstance().setBuildings(buildings);
        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Player player1 = new Player(firstBuilding.getX(), 600-(int)firstBuilding.getLength()-100);
        Player player2 = new Player(firstBuilding.getX(), 600-(int)firstBuilding.getLength()-100);
        GameData.getInstance().setServerPlayer(player1);
        GameData.getInstance().setClientPlayer(player2);
        //MultiplayerClient client = new MultiplayerClient();
        MultiplayerServer server = new MultiplayerServer();
        //client.simulateGame();
        server.simulateGame();

    }
}
