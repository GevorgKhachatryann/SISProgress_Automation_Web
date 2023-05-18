//package reg.com.login.Tests.Tests;
//
//import reg.DTO.UserDTO;
//import Locators.*;
//import methods.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.util.concurrent.TimeUnit;
//
//public class DeletingAccount {
//    public static WebDriver driver;
//    @BeforeClass
//    public static void beforeClass () {
//        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mariam\\Downloads\\chromedriver_win32\\chromedriver.exe");
//        driver = new ChromeDriver();
//    }
//
//    //    @AfterClass
////    public static void afterClass () {
////        driver.quit();
////    }
//    @Test
//    public void RegisterLogInDelete() {
//        RegistrationLocators regLoc = new RegistrationLocators();
//        MailLocators mailLocators = new MailLocators();
//        UserDTO dto = new UserDTO();
//        General general = new General(driver);
//        RegistrationPage regPage = new RegistrationPage(driver);
//        TabControl tabControl = new TabControl(driver);
//        LoginLocators logLoc= new LoginLocators();
//        HomePageLocators homeLoc = new HomePageLocators();
//        SettingsLocators setLoc = new SettingsLocators();
//        TempMailMethods tempMailMeth = new TempMailMethods(driver);
//
//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//        driver.get("https://internxt.com/temporary-email");
//        tempMailMeth.getMail(mailLocators.uniqueMail);
//        System.out.println(dto.getEmail());
//        tabControl.openNewTab();
//        tabControl.switchTab();
//        driver.get("https://sisprogress.com/register");
//        general.enterText(regLoc.usernameField, dto.getFullName());
//        general.enterText(regLoc.emailField, dto.getEmail());
//        general.enterText(regLoc.passwordField, dto.getPassword());
//        general.enterText(regLoc.confirmPasswordFiels, dto.getPassword());
//        general.selectFromDropdown(regLoc.countryNumDropdown, dto.getCountryNumValue());
//        general.enterText(regLoc.mobileNumField, dto.getMobileNumber());
//        regPage.enterDate("2000", "03", "20");
//        regPage.selectCountry(dto.getCountry());
//        regPage.selectGrade(dto.getGrade());
//        general.clickElement(regLoc.nextButton);
//        general.selectFromFancyDropdown(regLoc.uniDropDown, regLoc.uniClass, dto.getUniversity());
//        general.selectFromFancyDropdown(regLoc.academicDropDown, regLoc.academicClass, dto.getAcademics());
//        general.clickElement(regLoc.fall2024);
//        general.clickElement(regLoc.early);
//        general.clickElement(regLoc.yes);
//        general.clickElement(regLoc.yess);
//        general.clickElement(regLoc.nextButton2);
//        general.enterText(regLoc.admTextbox, dto.getAdmText());
//        general.clickElement(regLoc.privacyPolicy);
//        general.clickElement(regLoc.submit);
//        general.clickElement(regLoc.verifyButton);
//        tabControl.switchTabToLeft();
//        general.clickElement(mailLocators.indexSection);
//        general.clickElement(mailLocators.verifyInMail);
//        general.assertThatPageContains(regLoc.registrationVerifyAssert);
//        //Login
//        general.clickElement(regLoc.goToLogIN);
//        general.enterText(logLoc.loginField, dto.getValidEmail());
//        general.enterText(logLoc.passwordField, dto.getValidPassword());
//        general.clickElement(logLoc.loginButton);
//        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getValidUserName(), 10);
//        general.clickElement(setLoc.settingsIcon);
//        general.waitAndAssertThatEquals(dto.getValidUserName(), setLoc.name);
//        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getValidUserName(), 10);
//        general.assertThatValueEquals(dto.getValidUserName(), setLoc.personalDetailsName);
//        general.clickElement(setLoc.menuSection);
//        general.assertThatValueEquals(dto.getValidEmail(), setLoc.mailInput);
//    }
//}
