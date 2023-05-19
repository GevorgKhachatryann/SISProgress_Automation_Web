package methods;

import DTO.UserDTO;
import Locators.MailLocators;
import Locators.RegistrationLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static methods.TestData.mail;
import static methods.TestData.password;

public class General {
    private final WebDriver driver;
    public static WebDriverWait wait;

    public General(WebDriver driver) {
        this.driver = driver;
    }


    private String password = "Test1234*";

    public void selectFromFancyDropdown(By arrowSelector, String classSelector, String value){
        driver.findElement(arrowSelector).click();
        driver.findElement(By.xpath ("//*[contains(@class, \'"+classSelector+"\') and contains(text(), \'"+value+"\')]")).click();
    }
    public void clickElement(By selector){
        driver.findElement(selector).click();
    }
    public void enterText(By selector, String username) {
        driver.findElement(selector).sendKeys(username);
    }

    public void selectFromDropdown(By selector, String countryValue){
        Select dropdown = new Select(driver.findElement(selector));
        dropdown.selectByValue(countryValue);
    }
    public void explicitWait(By locator, int waitingDuration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingDuration));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void assertThatPageContains(ArrayList<String> expected){
        for (int a = 0; a < expected.size(); a++) {
            Assert.assertTrue(driver.getPageSource().contains(expected.get(a)));
        }
    }

    public void waitAndAssertThatEquals(String text, By locatorToCompare){
        WebElement element = driver.findElement(locatorToCompare);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        String compareWith = driver.findElement(locatorToCompare).getText();
        Assert.assertEquals(text, compareWith);
    }
    public void assertThatValueEquals(String text, By locatorToCompare){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(locatorToCompare, "value", text));
        String compareWith = driver.findElement(locatorToCompare).getAttribute("value");
        Assert.assertEquals(text, compareWith);
    }

    public void assertThatElementContains(String text, By locatorToCompare){
        String compareWith = driver.findElement(locatorToCompare).getText();
        Assert.assertTrue(compareWith.contains(text));
    }
    public void waitAndAssertUntilTextContains(By locator, String text, int waitingDuration){
        WebElement element = driver.findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingDuration));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        Assert.assertTrue(element.getText().contains(text));
    }
    public void scrollPageToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void registerWithValidData() throws InterruptedException {
        RegistrationLocators regLoc = new RegistrationLocators();
        MailLocators mailLocators = new MailLocators();
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        RegistrationPage regPage = new RegistrationPage(driver);
        TabControl tabControl = new TabControl(driver);
        TempMailMethods tempMailMeth = new TempMailMethods(driver);
        TestData testData = new TestData();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://internxt.com/temporary-email");
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
        Thread.sleep(200);
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
    public String getFormattedDate() {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

        String formattedDate = currentDate.format(formatter);
        return formattedDate;

    }
    public String getFormattedTomorrowDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d");
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        String formattedMonthAndDate = tomorrowDate.format(dateFormatter);

        int dayOfMonth = tomorrowDate.getDayOfMonth();
        String suffix;
        if (dayOfMonth >= 11 && dayOfMonth <= 13) {
            suffix = "th";
        } else {
            int lastDigit = dayOfMonth % 10;
            switch (lastDigit) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        formattedMonthAndDate += suffix;
        return formattedMonthAndDate;
    }

    public static String[] extractDateParts(String dateText) {

        String[] parts = dateText.split("\\s+");
        String dayText = parts[0];
        String date = parts[1];

        return new String[]{dayText, date};
    }
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}