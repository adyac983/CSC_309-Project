import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildingParser {

    public static List<Building> parseDataToBuildings(List<DataRecord> dataRecords) {
        List<Building> buildings = new ArrayList<>();

        for (int i = 0; i < dataRecords.size(); i++) {
            DataRecord record = dataRecords.get(i);
            double number = Double.parseDouble(record.getNum());

            double length = number * 5.0;
            int breadth = 150;

            buildings.add(new Building(length, breadth));
        }
        Collections.shuffle(buildings);

        return buildings;
    }

}
