package test;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.*;
import java.util.HashMap;

import manager.ApplicationManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;


public class TestPageLoadStrategy extends ApplicationManager {

    WebDriver driver;
    String username = "<lambdtest_username>";
    String accessKey = "<lambdtest_accesskey>";

    ChromeOptions chromeOptions = new ChromeOptions();
    HashMap<String, Object> ltOptions = new HashMap<String, Object>();


    private void setup(String buildName) {


//        chromeOptions.setPlatformName("Windows 10");
//        chromeOptions.setBrowserVersion("108.0");


        ltOptions.put("project", "Page Load Strategy");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        ltOptions.put("networkThrottling", "Regular 2G");
        ltOptions.put("build", buildName);
    }


    private void checkPageLoad() {

        driver = new FirefoxDriver();

        Instant startTime = Instant.now();
        System.out.println("Page load started at : " + startTime.toString());

        System.out.println("Navigating to the URL");
        driver.get("https://ilcarro.web.app/search");

        Instant endTime = Instant.now();
        System.out.println("Page load ended at : " + endTime.toString());


        Duration duration = Duration.between(startTime, endTime);
        System.out.println("Total PageLoad time : " + duration.toMillis() + " milli seconds");
    }


    @Test
    public void testNormalStrategy() {
        // To set PageLoadStrategy = Normal
        setup("Page Load Strategy - Normal");


        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.setCapability("LT:Options", ltOptions);
        checkPageLoad();
    }


    @Test
    public void testEagerStrategy() {
        // To set PageLoadStrategy = Eager
        setup("Page Load Strategy - Eager");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.setCapability("LT:Options", ltOptions);
        checkPageLoad();
    }


    @Test
    public void testNoneStrategy() {
        // To set PageLoadStrategy = None
        setup("Page Load Strategy - None");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        chromeOptions.setCapability("LT:Options", ltOptions);
        checkPageLoad();
    }
}