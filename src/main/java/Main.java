import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main {
    //right now, code here is mainly for testing that the stuff works
    public static void main(String[] args) {
        List<DataRecord> dataRecords = WebDataExtractor.extractWebTableData();

        // Display the collected data
        System.out.println("Collected Data:");
        for (DataRecord record : dataRecords) {
            System.out.println(record.getHeader() + ") " + record.getCountry() + " : " + record.getNum());
        }

        List<Building> buildings = BuildingParser.parseDataToBuildings(dataRecords);


        // Display the parsed buildings
        System.out.println("Parsed Buildings:");
        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            System.out.println("Building " + (i + 1) + ": Length=" + building.getLength() + ", Breadth=" + building.getBreadth());
        }

//        List <Building> buildings2 = buildings.subList(0, Math.min(5, buildings.size()));
        // Create and configure the JFrame
        JFrame frame = new JFrame("Building Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        displayBuildings(buildings, frame);
        frame.setVisible(true);
    }


    private static void displayBuildings(List  <Building> buildings, JFrame frame) {
        double totalBreadth = 0;
        int maxHeight = 0;
        for (Building building : buildings) {
            totalBreadth += building.getBreadth();
            if (building.getLength() > maxHeight) {
                maxHeight = (int) building.getLength();
            }
        }

        // Calculate the scale factor to fit all buildings within the frame
        double scaleFactor = 2000 / totalBreadth;

        int finalMaxHeight = maxHeight;
        JPanel buildingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 10; // Starting x-coordinate for the first building
                int y = 10; // Starting y-coordinate for all buildings
                for (Building building : buildings) {
                    // Calculate the width and height of the building based on the scale factor
                    int width = (int) (building.getBreadth() * scaleFactor);
                    int height = (int) (building.getLength() * scaleFactor);
                    g.setColor(Color.BLUE);
                    g.fillRect(x, getHeight() - height - 10, width * 10, height);
                    g.setColor(Color.RED);
                    g.drawString("Building", x + 10, getHeight() - height - 20);
                    g.drawString("Length: " + building.getLength(), x + 10, getHeight() - height);
                    g.drawString("Breadth: " + building.getBreadth(), x + 10, getHeight() - height + 20);
                    x += width * 10 + 10; // Add a gap between buildings
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(buildings.size() * 50, finalMaxHeight + 25); // Adjust the size as needed
            }
        };


        frame.add(new JScrollPane(buildingPanel), BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(buildingPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

    }


}


