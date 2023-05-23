package Tests;

import DTO.UserDTO;
import Locators.*;
import methods.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class ForgetPasswordTest {

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
    public void ForgetPassword() throws InterruptedException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        TabControl tabControl = new TabControl(driver);
        SettingsLocators setLoc = new SettingsLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        MailLocators mailLocators = new MailLocators();
        RegistrationLocators regLoc = new RegistrationLocators();
        TempMailMethods tempMailMeth = new TempMailMethods(driver);

        general.registerWithValidData();
        general.clickElement(logLoc.Login);
        general.clickElement(logLoc.forget);
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        general.enterText(logLoc.emailField,dto.getEmail());
        general.clickElement(logLoc.sendEmail);
        currentTab++;
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab].toString());
        general.scroll(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.reset));
        general.clickElement(mailLocators.reset);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.resetPassword));
        general.clickElement(mailLocators.resetPassword);
        general.enterText(logLoc.passwordField,TestData.changedPass);
        general.enterText(regLoc.confirm,TestData.changedPass);
        general.clickElement(logLoc.changeButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.PassChangedMessage));
        general.assertThatElementContains("YEAH! Your password is changed successfully",logLoc.PassChangedMessage);
        general.clickElement(logLoc.formChangeLogin);
        general.enterText(logLoc.loginField,dto.getEmail());
        general.enterText(logLoc.passwordField,TestData.changedPass);
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.waitAndAssertThatEquals(dto.getFullName(), setLoc.name);
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.assertThatValueEquals(dto.getFullName(), setLoc.personalDetailsName);
        general.clickElement(setLoc.menuSection);
        general.assertThatValueEquals(dto.getEmail(), setLoc.mailInput);
    }

    @Test
    public void InvalidEmail(){
        LoginLocators logLoc= new LoginLocators();
        General general = new General(driver);

        driver.get("https://sisprogress.com/login");
        general.clickElement(logLoc.forget);
        general.enterText(logLoc.emailField,"xyz");
        general.clickElement(logLoc.sendEmail);
        String expectedValidationMessage = "Please include an '@' in the email address. 'xyz' is missing an '@'.";
        WebElement emailFieldAgain = driver.findElement(logLoc.emailField);
        String actualValidationMessage = emailFieldAgain.getAttribute("validationMessage");
        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println("Verified Validation Message");
        }
    }
    @Test
    public void PasswordsDoNotMatch() throws InterruptedException {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        TabControl tabControl = new TabControl(driver);
        MailLocators mailLocators = new MailLocators();
        RegistrationLocators regLoc = new RegistrationLocators();
        TempMailMethods tempMailMeth = new TempMailMethods(driver);

        general.registerWithValidData();
        general.clickElement(logLoc.Login);
        general.clickElement(logLoc.forget);
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        general.enterText(logLoc.emailField,dto.getEmail());
        general.clickElement(logLoc.sendEmail);
        currentTab++;
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab].toString());
        general.scroll(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.reset));
        general.clickElement(mailLocators.reset);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.resetPassword));
        general.clickElement(mailLocators.resetPassword);
        general.enterText(logLoc.passwordField,TestData.changedPass);
        general.enterText(regLoc.confirm,TestData.password);
        general.clickElement(logLoc.changeButton);
        general.assertThatElementContains("Password and Confirm Password does not match.",logLoc.PassErrorMessage);
    }




}