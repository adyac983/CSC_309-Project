package mathleap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildingParser {

    public static List<Building> parseDataToBuildings(List<DataRecord> dataRecords) {
        double length = 0;
        double number;
        List<Building> buildings = new ArrayList<>();
        for (int i = 0; i < dataRecords.size(); i++) {
            DataRecord record = dataRecords.get(i);
            if(ChoicePanel.getChoice() != 1){
                String areaString = record.getNum().replaceAll("[^\\d.]", "");
                number = Double.parseDouble(areaString);
            }
            else{
                number = Double.parseDouble(record.getNum());
            }
            if(ChoicePanel.getChoice() == 1){
                length = number * 5.0;
            }
            else {
                if(number > 1000000){
                    length = number / 100000;
                }
                else if(number > 100000){
                    length = number/10000;
                }
                else if (number > 1000){
                    length = number/ 100;
                }
                else{
                    length = number/10;
                }
            }
            if(length > 700){
                length = 650;
            }
            int breadth = 150;
            String c = record.getCountry();
            buildings.add(new Building(length, breadth, c));
        }
        Collections.shuffle(buildings);
        return buildings;
    }
}
