package testNG;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.store.Path;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Automating_Sitemap_XML {
	// Define the URL to test
    private static final String URL = "https://www.getcalley.com/page-sitemap.xml"; // Replace with your target website URL

    // Define the screen resolutions to test
    private static final Dimension[] RESOLUTIONS = {
            new Dimension(1920,1080),
            new Dimension(1366,768),
            new Dimension(1536,864 ),
            new Dimension(1024, 768)
    };

    // Define the browsers and their corresponding WebDrivers
    private static final Map<String, String> BROWSERS = new HashMap<>() {{
        put("chrome", "path/to/chromedriver"); // Update with the path to your ChromeDriver
        put("firefox", "path/to/geckodriver"); // Update with the path to your GeckoDriver
    }};

    // Base directory to save screenshots
    private static final String BASE_DIR = "screenshots";

    public static void main(String[] args) {
        // Loop through each browser
        for (Map.Entry<String, String> browser : BROWSERS.entrySet()) {
            System.setProperty("webdriver." + browser.getKey() + ".driver", browser.getValue());
            WebDriver driver = createWebDriver(browser.getKey());

            if (driver != null) {
                // Loop through each resolution
                for (Dimension resolution : RESOLUTIONS) {
                    // Set the window size
                    driver.manage().window().setSize(resolution);
                    takeScreenshot(driver, browser.getKey(), resolution);
                }
                // Close the browser
                driver.quit();
            }
        }
    }

    // Create WebDriver based on the browser type
    private static WebDriver createWebDriver(String browserName) {
        switch (browserName) {
            case "chrome":
               ChromeOptions chromeOptions = new ChromeOptions();
               return new ChromeDriver(chromeOptions);
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
            default:
                System.out.println("Unsupported browser: " + browserName);
                return null;
        }
    }

    // Take a screenshot and save it in a structured directory format
    private static void takeScreenshot(WebDriver driver, String browserName, Dimension resolution) {
        // Open the target URL
        driver.get(URL);

        // Allow some time for the page to load
        try {
            Thread.sleep(2000); // Adjust if necessary based on page load speed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create the directory structure based on browser and resolution
        String resolutionDir = BASE_DIR + "/" + browserName + "_" + resolution.getWidth() + "x" + resolution.getHeight();
        Path path = Paths.get(resolutionDir);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Take the screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(resolutionDir + "/UI_screenshots.png");
        try {
            Files.copy(screenshot.toPath(), destinationFile.toPath());
            System.out.println("Screenshot saved to " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}