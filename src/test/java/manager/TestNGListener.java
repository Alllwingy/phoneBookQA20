package manager;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DatesUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TestNGListener implements ITestListener {

    Logger logger = LoggerFactory.getLogger(TestNGListener.class);
    Throwable throwable = new Throwable();

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("started test: " + result.getName());
        ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("test success: " + result.getName());
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);

        logger.info("Failure: " + result.getName() + "start on exception in TestNGListener class");
        String fileName = "src/test/screenshots/screenshot_" + DatesUtils.getDateString() + ".png";
        logger.info("took screenshot with name: " + fileName);
//        logger.error(throwable.getMessage());
        logger.error(Arrays.toString(throwable.getStackTrace()));
//        logger.error(throwable.toString());
        takeScreenshot(ApplicationManager.driver, fileName);
    }

    public void takeScreenshot(WebDriver driver, String filePath) {

        try {

            Files.copy(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath));

        } catch (IOException e) {

            e.getStackTrace();
            logger.error("got an exception for adding screenshot to the folder: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("test skipped: " + result.getName());
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logger.info("test out of time: " + result.getName());
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("start test - onstart - : " + context.getName());
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("all code executed: passed tests: " + context.getPassedTests());
        logger.info("all code executed: failed tests: " + context.getFailedTests());
        ITestListener.super.onFinish(context);
    }
}
