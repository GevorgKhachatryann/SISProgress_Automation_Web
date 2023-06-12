package Tests;

import Config.BaseClass;
import Config.Constants;
import DTO.UserDTO;
import Locators.HomePageLocators;
import Locators.LoginLocators;
import Locators.SettingsLocators;
import methods.ApiRequests;
import methods.General;
import methods.RegistrationPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class DeactivateTest extends BaseClass {
    @Test
    public void deactivateAccount() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        SettingsLocators settingsLocators = new SettingsLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.deactivate);
        String deactivationReason = general.generateDeleteReason();
        general.enterText(settingsLocators.deactivateReason, deactivationReason);
        general.clickElement(settingsLocators.reasonNext);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.passForDeactivate));
        general.enterText(settingsLocators.passForDeactivate, dto.getPassword());
        general.clickElement(settingsLocators.submitDeactivate);
        requests.retrieveVerificationEmail();
        String confirmationLink = requests.extractAccountDeletionLink(dto.getRegistrationMail());
        driver.get(confirmationLink);
        general.assertThatElementContains("", settingsLocators.deactivatedMessage);
        requests.retrieveThankYouEmail();
        Assert.assertEquals(dto.getRegistrationMail(), Constants.HOPE_TO_SEE_YOU_SOON);
        requests.retrieveThankYouEmailUsername();
        Assert.assertEquals(dto.getUsername(), Constants.DEAR + dto.getFullName());
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
    }

    @Test
    public void deactivateWithoutReason() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        SettingsLocators settingsLocators = new SettingsLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.deactivate);
        general.clickElement(settingsLocators.deactivateSkipBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.passForDeactivate));
        general.enterText(settingsLocators.passForDeactivate, dto.getPassword());
        general.clickElement(settingsLocators.submitDeactivate);
        requests.retrieveVerificationEmail();
        String confirmationLink = requests.extractAccountDeletionLink(dto.getRegistrationMail());
        driver.get(confirmationLink);
        general.assertThatElementContains("", settingsLocators.deactivatedMessage);
        requests.retrieveThankYouEmail();
        Assert.assertEquals(dto.getRegistrationMail(), Constants.HOPE_TO_SEE_YOU_SOON);
        requests.retrieveThankYouEmailUsername();
        Assert.assertEquals(dto.getUsername(), Constants.DEAR + dto.getFullName());
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
    }

    @Test
    public void cancelDeactivation() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        SettingsLocators settingsLocators = new SettingsLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.deactivate);
        String deactivationReason = general.generateDeleteReason();
        general.enterText(settingsLocators.deactivateReason, deactivationReason);
        general.clickElement(settingsLocators.reasonNext);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.passForDeactivate));
        general.enterText(settingsLocators.passForDeactivate, dto.getPassword());
        general.clickElement(settingsLocators.cancelBtn);
        general.urlDoesNotContainPath(driver.getCurrentUrl(), Constants.GET_STARTED);
        requests.deleteAccount(dto.getEmail(),dto.getPassword());

    }
}
