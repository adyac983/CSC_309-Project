package mathleap;

import javax.swing.*;
import java.awt.GraphicsEnvironment;

public class MainGame {
    // right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Running in headless mode!");
        }
        JFrame frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        HomeScreen homeScreen = new HomeScreen();
        frame.add(homeScreen);
        frame.setVisible(true);
        GameData.getInstance().setHomeScreen(homeScreen);
    }
}
