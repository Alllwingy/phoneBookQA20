package manager;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseHelper {

    WebDriver driver;
    public WebDriverWait wait;
    Logger logger = LoggerFactory.getLogger(AppleManager.class);


    public BaseHelper(WebDriver driver) {

        this.driver = driver;
    }

    private WebElement findElementBy(By locator) {

        logger.info("findElementBy locator: " + locator);
        return driver.findElement(locator);
    }

    private List<WebElement> findElementsBy(By locator) {

        return driver.findElements(locator);
    }

    public WebElement getElement(By locator) {

        return findElementBy(locator);
    }

    public void click_Mouse(By locatorAnd) {

        findElementBy(locatorAnd).click();
    }

    public void click_Action(By locator) {

        Actions action = new Actions(driver);
        action.moveToElement(findElementBy(locator)).click().perform();
    }

    public void click_Action(By locator, int right, int down) {

        Rectangle rectangle = findElementBy(locator).getRect();
        int x = rectangle.getX() + rectangle.getWidth() / right;
        int y = rectangle.getY() + rectangle.getHeight() / down;
//        int x = rectangle.getX() + right;
//        int y = rectangle.getY() + down;

        Actions action = new Actions(driver);
        action.moveByOffset(x,y).click().perform();
    }

    public void pause(int seconds) {

        try {

            Thread.sleep(seconds * 1_000L);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
    }

    public void fill(By locator, String text) {

        WebElement elementTo = findElementBy(locator);
        elementTo.click();
        elementTo.clear();
        elementTo.sendKeys(text);
    }

    public String getTextBy(By locator) {

        return findElementBy(locator).getText().trim().toUpperCase();
    }

    public boolean isElementPresent(By locator) {

        return !findElementsBy(locator).isEmpty();
    }

    public boolean isResultsEquals(By locator, String expectedResult) {

        String actualResult = getTextBy(locator);
        expectedResult = expectedResult.toUpperCase();

        if (expectedResult.equals(actualResult))
            return true;
        else {

            System.out.println("expected result: " + expectedResult + "\nactual result: " + actualResult);
            return false;
        }
    }

    public boolean isTextContains(String actualResult, String expectedResult) {

        actualResult = actualResult.toUpperCase();
        expectedResult = expectedResult.toUpperCase();

        if (actualResult.contains(expectedResult))
            return true;
        else {

            System.out.println("expected result: " + expectedResult + "\nactual result: " + actualResult);
            return false;
        }
    }

    public String alertGetText() {

        wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        return alert.getText().trim().toUpperCase();
    }

    public void alertAccept(boolean accept) {

        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        if (accept)
            alert.accept();
        else
            alert.dismiss();
    }

    public void alertSendKeys(String text) {

        wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        alert.sendKeys(text);
    }
}