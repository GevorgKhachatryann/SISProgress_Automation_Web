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

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class General {
    private final WebDriver driver;
    public static WebDriverWait wait;

    public General(WebDriver driver) {
        this.driver = driver;
    }




    public void selectFromFancyDropdown(By arrowSelector, String classSelector, String value){
        driver.findElement(arrowSelector).click();
        driver.findElement(By.xpath ("//*[contains(@class, \'"+classSelector+"\') and contains(text(), \'"+value+"\')]")).click();
    }
    public void clickElement(By selector){
        driver.findElement(selector).click();
    }
    public void enterText(By selector, String username) {
        driver.findElement(selector).clear();
        driver.findElement(selector).click();
        driver.findElement(selector).sendKeys(username);
    }
    public void scroll(int num){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        long scrollTo = scrollHeight / num;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);

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