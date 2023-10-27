package tests;

import dto.UserDTO;
import dto.UserDTOLombok;
import dto.UserDTOWith;
import manager.AppleManager;
import manager.TestNGListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import utils.ConfigurationProperties;
import utils.FakerGenerator;
import utils.RandomGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;

@Listeners(TestNGListener.class)
public class BaseTests {

    static AppleManager apple = new AppleManager();
    Logger logger = LoggerFactory.getLogger(BaseTests.class);
    RandomGenerator random = new RandomGenerator();
    FakerGenerator faker = new FakerGenerator();
    UserDTO userDTO;
    UserDTOWith userDTOWith;
    UserDTOLombok user;

    boolean isFlagLogin = false, isFlagAlert = false;

    @BeforeSuite (alwaysRun = true)
    public void before() {

        apple.setUp();
    }

    @AfterSuite (alwaysRun = true)
    public void after() {

        apple.tearDown();
    }

    @BeforeMethod (alwaysRun = true)
    public void loggerBeforeMethod(Method method) {

        userDTO = new UserDTO(
                ConfigurationProperties.getProperty("email"),
                ConfigurationProperties.getProperty("password"));

        userDTOWith = new UserDTOWith()
                .withEmail(ConfigurationProperties.getProperty("email"))
                .withPassword(ConfigurationProperties.getProperty("password"));

        user = UserDTOLombok.builder()
                .email(ConfigurationProperties.getProperty("email"))
                .password(ConfigurationProperties.getProperty("password"))
                .build();

        logger.info("__________________________________________________________________________");
        logger.info("started method: " + method.getName() + " with parameters: " + Arrays.toString(method.getParameters()));
    }

    @AfterMethod (alwaysRun = true)
    public void loggerAfterMethod(Method method) {

        logger.info("stopped method: " + method.getName() + ", with parameters: " + Arrays.toString(method.getParameters()));
    }

    public void flagAfterMethod() {

        if (isFlagLogin) {
            isFlagLogin = false;
            apple.getUserHelperToApply().logout();
        }
        if (isFlagAlert) {
            isFlagAlert = false;
            apple.getUserHelperToApply().alertAccept(true);
        }
    }
}