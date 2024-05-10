import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    //right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {

        JFrame frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        ChoicePanel choicePanel = new ChoicePanel();
        frame.add(choicePanel);
        frame.setVisible(true);
    }
}


