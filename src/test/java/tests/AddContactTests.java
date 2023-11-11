package tests;

import dto.NewContactDto;
import org.testng.Assert;
import org.testng.annotations.*;

public class AddContactTests extends BaseTests {

    String phone;
    NewContactDto contact;
    boolean flagRefresh = false;

    @BeforeClass(alwaysRun = true)
    public void preconditionsBeforeClass() {

        if (app.isPageUrlHome())
            app.getUserHelper().openLoginPage();
        app.getUserHelper().fillLoginUserDtoLombok(userDTOLombok);
    }

    @BeforeMethod
    public void beforeMethod() {

        phone = random.generateStringDigits(12);
        logger.info("phone for the new contact: " + phone);

        contact = NewContactDto.builder()
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(phone)
                .email(faker.internet().emailAddress())
                .address(faker.address().fullAddress())
                .description(faker.demographic().race())
                .build();
    }

    @AfterMethod
    public void afterMethod() {

        preconditionForLoginAndRegTests();

        if (flagRefresh)
            app.refresh();
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {

        app.getUserHelper().logout();
    }

    @Test
    public void addContactPositive() {

        app.getContactHelper().addNewContact(contact);
        Assert.assertTrue(app.getContactHelper().validateContactCreated(phone));
    }

    @Test
    public void addContactNegativeWrongPhone() {

        contact.setPhone("654321");

        app.getContactHelper().addNewContact(contact);

        flagIsAlertPresent = true;
        flagRefresh = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongPhone());
    }

    @Test
    public void addContactNegativeWrongEmail() {

        contact.setEmail("kjhv");

        app.getContactHelper().addNewContact(contact);

        flagIsAlertPresent = true;
        flagRefresh = true;

        Assert.assertTrue(app.getUserHelper().validateMessageAlertWrongEmail());
    }
}
