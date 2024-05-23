import javax.swing.*;

public class Main {
    // right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {
        JFrame frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        HomeScreen homeScreen = new HomeScreen();
        frame.add(homeScreen);
        frame.setVisible(true);
    }
}
