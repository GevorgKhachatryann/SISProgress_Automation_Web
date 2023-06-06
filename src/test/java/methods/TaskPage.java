package methods;

import Config.Constants;
import DTO.UserDTO;
import Locators.CalendarLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static Config.BaseClass.wait;

public class TaskPage {
    private WebDriver driver;
    public TaskPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTaskName(WebDriver driver, int randomIndex) {
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        return driver.findElement(By.cssSelector(task)).getText();
    }

    public String getPoints(WebDriver driver, int randomIndex) {
        String point = "div:nth-child(" + randomIndex + ") > div > div > div.Cal_right__YUxVV > p";
        String points = driver.findElement(By.cssSelector(point)).getText();
        return points.replaceAll("[^\\d.]+", "");
    }
    public void addTaskAndSelectCheckbox(WebDriver driver, WebDriverWait wait, By calendarLocators) {
        UserDTO dto = new UserDTO();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("dd.MM"));
        String dateLocator = String.format("//p[contains(text(), '%s')]", formattedDate);
        WebElement dateElement = driver.findElement(By.xpath(dateLocator));
        WebElement addButton = dateElement.findElement(By.xpath("//div[contains(text(), '+')][@id='" + formattedDate + "_____1']"));
        addButton.click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators));

        List<WebElement> checkboxes = driver.findElements(calendarLocators);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;
        checkboxes.get(randomIndex - 1).click();
        dto.setRandomIndex(randomIndex);

    }
    public void processDateElements(WebDriver driver, WebDriverWait wait) {
        CalendarLocators calendarLocators = new CalendarLocators();
        General general = new General(driver);
        List<WebElement> dateElements = driver.findElements(calendarLocators.days);
        String[] taskDays = {Constants.MONDAY, Constants.TUESDAY, Constants.WEDNESDAY, Constants.THURSDAY,
                Constants.FRIDAY, Constants.SATURDAY, Constants.SUNDAY};
        for (WebElement dateElement : dateElements) {
            String dateText = dateElement.getText();
            String[] parts = General.extractDateParts(dateText);
            String date = parts[1];

            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));

            LocalDate websiteDate = LocalDate.of(LocalDate.now().getYear(), month, day);
            DayOfWeek websiteDayOfWeek = websiteDate.getDayOfWeek();
            int websiteDayIndex = websiteDayOfWeek.getValue() - 1;

            String taskDay = taskDays[websiteDayIndex];

            if (websiteDate.isAfter(LocalDate.now()) || websiteDate.isEqual(LocalDate.now())) {
                System.out.println(Constants.CAN_ADD_A_TASK_FOR + date + Constants.ON + taskDay);
                String columnId = String.format("//div[text()='+'][@id='%s_____1']", date);
                WebElement columnElement = driver.findElement(By.xpath(columnId));
                columnElement.click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.modal));
                boolean isModalDisplayed = driver.findElement(calendarLocators.modal).isDisplayed();

                if (isModalDisplayed) {
                    System.out.println(Constants.MODAL_IS_OPENED_FOR + date);
                    general.clickElement(calendarLocators.closeModal);
                }
            } else {
                System.out.println(Constants.CANNOT_ADD_A_TASK_FOR + date + Constants.ON + taskDay);
            }
        }
    }
    public void clickAllSubTaskCheckboxes(WebDriver driver, By calendarLocators) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators));
        List<WebElement> subTaskCheckboxes = driver.findElements(calendarLocators);
        for (WebElement checkbox : subTaskCheckboxes) {
            checkbox.click();
        }
    }
    public void clickRandomSubTaskCheckbox(WebDriver driver, By subTaskCheckboxLocator) {
        List<WebElement> subTaskCheckboxes = driver.findElements(subTaskCheckboxLocator);
        int randomIndex = new Random().nextInt(subTaskCheckboxes.size());
        subTaskCheckboxes.get(randomIndex).click();
    }
}
