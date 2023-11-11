package tests;

import com.github.javafaker.Faker;
import dto.NewContactDto;
import dto.UserDTO;
import dto.UserDTOLombok;
import dto.UserDTOWith;
import manager.ApplicationManager;
import manager.TestNGListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import utils.ConfigurationProperties;
import utils.RandomGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;

@Listeners(TestNGListener.class)
public class BaseTests {

    static ApplicationManager app = new ApplicationManager();
    Logger logger = LoggerFactory.getLogger(BaseTests.class);
    RandomGenerator random = new RandomGenerator();
    Faker faker = new Faker();

    UserDTO userDTO = new UserDTO(
            ConfigurationProperties.getProperty("email"),
            ConfigurationProperties.getProperty("password"));

    UserDTOWith userDTOWith = new UserDTOWith()
            .withEmail(ConfigurationProperties.getProperty("email"))
            .withPassword(ConfigurationProperties.getProperty("password"));

    UserDTOLombok userDTOLombok = UserDTOLombok.builder()
            .email(ConfigurationProperties.getProperty("email"))
            .password(ConfigurationProperties.getProperty("password"))
            .build();

    boolean flagIsUserLogin = false, flagIsAlertPresent = false;

    @BeforeSuite(alwaysRun = true)
    public void setup() {

        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void stop() {

        app.tearDown();
    }

    @AfterMethod(alwaysRun = true)
    public void afterEachMethod(Method method) {

        logger.info("stopped method: " + method.getName() + ", with parameters: " + Arrays.toString(method.getParameters()));
    }

    public void preconditionForLoginAndRegTests() {

        if (flagIsAlertPresent) {
            flagIsAlertPresent = false;
            app.getUserHelper().clickAcceptAlert(true);
        }
        if (flagIsUserLogin) {
            flagIsUserLogin = false;
            app.getUserHelper().logout();
        }
    }
}