import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    public Main() {
        //add panels inside here

    }
    //right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {
        Main main = new Main();
        main.setTitle("Towers of Hanoi");
        main.setSize(800, 400);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);

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

    }
}
