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
        general.enterText(loginLocators.loginField, dto.getEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        registrationPage.registrationSecondPartFor9Grade();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        requests.deleteAccount(dto.getEmail(), dto.getPassword());
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
        general.enterText(loginLocators.loginField, dto.getEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        registrationPage.registrationSecondPartFor10Grade();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        requests.deleteAccount(dto.getEmail(), dto.getPassword());
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
        general.clickRandomCheckbox(driver, calendarLocators.checkbox);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
        general.clickRandomCheckbox(driver, calendarLocators.checkbox);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
        general.clickRandomCheckbox(driver, calendarLocators.checkbox);
        WebElement outsideElement = driver.findElement(registrationLocators.header);
        actions.moveToElement(outsideElement).click().perform();
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        general.assertThatElementContains(Constants.INVALID_EMAIL_FORMAT, registrationLocators.mailError);

    }

    @Test
    public void registrationPasswordsDoNotMatch() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();

        driver.get(URL.Registration_URL);
        requests.generateRandomEmailForTest();
        general.enterText(registrationLocators.fullNameField, dto.getFullName());
        general.enterText(registrationLocators.emailForReg, dto.getEmail());
        general.enterText(registrationLocators.regPass, dto.getPassword());
        general.enterText(registrationLocators.confirmRegPass, dto.getInValidPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getGrade());
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.dreamUniDropdown));
        general.selectFromFancyDropdown(registrationLocators.dreamUniDropdown, registrationLocators.universityClass, dto.getUniversity());
        WebElement outsideElement = driver.findElement(registrationLocators.header);
        actions.moveToElement(outsideElement).click().perform();
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        general.assertThatElementContains(Constants.PASSWORD_AND_CONFIRM_DOES_NOT_MATCH, registrationLocators.passNotMatch);
    }

    @Test
    public void shortPasswordValidation() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();

        driver.get(URL.Registration_URL);
        requests.generateRandomEmailForTest();
        general.enterText(registrationLocators.fullNameField, dto.getFullName());
        general.enterText(registrationLocators.emailForReg, dto.getEmail());
        general.enterText(registrationLocators.regPass, Constants.WRONG_EMAIL);
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getGrade());
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.dreamUniDropdown));
        general.selectFromFancyDropdown(registrationLocators.dreamUniDropdown, registrationLocators.universityClass, dto.getUniversity());
        WebElement outsideElement = driver.findElement(registrationLocators.header);
        actions.moveToElement(outsideElement).click().perform();
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        String expectedValidationMessage = Constants.PASSWORD_LENGTH_MUST_BE_8;
        WebElement passField = driver.findElement(registrationLocators.regPass);
        String actualValidationMessage = passField.getAttribute(Constants.ATTRIBUTE);
        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println(Constants.VERIFIED_MESSAGE);
        }
    }

    @Test
    public void existingEmailValidationError() throws IOException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();

        requests.generateRandomEmailForTest();
        registrationPage.registration(dto.getEmail());
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        System.out.println(Constants.VERIFICATION_LINK + verificationLink);
        driver.get(verificationLink);
        driver.get(URL.Registration_URL);
        general.enterText(registrationLocators.fullNameField, dto.getFullName());
        general.enterText(registrationLocators.emailForReg, dto.getEmail());
        general.enterText(registrationLocators.regPass, dto.getPassword());
        System.out.println(dto.getPassword());
        general.enterText(registrationLocators.confirmRegPass, dto.getPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        registrationPage.enterDate(Constants.year, Constants.month, Constants.day);
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getGrade());
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.dreamUniDropdown));
        general.selectFromFancyDropdown(registrationLocators.dreamUniDropdown, registrationLocators.universityClass, dto.getUniversity());
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.emailExist));
        general.assertThatElementContains(Constants.EMAIL_IS_ALREADY_EXIST, registrationLocators.emailExist);
        requests.deleteAccount(dto.getEmail(), dto.getPassword());

    }
}


