package Tests;

import DTO.UserDTO;
import Locators.HomePageLocators;
import Locators.LoginLocators;
import Locators.SettingsLocators;
import methods.ApiRequests;
import methods.General;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import static methods.TestData.password;

public class LoginTest {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeMethod
    public static void beforeClass () {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver_win32 (1)/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public static void afterClass () {
        driver.quit();
    }


    @Test
    public void loginWithValidCreds(){
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        UserDTO dto = new UserDTO();
        HomePageLocators homeLoc = new HomePageLocators();
        SettingsLocators setLoc = new SettingsLocators();
        ApiRequests requests = new ApiRequests();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(setLoc.settingsIcon);
        general.waitAndAssertThatEquals(dto.getFullName(), setLoc.name);
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.assertThatValueEquals(dto.getFullName(), setLoc.personalDetailsName);
        general.clickElement(setLoc.menuSection);
        general.assertThatValueEquals(dto.getValidEmail(), setLoc.mailInput);

    }

    @Test
    public void logInWithInvalidEmailValidPassword(){

        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        UserDTO dto = new UserDTO();
        ApiRequests requests = new ApiRequests();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);
        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getInvalidEmail());
        general.enterText(logLoc.passwordField,dto.getPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.errorMessage));
        general.assertThatElementContains("Invalid email or password",logLoc.errorMessage);

    }

    @Test
    public void logInWithValidEmailInvalidPassword(){

        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        UserDTO dto = new UserDTO();
        ApiRequests requests = new ApiRequests();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);
        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,dto.getInValidPassword());
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.errorMessage));
        general.assertThatElementContains("Invalid email or password",logLoc.errorMessage);

    }
    @Test
    public void logInWithEmptyEmail(){

        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();

        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,"");
        general.enterText(logLoc.passwordField,password);
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.errorMessage));
        general.assertThatElementContains("Invalid email or password",logLoc.errorMessage);
    }

    @Test
    public void logInWithEmptyPassword(){
        General general = new General(driver);
        LoginLocators logLoc= new LoginLocators();
        UserDTO dto = new UserDTO();

        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,dto.getValidEmail());
        general.enterText(logLoc.passwordField,"");
        general.clickElement(logLoc.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(logLoc.errorMessage));
        general.assertThatElementContains("Invalid email or password",logLoc.errorMessage);
    }

    @Test
    public void logInWithInvalidEmail(){
        LoginLocators logLoc= new LoginLocators();
        General general = new General(driver);

        driver.get("https://sisprogress.com/login");
        general.enterText(logLoc.loginField,"xyz");
        general.clickElement(logLoc.loginButton);
        String expectedValidationMessage = "Please include an '@' in the email address. 'xyz' is missing an '@'.";
        WebElement emailFieldAgain = driver.findElement(logLoc.loginField);
        String actualValidationMessage = emailFieldAgain.getAttribute("validationMessage");

        if (actualValidationMessage.equals(expectedValidationMessage)) {
            System.out.println("Verified Validation Message");
        }

    }
}
