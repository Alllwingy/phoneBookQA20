package tests;

import dto.NewContactDto;
import org.testng.Assert;
import org.testng.annotations.*;

public class DeleteContactTests extends BaseTests {

    static NewContactDto contact;
    static String phone;

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
        app.refresh();
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {

        app.getUserHelper().logout();
    }

    @Test
    public static void deleteCreatedContactPositive() {

        app.getContactHelper().addNewContact(contact);

        app.getContactHelper().openContactInfoByPhone(phone);
        app.getContactHelper().removeActiveContact();

        Assert.assertTrue(app.getContactHelper().validateContactDeleted(phone));
    }
}
