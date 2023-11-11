package tests;

import data.DataProviderLogin;
import dto.UserDTOLombok;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class LoginTests extends BaseTests {

    String repetedLoggerText;

    @BeforeClass (alwaysRun = true)
    public void preconditionsBeforeClass() {

        repetedLoggerText = " fill email input field with: %s and password input field with: %s and click button Login";

        if (app.isPageUrlHome())
            app.getUserHelper().openLoginPage();
    }

    @AfterMethod (alwaysRun = true)
    public void preconditionsBeforeMethod() {

        preconditionForLoginAndRegTests();
    }

    @Test (groups = { "smoke", "all" })
    public void positiveLoginUserDto(Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTO.getEmail(), userDTO.getPassword()));

        app.getUserHelper().fillLoginUserDto(userDTO);

        flagIsUserLogin = true;

        Assert.assertTrue(app.getUserHelper().validateContactTextDisplayMainMenu());
    }

    @Test (groups = { "smoke", "all" })
    public void positiveLoginUserDTOWith(Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTOWith.getEmail(), userDTOWith.getPassword()));

        app.getUserHelper().fillLoginUserDtoWith(userDTOWith);

        flagIsUserLogin = true;

        Assert.assertTrue(app.getUserHelper().validateContactTextDisplayMainMenu());
    }

    @Test (groups = { "smoke", "regression", "all" }, dataProvider = "loginCSV", dataProviderClass = DataProviderLogin.class)
    public void positiveLoginUserDtoLombok(UserDTOLombok userDP, Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDP.getEmail(), userDP.getPassword()));

        app.getUserHelper().fillLoginUserDtoLombok(userDP);

        flagIsUserLogin = true;

        Assert.assertTrue(app.getUserHelper().validateContactTextDisplayMainMenu());
    }

    @Test
    public void negative_UserDTOLombok_WrongEmail(Method method) {

        userDTOLombok.setEmail("winnergmail.com");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTOLombok.getEmail(), userDTOLombok.getPassword()));

        app.getUserHelper().fillLoginUserDtoLombok(userDTOLombok);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordCorrect());
    }

    @Test (dataProvider = "loginCSV", dataProviderClass = DataProviderLogin.class)
    public void negativeWrongPasswordNoDigits(UserDTOLombok userDP, Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDP.getEmail(), userDP.getPassword()));

        app.getUserHelper().fillLoginUserDtoLombok(userDP);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordCorrect());
    }

    @Test
    public void negativeWrongPasswordWrongSymbol(Method method) throws IOException {

        userDTOLombok.setPassword("123456Aa5");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTOLombok.getEmail(), userDTOLombok.getPassword()));

        app.getUserHelper().fillLoginUserDtoLombok(userDTOLombok);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordCorrect());
    }

    @Test
    public void negativeWrongPasswordNoLetters(Method method) throws IOException {

        userDTOLombok.setPassword("12345655$");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTOLombok.getEmail(), userDTOLombok.getPassword()));

        app.getUserHelper().fillLoginUserDtoLombok(userDTOLombok);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordCorrect());
    }
}