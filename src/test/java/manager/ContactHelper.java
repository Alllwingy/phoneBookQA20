package manager;

import dto.NewContactDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactHelper extends BaseHelper {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    By btnAddNewContact = By.xpath("//a[@href='/add']");
    By inputNameAddContact = By.xpath("//input[@placeholder='Name']");
    By inputLastNameAddContact = By.xpath("//input[@placeholder='Last Name']");
    By inputPhoneAddContact = By.xpath("//input[@placeholder='Phone']");
    By inputEmailAddContact = By.xpath("//input[@placeholder='email']");
    By inputAddressAddContact = By.xpath("//input[@placeholder='Address']");
    By inputDescriptionAddContact = By.xpath("//input[@placeholder='description']");
    By btnSaveNewContact = By.xpath("//button/b");
    By textH3ContactList = By.xpath("//H3");
    String phone = "";
    By phoneContact = By.xpath(String.format("//H3[contains(text(), '%s')]", phone));
    By btnRemoveContact = By.xpath("//button[2]");

    public void addNewContact(NewContactDto newContactDto) {

        clickBase(btnAddNewContact);
        typeTextBase(inputNameAddContact, newContactDto.getName());
        typeTextBase(inputLastNameAddContact, newContactDto.getLastName());
        typeTextBase(inputPhoneAddContact, newContactDto.getPhone());
        typeTextBase(inputEmailAddContact, newContactDto.getEmail());
        typeTextBase(inputAddressAddContact, newContactDto.getAddress());
        typeTextBase(inputDescriptionAddContact, newContactDto.getDescription());
        clickBase(btnSaveNewContact);
    }

    public boolean validateContactCreated(String phone) {

        return isElementByTextExistsInTheList(textH3ContactList, phone);
    }

    public boolean validateContactDeleted(String phone) {

        return new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementWithText(By.xpath(String.format("//H3[text()='%s']", phone)), phone));
    }

    public void openContactInfoByPhone(String phone) {

        clickBase(By.xpath(String.format("//H3[text()='%s']", phone)));
    }

    public void removeActiveContact() {

        clickBase(btnRemoveContact);
    }
}