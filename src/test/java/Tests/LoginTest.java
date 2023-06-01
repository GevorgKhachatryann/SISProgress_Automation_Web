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

    @Test(dataProvider = "userData", dataProviderClass = UserDTO.class)
    public void loginTest(String email, String password) {

        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        SettingsLocators settingsLocators = new SettingsLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, email);
        general.enterText(loginLocators.passwordField, password);
        general.clickElement(loginLocators.loginButton);


        if (email.equals(dto.getValidEmail()) && password.equals(dto.getPassword())) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
            general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
            general.clickElement(settingsLocators.settingsIcon);
            general.waitAndAssertThatEquals(dto.getFullName(), settingsLocators.name);
            general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
            general.assertThatValueEquals(dto.getFullName(), settingsLocators.personalDetailsName);
            general.clickElement(settingsLocators.menuSection);
            general.assertThatValueEquals(dto.getValidEmail(), settingsLocators.mailInput);

        } else if (email.equals(dto.getInvalidEmail())) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
            general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);

        } else if (password.equals(dto.getInValidPassword())) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
            general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);

        } else if (email.equals(Constants.WRONG_EMAIL)) {

            String expectedValidationMessage = Constants.EMAIL_VALIDATION_MESSAGE;
            WebElement emailFieldAgain = driver.findElement(loginLocators.loginField);
            String actualValidationMessage = emailFieldAgain.getAttribute(Constants.ATTRIBUTE);
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, Constants.VERIFIED_MESSAGE);

        } else if (email.isEmpty()) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
            general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);

        } else if (password.isEmpty()) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(loginLocators.errorMessage));
            general.assertThatElementContains(Constants.INVALID_EMAIL_OR_PASSWORD, loginLocators.errorMessage);
        }
    }

}
