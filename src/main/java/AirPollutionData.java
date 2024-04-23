import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class AirPollutionData {

    public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");

        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the website
        driver.get("https://ourworldindata.org/explorers/air-pollution");

        // Find and click the "table" button
        WebElement tableButton = driver.findElement(By.xpath("//button[contains(text(), 'table')]"));
        tableButton.click();

        // Wait for the table to load (you may need to adjust the wait time based on the website's speed)
        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Extract data from the table using Jsoup or Selenium's methods
        String htmlContent = driver.getPageSource();
        // Call your data extraction method with the HTML content (e.g., extractDataFromHtml(htmlContent))
        try {
            Document doc = Jsoup.connect("\"https://ourworldindata.org/explorers/air-pollution\"").get();
            extractDataFromHtml1(doc);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        // Close the browser
        driver.quit();
    }

    private static void extractDataFromHtml1(Document doc) {
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

