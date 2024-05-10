import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GameData extends PropertyChangeSupport {

    private static GameData instance;
    private List<Building> buildings;
    private Player player;

    private int currBuilding = 0;

    private int numBuildings = 0;

    private JScrollPane sp = null;
    private int result = 0;

    private GameData() {
        super(new Object());
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setBuildings(List<Building> bs) {
        this.buildings = bs;
        this.numBuildings = bs.size();
    }
    public void setScrollPane(JScrollPane s) {
        this.sp = s;
    }

    public JScrollPane getSp() {
        return sp;
    }

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }
    public Player getPlayer() {
        return player;
    }
    public int getScrollPaneHeight() {
        return sp.getViewport().getHeight();
    }
    public void recalculate() {
        int x = 20;
        for (int i = 0; i < numBuildings; i++) {
            Building building = buildings.get(i);
            int width = building.getBreadth();
            int height = (int) (building.getLength());
            building.setX(x);
            building.setY(height);
            //System.out.println("y: " + height);
            x += width + 70;
        }
        if (player != null) {
            Building curr = getCurrBuilding();
            System.out.println("curr building height: " + curr.getY());
            if (result == 0) {
                player.moveTo(curr.getX(),getScrollPaneHeight()-curr.getY()-100);
            }
            else {
                player.moveTo(curr.getX()-50,getScrollPaneHeight()-100);
            }
        }
        System.out.println("player x:" + player.getX());
        System.out.println("player y:" + player.getY());

    }
    public void setResult(int r) {
        this.result = r;
    }
    public List<Building> getBuildings() {
        return buildings;
    }
    public void nextBuilding() {
        this.currBuilding += 1;
    }
    public Building getCurrBuilding() {
        if (buildings.isEmpty()) {
            return null;
        }
        return buildings.get(currBuilding);
    }
}
