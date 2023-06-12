package methods;

import Config.Constants;
import DTO.UserDTO;
import Locators.CalendarLocators;
import Locators.HomePageLocators;
import Locators.LoginLocators;
import Locators.RegistrationLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;


public class RegistrationPage {

    private WebDriver driver;
    public static WebDriverWait wait;


    private By countryDropdown = By.className("RegisterOne_arrowBottom__86Gfn");
    private By countryDropdowns = By.className("Reg_arrowBottom__+AJP-");
    private By gradeDropdown = By.cssSelector("[placeholder=\"Grade level\"]");
    private By dateField = By.className("MuiSvgIcon-root");
    private By dateArrow = By.className("MuiPickersCalendarHeader-switchViewIcon");


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterDate(String year, String month, String day ){
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
        driver.findElement(dateField).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateArrow));
        driver.findElement(dateArrow).click();
        driver.findElement(By.xpath("//*[text()='" + year + "']")).click();
        driver.findElement(By.cssSelector(".MuiTypography-root:nth-child("+month+")")).click();
        driver.findElement(By.xpath("//*[text()='" + day + "']")).click();
    }
    public void selectCountry(String countryValue){
        driver.findElement(countryDropdowns).click();
        driver.findElement(By.xpath ("//*[contains(@class, 'Countries_countryName__XU42W') and contains(text(), \'"+countryValue+"\')]")).click();
    }

    public void selectGrade(String grade){
        driver.findElement(gradeDropdown).click();
        driver.findElement(By.xpath ("//*[contains(@class, 'WhichClass_countryName__+VCbE') and contains(text(), \'"+grade+"\')]")).click();
    }
    public void registrationForForget() throws IOException {
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
    }
        public void registration() throws IOException {
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
    }
    public void registration(String email){
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        driver.get("https://sisprogress.com/register");

        general.enterText(registrationLocators.fullNameField, dto.getFullName());
        general.enterText(registrationLocators.emailForReg, email);
        System.out.println(email);
        general.enterText(registrationLocators.regPass, dto.getPassword());
        System.out.println(dto.getPassword());
        general.enterText(registrationLocators.confirmRegPass, dto.getPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
//        registrationPage.enterDate("2000", "03", "20");
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
        WebElement outsideElement = driver.findElement(By.tagName("header"));
        actions.moveToElement(outsideElement).click().perform();
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.sendVerification));
        general.clickElement(registrationLocators.sendVerification);
    }
    public void registrationFor10Grade(String email){
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        driver.get("https://sisprogress.com/register");

        general.enterText(registrationLocators.fullNameField, dto.getFullName());
        general.enterText(registrationLocators.emailForReg, email);
        System.out.println(email);
        general.enterText(registrationLocators.regPass, dto.getPassword());
        System.out.println(dto.getPassword());
        general.enterText(registrationLocators.confirmRegPass, dto.getPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
//        registrationPage.enterDate("2000", "03", "20");
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getTenGrade());
        general.selectFromFancyDropdown(registrationLocators.highSchoolDropdown, registrationLocators.schools, dto.getSchool());

        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.dreamUniDropdown));
        general.selectFromFancyDropdown(registrationLocators.dreamUniDropdown, registrationLocators.universityClass, dto.getUniversity());
        general.clickElement(registrationLocators.academicProgram);
        general.clickRandomCheckbox(driver,calendarLocators.checkbox);
        general.clickRandomCheckbox(driver,calendarLocators.checkbox);
        general.clickRandomCheckbox(driver,calendarLocators.checkbox);
        WebElement outsideElement = driver.findElement(By.tagName("header"));
        actions.moveToElement(outsideElement).click().perform();
        general.clickElement(calendarLocators.checkbox);
        general.clickElement(registrationLocators.submitRegistration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.sendVerification));
        general.clickElement(registrationLocators.sendVerification);
    }
    public void registrationSecondPartFor9Grade(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.fall2024));
        general.clickElement(registrationLocators.fall2024);
        general.clickElement(registrationLocators.early);
        general.clickElement(registrationLocators.yes);
        general.clickElement(registrationLocators.yess);
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.admTextbox));
        general.enterText(registrationLocators.admTextbox, dto.getAdmText());
        general.clickElement(registrationLocators.submit);

    }
    public void registrationSecondPartFor10Grade(){
        General general = new General(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.fall2024));
        general.clickElement(registrationLocators.fall2024);
        general.clickElement(registrationLocators.early);
        general.clickElement(registrationLocators.yes);
        general.clickElement(registrationLocators.yess);
        general.clickElement(registrationLocators.fromUS);
        general.clickElement(registrationLocators.achievements);
        general.clickElement(registrationLocators.tests);
        general.clickElement(registrationLocators.activityDropdown);
        general.clickRandomCheckbox(driver,registrationLocators.check);
        general.clickRandomCheckbox(driver,registrationLocators.check);
        general.clickRandomCheckbox(driver,registrationLocators.check);
        general.clickElement(registrationLocators.submit);


    }
    }
