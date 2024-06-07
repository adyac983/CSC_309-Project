import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.List;
public class GameData extends PropertyChangeSupport {
    private static GameData instance;
    private List<Building> buildings;
    private Player soloplayer;
    private int currBuilding = 0;
    private int numBuildings = 0;
    private JScrollPane sp = null;
    private int result = 0;
    private HomeScreen homeScreen;
    private JFrame frame;
    private int score = 0;
    private Player currentPlayer = null;

    //multiplayer
    private int multiplayer = 1;
    private int whoIAm = 0;
    private int serverPlayerScore;
    private int clientPlayerScore;
    private Player serverPlayer;
    private Player clientPlayer;
    public static final int SERVER = 0;
    public static final int CLIENT = 1;

    private GameData() {
        super(new Object());
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public void setHomeScreen(HomeScreen s) {
        this.homeScreen = s;
    }
    public void setSoloPlayer(Player player) {
        this.soloplayer = player;
    }
    public Player getCurrentPlayer() {
        Player player;
        if (multiplayer == 1)
            player = soloplayer;
        else if (whoIAm == GameData.CLIENT)
            player = clientPlayer;
        else
            player = serverPlayer;
        this.currentPlayer = player;
        return player;
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
        return currentPlayer;
    }
    public int getScrollPaneHeight() {
        return sp.getViewport().getHeight();
    }
    public void recalculate() {
        Player player = getCurrentPlayer();

        if (multiplayer == 1 && player.getHp() <= 0) {
            Gameover();
        }

        // Calculate building positions
        int x = 20;
        for (int i = 0; i < numBuildings; i++) {
            Building building = buildings.get(i);
            int width = building.getBreadth();
            int height = (int) (building.getLength());
            building.setX(x);
            building.setY(height);
            x += width + 70;
        }

        // Update UI and check player position
        if (player != null) {
            BuildingPanel.changeHpLabelText();
            Building curr = getCurrBuilding();
            if (curr.getX() + 150 < player.getX() + 20) { // player took too long to answer
                result = 1;
                player.setHp(player.getHp() - 1);
                BuildingPanel.changeHpLabelText();
            }


            if (result == 0) { // default or player answered correctly
                player.moveTo(player.getX(), getScrollPaneHeight() - curr.getY() - 100);
                BuildingPanel.startTimer();
                int scrollToX = curr.getX() - 50;
                int scrollToY = 0;
                sp.getViewport().setViewPosition(new Point(scrollToX, scrollToY));
            } else {
                if (player.getY() != getScrollPaneHeight() - 100) { // move player to bottom of next building
                    nextBuilding();
                    curr = getCurrBuilding();
                }
                player.moveTo(curr.getX() - 50, getScrollPaneHeight() - 100);
                BuildingPanel.stopTimer();


                int scrollToX = curr.getX() - 50;
                int scrollToY = 0;
                sp.getViewport().setViewPosition(new Point(scrollToX, scrollToY));
            }
        }
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
    public void Gameover() {
        BuildingPanel.stopTimer();
        homeScreen.GameOver();
        frame.dispose();
    }
    public int getScore() {
        return score;
    }
    public void setScore(int s) {
        this.score = s;
    }
    public void reset() {
        this.score = 0;
        this.currBuilding = 0;
        this.result = 0;
    }
    public void setClientPlayerScore (int s) {
        clientPlayerScore = s;
    }

    public void setServerPlayerScore(int s) {
        serverPlayerScore = s;
    }
    public void setWhoIAm(int client) {
        this.whoIAm = client;
    }
    public void setMultiplayer(int multiplayer) {
        this.multiplayer = multiplayer;
    }
    public int getMultiplayer() {
        return this.multiplayer;
    }
    public void setServerPlayer(Player p) {
        this.serverPlayer = p;
    }
    public void setClientPlayer(Player p ) {
        this.clientPlayer = p;
    }

    public int getServerPlayerScore() {
        return serverPlayerScore;
    }

    public int getClientPlayerScore() {
        return clientPlayerScore;
    }

    public int getWhoIAm() {
        return whoIAm;
    }
}