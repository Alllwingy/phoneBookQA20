package tests;

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

        if (apple.isPageUrlHome())
            apple.getUserHelperToApply().openLoginPage();
    }

    @BeforeMethod (alwaysRun = true)
    public void beforeMethod() {

        user = UserDTOLombok.builder()
                .email(faker.generateEmail_Faker())
                .password(apple.getUserHelperToApply().password)
                .build();
    }

    @AfterMethod (alwaysRun = true)
    public void afterMethod() {

        flagAfterMethod();
    }

    @Test  (groups = { "smoke", "all" })
    public void positive_UserDTOLombok_WFaker(Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().registration(user);

        isFlagLogin = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfContactsButtonOnNavigationBar());
    }

    @Test  (groups = { "regression", "all" })
    public void negative_UserDTOLombok_WrongEmail(Method method) {

        user.setEmail("abqduv.co.il");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().registration(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextRegistraton());
    }

    @Test
    public void negative_UserDTOLombok_EmptyEmail(Method method) {

        user.setEmail("");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().registration(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextRegistraton());
    }

    @Test
    public void negative_UserDTOLombok_WrongPassword(Method method) {

        user.setPassword("Test");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().registration(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextRegistraton());
    }

    @Test
    public void negative_UserDTOLombok_EmptyPassword(Method method) {

        user.setPassword("");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().registration(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextRegistraton());
    }
}