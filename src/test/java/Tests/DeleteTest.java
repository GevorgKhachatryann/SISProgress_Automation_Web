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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteTest extends BaseClass {

    @Test
    public void deleteAccount() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        SettingsLocators settingsLocators = new SettingsLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.deleteBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.deleteNext));
        general.isDisabled(driver, settingsLocators.deleteNext);
        String deleteReason = general.generateDeleteReason();
        general.enterText(settingsLocators.reasonField, deleteReason);
        general.clickElement(settingsLocators.enableNext);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.passForDelete));
        general.enterText(settingsLocators.passForDelete, dto.getPassword());
        general.clickElement(settingsLocators.submit);
        requests.retrieveVerificationEmail();
        String confirmationLink = requests.extractAccountDeletionLink(dto.getRegistrationMail());
        driver.get(confirmationLink);
        general.assertThatElementContains(Constants.ACCOUNT_HAS_BEEN_SUCCESSFULLY_DELETED, settingsLocators.accountDeletedMessage);
        requests.retrieveThankYouEmail();
        Assert.assertEquals(dto.getRegistrationMail(), Constants.FAREWELL_AND_A_SPECIAL_THANK_YOU);
        requests.retrieveThankYouEmailUsername();
        Assert.assertEquals(dto.getUsername(), Constants.DEAR + dto.getFullName());
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
    }

    @Test
    public void deleteAccountWithoutReason() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        SettingsLocators settingsLocators = new SettingsLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.deleteBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.deleteNext));
        general.isDisabled(driver, settingsLocators.deleteNext);
        general.clickElement(settingsLocators.skip);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.passForDelete));
        general.enterText(settingsLocators.passForDelete, dto.getPassword());
        general.clickElement(settingsLocators.submit);
        requests.retrieveVerificationEmail();
        String confirmationLink = requests.extractAccountDeletionLink(dto.getRegistrationMail());
        driver.get(confirmationLink);
        general.assertThatElementContains(Constants.ACCOUNT_HAS_BEEN_SUCCESSFULLY_DELETED, settingsLocators.accountDeletedMessage);
        requests.retrieveThankYouEmail();
        Assert.assertEquals(dto.getRegistrationMail(), Constants.FAREWELL_AND_A_SPECIAL_THANK_YOU);
        requests.retrieveThankYouEmailUsername();
        Assert.assertEquals(dto.getUsername(), Constants.DEAR + dto.getFullName());
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
    }

    @Test
    public void cancelDeletion() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        SettingsLocators settingsLocators = new SettingsLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.deleteBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.deleteNext));
        general.isDisabled(driver, settingsLocators.deleteNext);
        general.clickElement(settingsLocators.skip);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.passForDelete));
        general.enterText(settingsLocators.passForDelete, dto.getPassword());
        general.clickElement(settingsLocators.cancel);
        general.urlDoesNotContainPath(driver.getCurrentUrl(), Constants.GET_STARTED);
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
    }
}
