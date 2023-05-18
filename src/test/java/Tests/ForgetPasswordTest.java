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
        driver.findElement(logLoc.Login).click();
        driver.findElement(logLoc.forget).click();
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        driver.findElement(logLoc.emailField).clear();
        driver.findElement(logLoc.emailField).sendKeys(dto.getEmail());

        driver.findElement(logLoc.sendEmail).click();
        currentTab++;
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab].toString());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        long scrollTo = scrollHeight / 15;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        general.clickElement(mailLocators.indexSection);
        general.clickElement(mailLocators.resetPassword);
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(TestData.changedPass);
        driver.findElement(regLoc.confirm).clear();
        driver.findElement(regLoc.confirm).sendKeys(TestData.changedPass);
        driver.findElement(logLoc.changeButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.PassChangedMessage));
        general.assertThatElementContains("YEAH! Your password is changed successfully",logLoc.PassChangedMessage);
        general.clickElement(logLoc.formChangeLogin);
        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(TestData.changedPass);
        driver.findElement(logLoc.loginButton).click();
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
        driver.get("https://sisprogress.com/login");
        driver.findElement(logLoc.forget).click();
        driver.findElement(logLoc.emailField).clear();
        driver.findElement(logLoc.emailField).sendKeys("xyz");
        driver.findElement(logLoc.sendEmail).click();
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
        driver.findElement(logLoc.Login).click();
        driver.findElement(logLoc.forget).click();
        int currentTab = 1;
        tabControl.openNewTab();
        tabControl.switchTab();
        driver.get("https://internxt.com/temporary-email");
        tempMailMeth.getMail(mailLocators.uniqueMail);
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab - 1].toString());
        driver.findElement(logLoc.emailField).clear();
        driver.findElement(logLoc.emailField).sendKeys(dto.getEmail());

        driver.findElement(logLoc.sendEmail).click();
        currentTab++;
        driver.switchTo().window(driver.getWindowHandles().toArray()[currentTab].toString());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        long scrollTo = scrollHeight / 15;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        general.clickElement(mailLocators.indexSection);
        general.clickElement(mailLocators.resetPassword);
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(TestData.changedPass);
        driver.findElement(regLoc.confirm).clear();
        driver.findElement(regLoc.confirm).sendKeys(TestData.password);
        driver.findElement(logLoc.changeButton).click();
        general.assertThatElementContains("Password and Confirm Password does not match.",logLoc.PassErrorMessage);
    }




}