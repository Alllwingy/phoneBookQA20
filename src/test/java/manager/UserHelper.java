package manager;

import dto.UserDTO;
import dto.UserDTOLombok;
import dto.UserDTOWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigurationProperties;

public class UserHelper extends BaseHelper {

    public UserHelper(WebDriver driver) {
        super(driver);
    }

    By btnOpenLoginForm = By.xpath("//*[@href='/login']");
    By inputEmail = By.xpath("//input[@name='email']");
    By inputPassword = By.xpath("//input[@name='password']");
    By btnLogin = By.xpath("//button[@name='login']");
//    By registrationButtonOnLoginForm = By.xpath("//button[@name='registration']");
    String registrationButtonOnLoginForm = "document.querySelector('[name=\"registration\"]').click();\n";
    By textContacts = By.xpath("//a[@href='/contacts']");
    By btnLogout = By.tagName("button");
    String contactsButtonTextToValidate = "CONTACTS";
    public String alertTextLogin_WrongEmailToValidate = "Wrong email or password";
    public String alertTextRegistration_WrongEmailToValidate = "Wrong email or password format";
    String alertTextAddContact_WrongPhoneToValidate = "Phone not valid: Phone number must contain only digits! And length min 10, max 15!";
    String alertTextAddContact_WrongEmailToValidate = "Email not valid";
    public String password = ConfigurationProperties.getProperty("password");


    public void openLoginPage() {

        clickBase(btnOpenLoginForm);
    }

    public void fillLoginUserDto(UserDTO userDTO) {

        typeTextBase(inputEmail, userDTO.getEmail());
        typeTextBase(inputPassword, userDTO.getPassword());
        click_Action(btnLogin);
    }

    public void fillLoginUserDtoWith(UserDTOWith userDTOWith) {

        typeTextBase(inputEmail, userDTOWith.getEmail());
        typeTextBase(inputPassword, userDTOWith.getPassword());
        click_Action(btnLogin);
    }

    public void fillLoginUserDtoLombok(UserDTOLombok user) {

        typeTextBase(inputEmail, user.getEmail());
        typeTextBase(inputPassword, user.getPassword());
        click_Action(btnLogin);
    }

    public void fillRegUserDtoLombok(UserDTOLombok user) {

        typeTextBase(inputEmail, user.getEmail());
        typeTextBase(inputPassword, user.getPassword());
        jsClickBase(registrationButtonOnLoginForm);
    }

    public boolean validateContactTextDisplayMainMenu() {

        return isTextEqual(textContacts, contactsButtonTextToValidate);
    }

    public boolean validateMessageAlertWrongEmailPasswordCorrect() {

        return isTextContainsGet2Strings(getTextAlert(), alertTextLogin_WrongEmailToValidate);
    }

    public boolean validateMessageAlertWrongEmailPasswordReg() {

        return isTextContainsGet2Strings(getTextAlert(), alertTextRegistration_WrongEmailToValidate);
    }

    public boolean validateMessageAlertWrongPhone() {

        return isTextContainsGet2Strings(getTextAlert(), alertTextAddContact_WrongPhoneToValidate);
    }

    public boolean validateMessageAlertWrongEmail() {

        return isTextContainsGet2Strings(getTextAlert(), alertTextAddContact_WrongEmailToValidate);
    }

    public void logout() {

        clickBase(btnLogout);
    }
}