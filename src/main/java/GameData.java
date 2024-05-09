import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game data for the Towers of Hanoi game.
 * It contains the disks and towers, and recalculate their positions.
 * It extends PropertyChangeSupport to notify observers of changes in the game data.
 *
 * @author Professor
 */
public class GameData extends PropertyChangeSupport {

    private static GameData instance;
    private List<Building> buildings;
    private Player player;

    private int mouseYOffset = 0;

    private int currBuilding = 0;

    private int numBuildings = 0;

    private GameData() {
        super(new Object());
        List<DataRecord> dataRecords = WebDataExtractor.extractWebTableData(ChoicePanel.getChoice());
        buildings = BuildingParser.parseDataToBuildings(dataRecords);
        // Display the parsed buildings
//        System.out.println("Parsed Buildings:");
//        for (int i = 0; i < buildings.size(); i++) {
//            Building building = buildings.get(i);
//            System.out.println("Building " + (i + 1) + ": Length=" + building.getLength() + ", Breadth=" + building.getBreadth());
//        }
        numBuildings = buildings.size();
        this.recalculate();
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void recalculate () {
        double totalBreadth = 0;
        int maxHeight = 0;
        for (Building building : buildings) {
            totalBreadth += building.getBreadth();
            if (building.getLength() > maxHeight) {
                maxHeight = (int) building.getLength();
            }
        }

        // Calculate the scale factor to fit all buildings within the frame
        double scaleFactor = 2000 / totalBreadth;

        int finalMaxHeight = maxHeight;
        int x = 10; // Starting x-coordinate for the first building
        int y = 10; // Starting y-coordinate for all buildings

        for (int i = currBuilding; i < numBuildings; i++) {
            // Calculate the width and height of the building based on the scale factor
            Building building = buildings.get(i);
            int width = (int) (building.getBreadth() * scaleFactor);
            int height = (int) (building.getLength() * scaleFactor);

            building.setX(x);
            building.setY(y);

            x += width * 10 + 10; // Add a gap between buildings
        }

    }

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public int getMouseXOffset() {
        return mouseXOffset;
    }

    public void setMouseXOffset(int mouseXOffset) {
        this.mouseXOffset = mouseXOffset;
    }

    private int mouseXOffset = 0;

    public int getMouseYOffset() {
        return mouseYOffset;
    }

    public void setMouseYOffset(int mouseYOffset) {
        this.mouseYOffset = mouseYOffset;
    }
    public void setCurrBuilding() {
        this.currBuilding += 1;
    }
    public Building getCurrBuilding() {
        return buildings.get(currBuilding);
    }

    public void repaint() {
        firePropertyChange("repaint", null, null);
    }
}
