import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    //right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {
        List<DataRecord> dataRecords = WebDataExtractor.extractWebTableData();

        // Display the collected data
        System.out.println("Collected Data:");
        for (DataRecord record : dataRecords) {
            System.out.println(record.getHeader() + ") " + record.getCountry() + " : " + record.getNum());
        }

        List<Building> buildings = BuildingParser.parseDataToBuildings(dataRecords);


        // Display the parsed buildings
        System.out.println("Parsed Buildings:");
        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            System.out.println("Building " + (i + 1) + ": Length=" + building.getLength() + ", Breadth=" + building.getBreadth());
        }

        JFrame frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        BuildingPanel buildingPanel = new BuildingPanel(buildings);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);

        GameData.getInstance().setSize(400,300);
        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Player player = new Player(firstBuilding.getX(),0,50,100);
        player.setBounds(player.getX(), player.getY(), player.getBounds().width, player.getBounds().height);
        frame.add(player);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Feedback panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        Feedback feedback = new Feedback();
        feedbackPanel.add(feedback, BorderLayout.SOUTH);
        frame.add(feedbackPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}


