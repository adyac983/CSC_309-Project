import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebDataExtractor {

    private static String URL = "https://en.wikipedia.org/wiki/List_of_countries_by_air_pollution";

    public static List<DataRecord> extractWebTableData() {
        List<DataRecord> dataRecords = new ArrayList<>();

        try {
            System.out.println("Accessing " + URL + "...");

            Document source = Jsoup.connect(URL).get();
            Element table = source.select("table").get(0); // Adjust the index based on the specific table you want to scrape
            Elements rows = table.select("tr");
            if (rows.isEmpty()) {
                System.out.println("No rows found in the table.");
                return dataRecords; // Return empty list
            }

            for (Element row : rows) {
                Elements columns = row.select("td");

                if (columns.size() >= 3) {
                    String column1 = columns.get(0).text();
                    String column2 = columns.get(1).text();
                    String column3 = columns.get(2).text();

                    dataRecords.add(new DataRecord(column1, column2, column3));
                }
            }

            System.out.println("Table data has been collected successfully.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("ERROR : Failed to access " + URL);
        }

        return dataRecords;
    }
}
