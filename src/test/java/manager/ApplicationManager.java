package manager;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigurationProperties;
import utils.DatesUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    static EventFiringWebDriver driver;
    static String browser;
    UserHelper userHelper;
    ContactHelper contactHelper;
    Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    public ApplicationManager() {

        browser = System.getProperty("browser", BrowserType.CHROME);
    }

    public void init() {

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
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//        driver.register(new WDListener());

        userHelper = new UserHelper(driver);
        contactHelper = new ContactHelper(driver);
    }

    public void navigateToMainPage() {

        driver.navigate().to("https://telranedu.web.app/home");
    }

    public void refresh() {

        driver.navigate().refresh();
    }

    public UserHelper getUserHelper() {

        return userHelper;
    }

    public ContactHelper getContactHelper() {

        return contactHelper;
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