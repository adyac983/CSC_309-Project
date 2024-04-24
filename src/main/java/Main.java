import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        //add panels inside here
        JPanel mainPanel = new JPanel();
        GameData.getInstance().setSize(this.getWidth(), this.getHeight());
        Building firstBuilding = GameData.getInstance().getCurrBuilding();

        BuildingPanel buildingPanel = new BuildingPanel(GameData.getInstance().getBuildings());
        Player player = new Player(firstBuilding.getX(),firstBuilding.getY(),10,30);

        mainPanel.add(buildingPanel);
        mainPanel.add(player);
        //GameData.getInstance().addPropertyChangeListener();
    }

    //right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {
        Main main = new Main();
        main.setTitle("Building data");
        main.setSize(800, 400);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);

    }
}


