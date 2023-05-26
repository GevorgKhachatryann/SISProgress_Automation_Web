package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SettingsTest extends BaseClass {

    @Test
    public void updatePersonalDetails(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);

        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.assertThatValueEquals(dto.getFullName(), settingsLocators.personalDetailsName);
        general.enterText(settingsLocators.personalDetailsName,dto.getUpdatedName());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getUpdatedMobileNumber());
//        registrationPage.enterDate("2000", "03", "21");
        general.clickElement(settingsLocators.countryDrop);
        general.clickElement(settingsLocators.country);
        general.clickElement(settingsLocators.updateBtn);
        WebElement modalWindow = driver.findElement(settingsLocators.modal);
        Assert.assertTrue(modalWindow.isDisplayed(), Constants.MODAL_IS_NOT_DISPLAYED);
        String modalWindowText = modalWindow.getText();
        Assert.assertTrue(modalWindowText.contains(Constants.PERSONAL_DATA_UPDATED), Constants.MODAL_DOES_NOT_CONTAIN_TEXT);
    }

    @Test
    public void checkDiscardBtnFunctionality() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.assertThatValueEquals(dto.getFullName(), settingsLocators.personalDetailsName);
        general.enterText(settingsLocators.personalDetailsName,dto.getUpdatedName());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getUpdatedMobileNumber());
        general.clickElement(settingsLocators.discardBtn);
        general.assertThatValueEquals(dto.getFullName(), settingsLocators.personalDetailsName);
        general.assertThatValueEquals(dto.getMobileNumber(), registrationLocators.mobileNumField);

    }

    @Test
    public void updatePrimaryEmail() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), settingsLocators.mailInput);
        Assert.assertFalse(driver.findElement(settingsLocators.updateBtn).isEnabled());
        Assert.assertFalse(driver.findElement(settingsLocators.discardBtn).isEnabled());
        requests.generateRandomEmailForTest();
        general.enterText(settingsLocators.mailInput,dto.getEmail());
        general.clickElement(settingsLocators.updateBtn);
        general.clickElement(settingsLocators.sendVerifyBtn);
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        driver.get(verificationLink);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.emailChangedMessage));
        general.assertThatElementContains(Constants.PRIMARY_EMAIL_UPDATED,loginLocators.emailChangedMessage);
        general.scroll(2);
        general.clickElement(loginLocators.verifyPrimaryBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.loginField));
        general.enterText(loginLocators.loginField,dto.getEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
    }

    @Test
    public void updateWithInvalidEmail(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.menuSection);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.emailBox));
        general.enterText(settingsLocators.emailBox,Constants.WRONG_EMAIL);
        general.clickElement(settingsLocators.updateBtn);
        String expectedValidationMessage = Constants.EMAIL_VALIDATION_MESSAGE;
        WebElement emailFieldAgain = driver.findElement(settingsLocators.emailBox);
        String actualValidationMessage = emailFieldAgain.getAttribute(Constants.ATTRIBUTE);

        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println(Constants.VERIFIED_MESSAGE);
        }

    }

    @Test
    public void addSecondaryEmail() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), settingsLocators.mailInput);
        requests.generateRandomEmailForTest();
        general.clickElement(settingsLocators.addSecondary);
        general.enterText(settingsLocators.secondaryInput,dto.getEmail());
        general.clickElement(settingsLocators.secondaryUpdate);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.sendSecondaryVerify));
        general.clickElement(settingsLocators.sendSecondaryVerify);
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        driver.get(verificationLink);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.emailChangedMessage));
        general.assertThatElementContains(Constants.SECONDARY_EMAIL_ADDED,loginLocators.emailChangedMessage);

    }

    @Test
    public void removeSecondaryEmail() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.clickElement(settingsLocators.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), settingsLocators.mailInput);
        requests.generateRandomEmailForTest();
        general.clickElement(settingsLocators.addSecondary);
        general.enterText(settingsLocators.secondaryInput,dto.getEmail());
        general.clickElement(settingsLocators.secondaryUpdate);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.sendSecondaryVerify));
        general.clickElement(settingsLocators.sendSecondaryVerify);
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        driver.get(verificationLink);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.emailChangedMessage));
        general.assertThatElementContains(Constants.SECONDARY_EMAIL_ADDED,loginLocators.emailChangedMessage);
        general.clickElement(settingsLocators.backToSettings);
        general.clickElement(settingsLocators.menuSection);
        general.assertThatValueEquals(dto.getEmail(),settingsLocators.secondaryInput);
        general.clickElement(settingsLocators.removeSecondary);
        wait.until(ExpectedConditions.visibilityOfElementLocated(settingsLocators.addSecondary));
        WebElement element = driver.findElement(settingsLocators.addSecondary);
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains(Constants.ADD_SECONDARY_EMAIL));

    }
}
