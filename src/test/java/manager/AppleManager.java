package manager;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigurationProperties;
import utils.DatesUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppleManager {

    static EventFiringWebDriver driver;
    UserHelper userHelper;
    Logger logger = LoggerFactory.getLogger(AppleManager.class);
    static String browser;

    public AppleManager() {

        browser = System.getProperty("browser", BrowserType.CHROME);
    }

    public void setUp() {

        if (browser.equals(BrowserType.CHROME)) {

            driver = new EventFiringWebDriver(new ChromeDriver());
            logger.info("CHROME driver");

        } else if (browser.equals(BrowserType.FIREFOX)) {

            driver = new EventFiringWebDriver(new FirefoxDriver());
            logger.info("FIREFOX driver");
        }

        driver.navigate().to(ConfigurationProperties.getProperty("url"));
        logger.info("open: " + ConfigurationProperties.getProperty("url") + " Start testing: ");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

//        driver.register(new WDListener());

        userHelper = new UserHelper(driver);
    }

    public void navigateToMainPage() {

        driver.navigate().to("https://telranedu.web.app/home");
    }

    public UserHelper getUserHelperToApply() {

        return userHelper;
    }

    public static String takeScreenshot(String filePath) {

        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
        String currentDate = DatesUtils.getDateString();
        File destinationFile = new File(filePath);

        try {

            Files.copy(sourceFile, destinationFile);

        } catch (
                IOException e) {

            throw new RuntimeException(e);
        }

        return currentDate;
    }

    public void tearDown() {

        driver.quit();
    }

    public boolean isPageUrlHome() {

        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl + "----------------- url");
        return currentUrl.equals(ConfigurationProperties.getProperty("url"));
    }
}