package tests;

import data.DataProviderLogin;
import data.DataProviderRegistration;
import dto.UserDTOLombok;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class RegistrationTests extends BaseTests {

    UserDTOLombok user;
    String repetedLoggerText;

    @BeforeClass (alwaysRun = true)
    public void beforeClass() {

        repetedLoggerText = " fill email input field with: %s and password input field with: %s and click button Login";

        if (app.isPageUrlHome())
            app.getUserHelper().openLoginPage();
    }

    @BeforeMethod (alwaysRun = true)
    public void beforeMethod() {

        user = UserDTOLombok.builder()
                .email(faker.internet().emailAddress())
                .password(app.getUserHelper().password)
                .build();
    }

    @AfterMethod (alwaysRun = true)
    public void preconditionsBeforeMethod() {

        preconditionForLoginAndRegTests();
    }

    @Test  (groups = { "smoke", "all" }, dataProvider = "data", dataProviderClass = DataProviderRegistration.class)
    public void positiveRegistration(UserDTOLombok userDP, Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDP.getEmail(), userDP.getPassword()));

        app.getUserHelper().fillRegUserDtoLombok(userDP);

        flagIsUserLogin = true;

        Assert.assertTrue(app.getUserHelper().validateContactTextDisplayMainMenu());
    }

    @Test
    public void positiveRegistrationTest() {
        app.getUserHelper().fillRegUserDtoLombok(user);

        flagIsUserLogin = true;
        Assert.assertTrue(app.getUserHelper().validateContactTextDisplayMainMenu());
    }

    @Test  (groups = { "regression", "all" })
    public void negative_UserDTOLombok_WrongEmail(Method method) {

        user.setEmail("abqduv.co.il");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        app.getUserHelper().fillRegUserDtoLombok(user);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordReg());
    }

    @Test (dataProvider = "data", dataProviderClass = DataProviderRegistration.class)
    public void negativeRegNoSymbol(UserDTOLombok userDP, Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDP.getEmail(), userDP.getPassword()));

        app.getUserHelper().fillRegUserDtoLombok(userDP);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordReg());
    }

    @Test
    public void negativeRegNoLetters(Method method) {

        user.setPassword("12345699#");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        app.getUserHelper().fillRegUserDtoLombok(user);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordReg());
    }

    @Test
    public void negativeRegNoDigits(Method method) {

        user.setPassword("Agshsjsks#");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        app.getUserHelper().fillRegUserDtoLombok(user);

        flagIsAlertPresent = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmailPasswordReg());
    }
}