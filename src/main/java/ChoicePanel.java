import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class ChoicePanel extends JPanel {
    static int choice;
    private JFrame frame;
    private int levelchoice;
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
        java.util.List<DataRecord> dataRecords = WebDataExtractor.extractWebTableData(choice);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dataRecords);


        frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GameData.getInstance().setFrame(frame);
        BuildingPanel buildingPanel = new BuildingPanel(buildings);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setViewportView(buildingPanel);
        GameData.getInstance().setScrollPane(scrollPane);
        GameData.getInstance().setBuildings(buildings);

        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Player player = new Player(firstBuilding.getX(), frame.getHeight()-(int)firstBuilding.getLength()-100);
        GameData.getInstance().setPlayer(player);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Feedback panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        Feedback feedback = new Feedback(levelchoice);
        feedbackPanel.add(feedback, BorderLayout.SOUTH);
        frame.add(feedbackPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public static int getChoice(){
        return choice;
    }

    public void setLevelChoice(int i) {
        levelchoice=i;
    }

}
