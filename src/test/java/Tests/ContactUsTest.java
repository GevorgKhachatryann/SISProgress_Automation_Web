package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.CalendarLocators;
import Locators.ContactLocators;
import Locators.RegistrationLocators;
import methods.ApiRequests;
import methods.General;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class ContactUsTest extends BaseClass {
    @Test
    public void sendMessage(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        ContactLocators contactLocators = new ContactLocators();
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();

        driver.get(URL.APP_URL);
        general.scrollPageToElement(driver,driver.findElement(contactLocators.contactUs));
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactLocators.fullName));
        general.enterText(contactLocators.fullName,dto.getFullName());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        requests.generateRandomEmailForTest();
        general.enterText(contactLocators.email,dto.getEmail());
        general.enterText(contactLocators.question,dto.getQuestion());
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(contactLocators.send);
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactLocators.messageSent));
        general.assertThatElementContains(Constants.MESSAGE_HAS_BEEN_SENT,contactLocators.messageSent);
    }
    @Test
    public void verifyDisabledSendButtonWithoutCheckboxSelection(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        ContactLocators contactLocators = new ContactLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();

        driver.get(URL.APP_URL);
        general.scrollPageToElement(driver,driver.findElement(contactLocators.contactUs));
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactLocators.fullName));
        general.enterText(contactLocators.fullName,dto.getFullName());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        requests.generateRandomEmailForTest();
        general.enterText(contactLocators.email,dto.getEmail());
        general.enterText(contactLocators.question,dto.getQuestion());
        general.isDisabled(driver,contactLocators.sendBtn);
    }
}
