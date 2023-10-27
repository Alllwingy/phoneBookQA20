package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class LoginTests extends BaseTests {

    String repetedLoggerText;

    @BeforeClass (alwaysRun = true)
    public void beforeClass() {

        repetedLoggerText = " fill email input field with: %s and password input field with: %s and click button Login";

        if (apple.isPageUrlHome())
            apple.getUserHelperToApply().openLoginPage();
    }

    @AfterMethod (alwaysRun = true)
    public void afterMethod() {

        flagAfterMethod();
    }

    @Test (groups = { "smoke", "all" })
    public void positive_UserDTO(Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTO.getEmail(), userDTO.getPassword()));

        apple.getUserHelperToApply().login(userDTO);

        isFlagLogin = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfContactsButtonOnNavigationBar());
    }

    @Test (groups = { "smoke", "all" })
    public void positive_UserDTOWith(Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, userDTOWith.getEmail(), userDTOWith.getPassword()));

        apple.getUserHelperToApply().login(userDTOWith);

        isFlagLogin = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfContactsButtonOnNavigationBar());
    }

    @Test
    public void positive_UserDTOLombok(Method method) {

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().login(user);

        isFlagLogin = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfContactsButtonOnNavigationBar());
    }

    @Test  (groups = { "smoke", "regression", "all" })
    public void negative_UserDTOLombok_WrongEmail(Method method) {

        user.setEmail("winnergmail.com");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().login(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextLogin());
    }

    @Test
    public void negative_UserDTOLombok_EmptyEmail(Method method) {

        user.setEmail("");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().login(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextLogin());
    }

    @Test
    public void negative_UserDTOLombok_WrongPassword(Method method) throws IOException {

        user.setPassword("Test");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().login(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextLogin());
    }

    @Test
    public void negative_UserDTOLombok_EmptyPassword(Method method) throws IOException {

        user.setPassword("");

        logger.info(String.format("in method: " + method.getName()
                + repetedLoggerText, user.getEmail(), user.getPassword()));

        apple.getUserHelperToApply().login(user);

        isFlagAlert = true;

        Assert.assertTrue(apple.getUserHelperToApply().validationOfAlertTextLogin());
    }
}