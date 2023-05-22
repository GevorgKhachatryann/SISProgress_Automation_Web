package Tests;

import DTO.UserDTO;
import Locators.MailLocators;
import Locators.RegistrationLocators;
import methods.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RegistrationTest {
    private WebDriver driver;
    public static WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver_win32 (1)/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void registerWithValidData() throws InterruptedException {
        RegistrationLocators regLoc = new RegistrationLocators();
        MailLocators mailLocators = new MailLocators();
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        RegistrationPage regPage = new RegistrationPage(driver);
        TabControl tabControl = new TabControl(driver);
        TempMailMethods tempMailMeth = new TempMailMethods(driver);
        TestData testData = new TestData();

        driver.get("https://internxt.com/temporary-email");
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.uniqueMail));
        tempMailMeth.getMail(mailLocators.uniqueMail);

        System.out.println(dto.getEmail());

        tabControl.openNewTab();
        tabControl.switchTab();

        driver.get("https://sisprogress.com/register");

        general.enterText(regLoc.usernameField, dto.getFullName());
        general.enterText(regLoc.emailField, dto.getEmail());
        general.enterText(regLoc.passwordField, testData.password);
        general.enterText(regLoc.confirmPasswordField, testData.password);
        general.selectFromDropdown(regLoc.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(regLoc.mobileNumField, dto.getMobileNumber());
        regPage.enterDate("2000", "03", "20");
        regPage.selectCountry(dto.getCountry());
        regPage.selectGrade(dto.getGrade());
        general.clickElement(regLoc.nextButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(regLoc.uniDropDown));
        general.selectFromFancyDropdown(regLoc.uniDropDown, regLoc.uniClass, dto.getUniversity());
        general.selectFromFancyDropdown(regLoc.academicDropDown, regLoc.academicClass, dto.getAcademics());
        general.clickElement(regLoc.fall2024);
        general.clickElement(regLoc.early);
        general.clickElement(regLoc.yes);
        general.clickElement(regLoc.yess);
        general.clickElement(regLoc.nextButton2);
        general.enterText(regLoc.admTextbox, dto.getAdmText());
        general.clickElement(regLoc.privacyPolicy);
        general.clickElement(regLoc.submit);
        general.clickElement(regLoc.verifyButton);
        tabControl.switchTabToLeft();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        long scrollTo = scrollHeight / 15;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        WebElement element = driver.findElement(mailLocators.indexSection);
        element.click();

        scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        scrollTo = scrollHeight / 13;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);
        driver.findElement(mailLocators.verifyInMail).click();

        general.assertThatPageContains(regLoc.registrationVerifyAssert);
        scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        scrollTo = scrollHeight / 2;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);
    }
}