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
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setBuildings(List<Building> bs) {
        this.buildings = bs;
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
    public void nextBuilding() {
        this.currBuilding += 1;
    }
    public Building getCurrBuilding() {
        return buildings.get(currBuilding);
    }

    public void repaint() {
        firePropertyChange("repaint", null, null);
    }
}
