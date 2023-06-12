package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.CalendarLocators;
import Locators.HomePageLocators;
import Locators.LoginLocators;
import Locators.RegistrationLocators;
import methods.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;


public class RegistrationTest extends BaseClass {
    @Test
    public void registrationWith9thGrade() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();
        requests.generateRandomEmailForTest();
        registrationPage.registration(dto.getEmail());
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        System.out.println(Constants.VERIFICATION_LINK + verificationLink);
        driver.get(verificationLink);
        general.scroll(2);
        general.clickElement(registrationLocators.getStarted);
        general.enterText(loginLocators.loginField,dto.getEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        registrationPage.registrationSecondPartFor9Grade();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        requests.deleteAccount(dto.getEmail(),dto.getPassword());

    }
    @Test
    public void registrationWith10thGrade() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();
        requests.generateRandomEmailForTest();
        registrationPage.registrationFor10Grade(dto.getEmail());
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        System.out.println(Constants.VERIFICATION_LINK + verificationLink);
        driver.get(verificationLink);
        general.clickElement(registrationLocators.getStarted);
        general.enterText(loginLocators.loginField,dto.getEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        registrationPage.registrationSecondPartFor10Grade();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        requests.deleteAccount(dto.getEmail(),dto.getPassword());
    }
    @Test
    public void registerWithInvalidEmail() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        driver.get(URL.Registration_URL);
        general.enterText(registrationLocators.fullNameField, dto.getFullName());
        general.enterText(registrationLocators.emailForReg, Constants.WRONG_EMAIL);
        general.enterText(registrationLocators.regPass, dto.getPassword());
        general.enterText(registrationLocators.confirmRegPass, dto.getPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getGrade());
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.dreamUniDropdown));
        general.selectFromFancyDropdown(registrationLocators.dreamUniDropdown, registrationLocators.universityClass, dto.getUniversity());
        general.clickElement(registrationLocators.academicProgram);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
        general.clickRandomCheckbox(driver,calendarLocators.checkbox);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
        general.clickRandomCheckbox(driver,calendarLocators.checkbox);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
        general.clickRandomCheckbox(driver,calendarLocators.checkbox);
        WebElement outsideElement = driver.findElement(registrationLocators.header);
        actions.moveToElement(outsideElement).click().perform();
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        general.assertThatElementContains(Constants.INVALID_EMAIL_FORMAT,registrationLocators.mailError);

    }
}


