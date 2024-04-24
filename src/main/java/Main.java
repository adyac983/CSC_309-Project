import java.util.ArrayList;
import java.util.List;

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

    }
}
