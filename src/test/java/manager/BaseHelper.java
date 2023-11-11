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
    Logger logger = LoggerFactory.getLogger(ApplicationManager.class);


    public BaseHelper(WebDriver driver) {

        this.driver = driver;
    }

    private WebElement findElementBase(By locator) {

        logger.info("findElementBy locator: " + locator);
        return driver.findElement(locator);
    }

    private List<WebElement> findElementsBase(By locator) {

        return driver.findElements(locator);
    }

    public WebElement getElement(By locator) {

        return findElementBase(locator);
    }

    public void clickBase(By locator) {

        findElementBase(locator).click();
    }

    public void click_Action(By locator) {

        Actions action = new Actions(driver);
        action.moveToElement(findElementBase(locator)).click().perform();
    }

    public void click_Action(By locator, int right, int down) {

        Rectangle rectangle = findElementBase(locator).getRect();
        int x = rectangle.getX() + rectangle.getWidth() / right;
        int y = rectangle.getY() + rectangle.getHeight() / down;
//        int x = rectangle.getX() + right;
//        int y = rectangle.getY() + down;

        Actions action = new Actions(driver);
        action.moveByOffset(x, y).click().perform();
    }

    public void pause(int seconds) {

        try {

            Thread.sleep(seconds * 1_000L);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
    }

    public void typeTextBase(By locator, String text) {

        WebElement el = findElementBase(locator);
        el.click();
        el.clear();
        el.sendKeys(text);
    }

    public String getTextBase(By locator) {

        return findElementBase(locator).getText().trim().toUpperCase();
    }

    public boolean isElementPresent(By locator) {

        return !findElementsBase(locator).isEmpty();
    }

    public void jsClickBase(String locator) {

        ((JavascriptExecutor) driver).executeScript(locator);
    }

    public boolean isTextEqual(By locator, String expectedResult) {

        String actualResult = getTextBase(locator);
        expectedResult = expectedResult.toUpperCase();

        if (expectedResult.equals(actualResult))
            return true;
        else {

            System.out.println("expected result: " + expectedResult + "\nactual result: " + actualResult);
            return false;
        }
    }

    public boolean isTextContainsGet2Strings(String actualResult, String expectedResult) {

        actualResult = actualResult.toUpperCase();
        expectedResult = expectedResult.toUpperCase();

        if (actualResult.contains(expectedResult))
            return true;
        else {

            System.out.println("expected result: " + expectedResult + "\nactual result: " + actualResult);
            return false;
        }
    }

    public String getTextAlert() {

        wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        return alert.getText().trim().toUpperCase();
    }

    public void clickAcceptAlert(boolean accept) {

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

    public boolean isElementByTextExistsInTheList(By locator, String phone) {

        List<WebElement> list = findElementsBase(locator);

        if (list.isEmpty())
            return false;

        for (WebElement l : list) {

            if (l.getText().equals(phone)) {
                return true;
            }
        }

        return false;
    }
}