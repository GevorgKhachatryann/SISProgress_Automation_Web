package Tests;

import DTO.UserDTO;
import Locators.*;
import methods.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SettingsTest {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeMethod
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver_win32 (1)/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();

    }

    @AfterMethod
    public static void afterClass() {
        driver.quit();
    }

    @Test
    public void updatePersonalDetails(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        ApiRequests requests = new ApiRequests();
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        RegistrationPage regPage = new RegistrationPage(driver);
        RegistrationLocators regLoc = new RegistrationLocators();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.assertThatValueEquals(dto.getFullName(), setLoc.personalDetailsName);
        general.enterText(setLoc.personalDetailsName,dto.getUpdatedName());
        general.selectFromDropdown(regLoc.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(regLoc.mobileNumField, dto.getUpdatedMobileNumber());
        regPage.enterDate("2000", "03", "21");
        general.clickElement(setLoc.countryDrop);
        general.clickElement(setLoc.country);
        general.clickElement(setLoc.updateBtn);
        WebElement modalWindow = driver.findElement(setLoc.modal);
        Assert.assertTrue(modalWindow.isDisplayed(), "Modal window is not displayed.");
        String modalWindowText = modalWindow.getText();
        Assert.assertTrue(modalWindowText.contains("Personal data is updated successfully"), "Modal window does not contain the expected text.");
    }

    @Test
    public void checkDiscardBtnFunctionality() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        ApiRequests requests = new ApiRequests();
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        RegistrationLocators regLoc = new RegistrationLocators();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.assertThatValueEquals(dto.getFullName(), setLoc.personalDetailsName);
        general.enterText(setLoc.personalDetailsName,dto.getUpdatedName());
        general.selectFromDropdown(regLoc.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(regLoc.mobileNumField, dto.getUpdatedMobileNumber());
        general.clickElement(setLoc.discardBtn);
        general.assertThatValueEquals(dto.getFullName(), setLoc.personalDetailsName);
        general.assertThatValueEquals(dto.getMobileNumber(), regLoc.mobileNumField);

    }

    @Test
    public void updatePrimaryEmail(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        ApiRequests requests = new ApiRequests();
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        MailLocators mailLocators = new MailLocators();
        TempMailMethods tempMailMeth = new TempMailMethods(driver);
        TabControl tabControl = new TabControl(driver);

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);
        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.clickElement(setLoc.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), setLoc.mailInput);
        Assert.assertFalse(driver.findElement(setLoc.updateBtn).isEnabled());
        Assert.assertFalse(driver.findElement(setLoc.discardBtn).isEnabled());
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        general.enterText(setLoc.mailInput,dto.getEmail());
        general.clickElement(setLoc.updateBtn);
        general.clickElement(setLoc.sendVerifyBtn);
        tabControl.switchToNextTab();
        general.scroll(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        general.clickElement(mailLocators.indexSection);
        general.clickElement(mailLocators.verifyInMail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.emailChangedMessage));
        general.assertThatElementContains("YEAH! Your primary email has been updated successfully. Please use your new email address to log in to your account.",logLoc.emailChangedMessage);
        general.scroll(2);
        general.clickElement(logLoc.verifyPrimaryBtn);
        general.enterText(logLoc.loginField,dto.getEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
    }

    @Test
    public void updateWithInvalidEmail(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        ApiRequests requests = new ApiRequests();
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);
        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.clickElement(setLoc.menuSection);
        wait.until(ExpectedConditions.visibilityOfElementLocated(setLoc.emailBox));
        general.enterText(setLoc.emailBox,"xyz");
        general.clickElement(setLoc.updateBtn);
        String expectedValidationMessage = "Please include an '@' in the email address. 'xyz' is missing an '@'.";
        WebElement emailFieldAgain = driver.findElement(setLoc.emailBox);
        String actualValidationMessage = emailFieldAgain.getAttribute("validationMessage");

        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println("Verified Validation Message");
        }

    }

    @Test
    public void addSecondaryEmail(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        ApiRequests requests = new ApiRequests();
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        MailLocators mailLocators = new MailLocators();
        TempMailMethods tempMailMeth = new TempMailMethods(driver);
        TabControl tabControl = new TabControl(driver);

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);
        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.clickElement(setLoc.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), setLoc.mailInput);
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        general.clickElement(setLoc.addSecondary);
        general.enterText(setLoc.secondaryInput,dto.getEmail());
        general.clickElement(setLoc.secondaryUpdate);
        wait.until(ExpectedConditions.visibilityOfElementLocated(setLoc.sendSecondaryVerify));
        general.clickElement(setLoc.sendSecondaryVerify);
        tabControl.switchToNextTab();
        general.scroll(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        general.clickElement(mailLocators.indexSection);
        general.clickElement(mailLocators.verifyInMail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.emailChangedMessage));
        general.assertThatElementContains("YEAH! The secondary email has been added.",logLoc.emailChangedMessage);

    }

    @Test
    public void removeSecondaryEmail(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        ApiRequests requests = new ApiRequests();
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        MailLocators mailLocators = new MailLocators();
        TempMailMethods tempMailMeth = new TempMailMethods(driver);
        TabControl tabControl = new TabControl(driver);

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);
        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.clickElement(setLoc.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), setLoc.mailInput);
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        general.clickElement(setLoc.addSecondary);
        general.enterText(setLoc.secondaryInput,dto.getEmail());
        general.clickElement(setLoc.secondaryUpdate);
        wait.until(ExpectedConditions.visibilityOfElementLocated(setLoc.sendSecondaryVerify));
        general.clickElement(setLoc.sendSecondaryVerify);
        tabControl.switchToNextTab();
        general.scroll(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        general.clickElement(mailLocators.indexSection);
        general.clickElement(mailLocators.verifyInMail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.emailChangedMessage));
        general.assertThatElementContains("YEAH! The secondary email has been added.",logLoc.emailChangedMessage);
        general.clickElement(setLoc.backToSettings);
        general.clickElement(setLoc.menuSection);
        general.assertThatValueEquals(dto.getEmail(),setLoc.secondaryInput);
        general.clickElement(setLoc.removeSecondary);
        wait.until(ExpectedConditions.visibilityOfElementLocated(setLoc.addSecondary));
        WebElement element = driver.findElement(setLoc.addSecondary);
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains("+ Add Secondery email"));

    }
}
