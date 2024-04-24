import java.util.ArrayList;
import java.util.List;

public class BuildingParser {

    public static List<Building> parseDataToBuildings(List<DataRecord> dataRecords) {
        List<Building> buildings = new ArrayList<>();

        for (int i = 0; i < dataRecords.size(); i++) {
            //int randomIndex = random.nextInt(dataRecords.size());
            DataRecord record = dataRecords.get(i);
            double number = Double.parseDouble(record.getNum()); // Assuming getData1 contains numbers

            double length = number * 5.0; // Assuming a scaling factor of 10 for length
            int breadth = 15; // Fixed breadth

            buildings.add(new Building(length, breadth));
        }

        return buildings;
    }

}
