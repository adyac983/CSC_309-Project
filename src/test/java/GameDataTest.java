import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import mathleap.*;

import static org.junit.jupiter.api.Assertions.*;
public class GameDataTest {

    @Test
    void testGameData() {
        java.util.List<DataRecord> dataRecords = WebDataExtractor.extractWebTableData(1);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dataRecords);
        BuildingPanel buildingPanel = new BuildingPanel(buildings);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setViewportView(buildingPanel);
        GameData.getInstance().setScrollPane(scrollPane);
        GameData.getInstance().setBuildings(buildings);

        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Player player = new Player(0,0);
        GameData.getInstance().setSoloPlayer(player);

        GameData.getInstance().recalculate();
        Assertions.assertEquals((int)firstBuilding.getLength(), firstBuilding.getY());
        Assertions.assertEquals(firstBuilding.getX(), 20);
    }
    @Test
    void testGameDataNoBuildings() {
        List<Building> buildings = new ArrayList<>();
        BuildingPanel buildingPanel = new BuildingPanel(buildings);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setViewportView(buildingPanel);
        GameData.getInstance().setScrollPane(scrollPane);
        GameData.getInstance().setBuildings(buildings);

        Building firstBuilding = GameData.getInstance().getCurrBuilding();
        Assertions.assertEquals(firstBuilding, null);

    }
    @Test
    void testGameDataBoyCrashed() {
        java.util.List<DataRecord> dataRecords = WebDataExtractor.extractWebTableData(1);
        List<Building> buildings = BuildingParser.parseDataToBuildings(dataRecords);
        BuildingPanel buildingPanel = new BuildingPanel(buildings);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setViewportView(buildingPanel);
        GameData.getInstance().setScrollPane(scrollPane);
        GameData.getInstance().setBuildings(buildings);

        List<Building> buildingsList = GameData.getInstance().getBuildings();
        Player player = new Player(0,0);
        GameData.getInstance().setSoloPlayer(player);

        GameData.getInstance().setResult(1);
        GameData.getInstance().nextBuilding();

        GameData.getInstance().recalculate();
        Assertions.assertEquals(player.getX(), GameData.getInstance().getCurrBuilding().getX()-50);
        Assertions.assertEquals(player.getY(),GameData.getInstance().getScrollPaneHeight()-100);
    }
}
