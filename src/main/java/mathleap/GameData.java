package mathleap;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class GameData extends PropertyChangeSupport {
    private static GameData instance;
    private List<Building> buildings;
    private int currBuilding = 0;
    private int numBuildings = 0;
    private JScrollPane sp = null;
    private int result = 0;
    private HomeScreen homeScreen;
    private JFrame frame;
    private int score = 0;
    private Player player = null;

    // Multiplayer
    private int multiplayer = 1;
    private int whoIAm = 0;
    private int serverPlayerScore;
    private int clientPlayerScore;
    public static final int SERVER = 0;
    public static final int CLIENT = 1;

    private Timer smoothScrollTimer;
    private Point targetPosition;
    private Point startPosition;
    private int scrollDuration = 500; // Duration of the scroll in milliseconds
    private long startTime;
    private String playerchoice;
    private int levelChoice;
    private JLabel hpLabel;
    private JLabel scoreLabel;

    private JLabel opponentScoreLabel;
    private int serverPlayerHp = 3;
    private int clientPlayerHp = 3;

    private GameData() {
        super(new Object());
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setHomeScreen(HomeScreen s) {
        this.homeScreen = s;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
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



    public int getScrollPaneHeight() {
        return sp.getViewport().getHeight();
    }

    public void recalculate() {
        if (player.getHp() <= 0) {
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
            GameData.getInstance().changeHpLabelText();
            Building curr = getCurrBuilding();
            if (curr.getX() + 150 < player.getX() + 20) { // player took too long to answer
                result = 1;
                player.setHp(player.getHp() - 1);
                GameData.getInstance().changeHpLabelText();
            }

            if (result == 0) { // default or player answered correctly
                player.moveTo(player.getX(), getScrollPaneHeight() - curr.getY() - 100);
                BuildingPanel.startTimer();
                int scrollToX = curr.getX() - 50;
                int scrollToY = 0;
                smoothScrollTo(scrollToX, scrollToY);
            } else {
                if (player.getY() != getScrollPaneHeight() - 100) { // move player to bottom of next building
                    nextBuilding();
                    curr = getCurrBuilding();
                }
                player.moveTo(curr.getX() - 50, getScrollPaneHeight() - 100);
                BuildingPanel.stopTimer();

                int scrollToX = curr.getX() - 50;
                int scrollToY = 0;
                smoothScrollTo(scrollToX, scrollToY);
            }
        }
    }

    private void smoothScrollTo(int targetX, int targetY) {
        targetPosition = new Point(targetX, targetY);
        startPosition = sp.getViewport().getViewPosition();
        startTime = System.currentTimeMillis();

        if (smoothScrollTimer != null && smoothScrollTimer.isRunning()) {
            smoothScrollTimer.stop();
        }

        smoothScrollTimer = new Timer(20, e -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            float progress = (float) elapsedTime / scrollDuration;
            if (progress > 1f) progress = 1f;

            int newX = startPosition.x + Math.round(progress * (targetPosition.x - startPosition.x));
            int newY = startPosition.y + Math.round(progress * (targetPosition.y - startPosition.y));
            sp.getViewport().setViewPosition(new Point(newX, newY));

            if (progress >= 1f) {
                smoothScrollTimer.stop();
            }
        });

        smoothScrollTimer.start();
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
        this.serverPlayerScore = 0;
        this.clientPlayerScore = 0;
        this.clientPlayerHp = 3;
        this.serverPlayerHp = 3;
    }

    public void setClientPlayerScore(int s) {
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

    public int getServerPlayerScore() {
        return serverPlayerScore;
    }

    public int getClientPlayerScore() {
        return clientPlayerScore;
    }

    public int getWhoIAm() {
        return whoIAm;
    }
    public String getPlayerchoice() {
        return playerchoice;
    }

    public void setPlayerchoice(String playerchoice) {
        this.playerchoice = playerchoice;
    }

    public int getLevelChoice() {
        return levelChoice;
    }

    public void setLevelChoice(int levelChoice) {
        this.levelChoice = levelChoice;
    }
    public void changeHpLabelText() {
        if (GameData.getInstance().getPlayer() != null) {
            hpLabel.setText("HP: " + GameData.getInstance().getPlayer().getHp());
        }
    }

    // Method to update Score label text
    public void changeScoreLabelText() {
        if (multiplayer == 0) {
            scoreLabel.setText("Score: " + GameData.getInstance().getScore());
        }
        else {
            if (GameData.getInstance().getWhoIAm() == GameData.SERVER) {
                scoreLabel.setText("Score: " + GameData.getInstance().getServerPlayerScore());
                opponentScoreLabel.setText("Opponent Score: " + GameData.getInstance().getClientPlayerScore());
            } else {
                scoreLabel.setText("Score: " + GameData.getInstance().getClientPlayerScore());
                opponentScoreLabel.setText("Opponent Score: " + GameData.getInstance().getServerPlayerScore());
            }
        }
    }

    public void setHpLabel(JLabel hpLabel) {
        this.hpLabel = hpLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void setOpponentScoreLabel(JLabel opponentScoreLabel) {
        this.opponentScoreLabel = opponentScoreLabel;
    }

    public int getServerPlayerHp() {
        return serverPlayerHp;
    }

    public void setServerPlayerHp(int serverPlayerHp) {
        this.serverPlayerHp = serverPlayerHp;
    }

    public int getClientPlayerHp() {
        return clientPlayerHp;
    }

    public void setClientPlayerHp(int clientPlayerHp) {
        this.clientPlayerHp = clientPlayerHp;
    }
}
