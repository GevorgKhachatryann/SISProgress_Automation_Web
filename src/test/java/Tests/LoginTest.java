package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.ApiRequests;
import methods.General;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseClass {


    @Test
    public void loginWithValidCreds() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(settingsLocators.settingsIcon);
        general.waitAndAssertThatEquals(dto.getFullName(), settingsLocators.name);
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.assertThatValueEquals(dto.getFullName(), settingsLocators.personalDetailsName);
        general.clickElement(settingsLocators.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), settingsLocators.mailInput);

    }

    @Test
    public void logInWithInvalidEmailValidPassword() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getInvalidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
        general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);

    }

    @Test
    public void logInWithValidEmailInvalidPassword() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getInValidPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
        general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);

    }

    @Test
    public void logInWithEmptyEmail() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators loginLocators = new LoginLocators();

        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField, "");
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
        general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);
    }

    @Test
    public void logInWithEmptyPassword() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators loginLocators = new LoginLocators();

        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, "");
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
        general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);
    }
    @Test
    public void logInWithInvalidEmail() {
        General general = new General(driver);
        LoginLocators loginLocators = new LoginLocators();

        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, Constants.WRONG_EMAIL);
        general.clickElement(loginLocators.loginButton);
        String expectedValidationMessage = Constants.EMAIL_VALIDATION_MESSAGE;
        WebElement emailFieldAgain = driver.findElement(loginLocators.loginField);
        String actualValidationMessage = emailFieldAgain.getAttribute(Constants.ATTRIBUTE);

        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println(Constants.VERIFIED_MESSAGE);
        }

    }
}
