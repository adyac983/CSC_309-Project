import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class AirPollutionDataExtractor {

    public static void main(String[] args) {
        String url = "https://ourworldindata.org/explorers/air-pollution?tab=table&uniformYAxis=0&Pollutant=All+pollutants&Fuel=From+all+fuels+%28Total%29&Per+capita=false";

        try {
            Document doc = Jsoup.connect(url).get();
            extractDataFromHtml(doc);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    private static void extractDataFromHtml(Document doc) {
        Element table = doc.select("table").first();
        if (table != null) {
            Elements rows = table.select("tr");
            if (rows.size() < 2) {
                System.err.println("Error: Insufficient rows in the table.");
                return;
            }

            for (int i = 1; i < rows.size(); i++) { // Start from index 1 to skip headers row
                Element row = rows.get(i);
                Elements cells = row.select("td");
                if (!cells.isEmpty()) {
                    String country = "";
                    String value = "";
                    for (Element cell : cells) {
                        if (cell.hasClass("entity")) {
                            country = cell.text();
                        } else if (cell.hasClass("dimension-delta")) {
                            value = cell.text();
                        }
                    }
                    if (!country.isEmpty() && !value.isEmpty()) {
                        System.out.println("Country/Area: " + country + ", Value: " + value);
                    } else {
                        System.err.println("Error: Missing data in row " + i);
                    }
                } else {
                    System.err.println("Error: No cells found in row " + i);
                }
            }
        } else {
            System.err.println("Error: Table not found in HTML document.");
        }
    }

}

