import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebDataExtractor {

    private static String air = "https://en.wikipedia.org/wiki/List_of_countries_by_air_pollution";
    private static String co2 = "https://en.wikipedia.org/wiki/List_of_countries_by_carbon_dioxide_emissions";
    private static String forest = "https://en.wikipedia.org/wiki/List_of_countries_by_forest_area#Share_of_national_land_which_is_forest";
    public static List<DataRecord> extractWebTableData(int selectedSource) {
        List<DataRecord> dataRecords = new ArrayList<>();
        String URL;
        int column1Index;
        int column2Index;
        switch (selectedSource) {
            case 1:
                URL = air;
                column1Index = 1;
                column2Index = 2;
                break;
            case 2:
                URL = co2;
                column1Index = 0;
                column2Index = 5;
                break;
            case 3:
                URL = forest;
                column1Index = 0;
                column2Index = 4;
                break;
            default:
                System.out.println("Invalid source selection.");
                return dataRecords; // Return empty list
        }
        try {
            System.out.println("Accessing " + URL + "...");

            Document source = Jsoup.connect(URL).get();
            Element table;
            if (3 == selectedSource) {
                // For Source3, we need to extract data from the second table
                table = source.select("table").get(1);
            } else {
                // For other sources, use the first table
                table = source.select("table").get(0);
            }

            Elements rows = table.select("tr");
            if (rows.isEmpty()) {
                System.out.println("No rows found in the table.");
                return dataRecords; // Return empty list
            }

            for (Element row : rows) {
                Elements columns = row.select("td");

                if (columns.size() >= Math.max(column1Index, column2Index) + 1) {
                    String column1 = columns.get(column1Index).text();
                    String column2 = columns.get(column2Index).text();

                    dataRecords.add(new DataRecord(column1, column2));
                }
            }

            System.out.println("Table data has been collected successfully.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("ERROR: Failed to access " + URL);
        }

        return dataRecords;
    }
}
