import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        //add panels inside here
        GameData.getInstance().setSize(this.getWidth(), this.getHeight());
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


