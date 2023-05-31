package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.IOException;


public class ForgetPasswordTest extends BaseClass {

    @Test
    public void ForgetPassword() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();

        registrationPage.registration();
        general.clickElement(loginLocators.Login);
        general.clickElement(loginLocators.forget);
        general.enterText(loginLocators.emailField, dto.getEmail());
        general.clickElement(loginLocators.sendEmail);
        requests.retrieveForgetPasswordEmail();
        String verificationLink = requests.extractForgetPasswordLink(dto.getRegistrationMail());
        System.out.println(Constants.PASSWORD_CHANGE_LINK + verificationLink);
        driver.get(verificationLink);
        general.enterText(loginLocators.passwordField, dto.getChangedPass());
        general.enterText(registrationLocators.confirm, dto.getChangedPass());
        general.clickElement(loginLocators.changeButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.PassChangedMessage));
        general.assertThatElementContains(Constants.PASSWORD_CHANGED_MESSAGE, loginLocators.PassChangedMessage);
        general.clickElement(loginLocators.formChangeLogin);
        general.enterText(loginLocators.loginField, dto.getEmail());
        general.enterText(loginLocators.passwordField, dto.getChangedPass());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.waitAndAssertThatEquals(dto.getFullName(), settingsLocators.name);
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.assertThatValueEquals(dto.getFullName(), settingsLocators.personalDetailsName);
        general.clickElement(settingsLocators.menuSection);
        general.assertThatValueEquals(dto.getEmail(), settingsLocators.mailInput);
    }

    @Test
    public void InvalidEmail() {
        General general = new General(driver);
        LoginLocators loginLocators = new LoginLocators();

        driver.get(URL.Login_URL);
        general.clickElement(loginLocators.forget);
        general.enterText(loginLocators.emailField, Constants.WRONG_EMAIL);
        general.clickElement(loginLocators.sendEmail);
        String expectedValidationMessage = Constants.EMAIL_VALIDATION_MESSAGE;
        WebElement emailFieldAgain = driver.findElement(loginLocators.emailField);
        String actualValidationMessage = emailFieldAgain.getAttribute(Constants.ATTRIBUTE);
        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println(Constants.VERIFIED_MESSAGE);
        }
    }

    @Test
    public void PasswordsDoNotMatch() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registration();
        general.clickElement(loginLocators.Login);
        general.clickElement(loginLocators.forget);
        general.enterText(loginLocators.emailField, dto.getEmail());
        general.clickElement(loginLocators.sendEmail);
        requests.retrieveForgetPasswordEmail();
        String verificationLink = requests.extractForgetPasswordLink(dto.getRegistrationMail());
        System.out.println(Constants.PASSWORD_CHANGE_LINK + verificationLink);
        driver.get(verificationLink);
        general.enterText(loginLocators.passwordField, dto.getChangedPass());
        general.enterText(registrationLocators.confirm, dto.getPassword());
        general.clickElement(loginLocators.changeButton);
        general.assertThatElementContains(Constants.PASSWORD_AND_CONFIRM_DOES_NOT_MATCH, loginLocators.PassErrorMessage);
    }


}