package methods;

import Config.BaseClass;
import Locators.CalendarLocators;
import Locators.ExploreLocators;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CalendarPage extends BaseClass {
    private final WebDriver driver;

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
    }
    public void performTaskSearch(String searchValue) {
        General general = new General(driver);
        CalendarLocators calendarLocators = new CalendarLocators();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.taskSearch));
        general.enterText(calendarLocators.taskSearch, searchValue);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(calendarLocators.taskSearch).sendKeys(Keys.ENTER);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getDropdownLength() {
        CalendarLocators calendarLocators = new CalendarLocators();

        WebElement dropdownElement = driver.findElement(calendarLocators.taskElement);
        List<WebElement> dropdownItems = dropdownElement.findElements(calendarLocators.tasks);

        return dropdownItems.size();
    }
}
