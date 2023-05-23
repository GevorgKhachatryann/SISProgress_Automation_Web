package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.ApiRequests;
import methods.General;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


public class TasksTest extends BaseClass {

    @Test
    public void addTaskFunctionality() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calendarLocators.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String point = "div:nth-child(" + randomIndex + ") > div > div > div.Cal_right__YUxVV > p";
        String taskName = driver.findElement(By.cssSelector(task)).getText();
        String points = driver.findElement(By.cssSelector(point)).getText();
        String number = points.replaceAll("[^\\d.]+", "");

        System.out.println(number);
        System.out.println(taskName);
        System.out.println(points);

        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);

    }

    @Test
    public void addTaskForTomorrow() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 20);
        general.clickElement(calendarLocators.calendarIcon);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("dd.MM"));
        String dateLocator = String.format("//p[contains(text(), '%s')]", formattedDate);
        WebElement dateElement = driver.findElement(By.xpath(dateLocator));
        WebElement addButton = dateElement.findElement(By.xpath("//div[contains(text(), '+')][@id='" + formattedDate + "_____1']"));
        addButton.click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.checkbox));

        List<WebElement> checkboxes = driver.findElements(calendarLocators.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String point = "div:nth-child(" + randomIndex + ") > div > div > div.Cal_right__YUxVV > p";
        String taskName = driver.findElement(By.cssSelector(task)).getText();
        String points = driver.findElement(By.cssSelector(point)).getText();
        String number = points.replaceAll("[^\\d.]+", ""); //points for example 5 (5 points)

        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.TasksName));
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
        String tomorrowDate = general.getFormattedTomorrowDate();
        general.assertThatElementContains(Constants.YOU_CAN_START_FROM + tomorrowDate, calendarLocators.taskFuture);


    }


    @Test
    public void deleteTask() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();
        Actions actions = new Actions(driver);

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 20);
        general.clickElement(calendarLocators.calendarIcon);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("dd.MM"));
        String dateLocator = String.format("//p[contains(text(), '%s')]", formattedDate);
        WebElement dateElement = driver.findElement(By.xpath(dateLocator));

        WebElement addButton = dateElement.findElement(By.xpath("//div[contains(text(), '+')][@id='" + formattedDate + "_____1']"));
        addButton.click();
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calendarLocators.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";

        String taskName = driver.findElement(By.cssSelector(task)).getText();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.AddBtn));
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
        String tomorrowDate = general.getFormattedTomorrowDate();
        general.assertThatElementContains(Constants.YOU_CAN_START_FROM + tomorrowDate, calendarLocators.taskFuture);
        general.clickElement(calendarLocators.deleteBtn);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(calendarLocators.firstTask));
        Assert.assertFalse(general.isElementDisplayed(calendarLocators.firstTask));

    }

    @Test
    public void checkInProgressStatus() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calendarLocators.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String point = "div:nth-child(" + randomIndex + ") > div > div > div.Cal_right__YUxVV > p";

        String taskName = driver.findElement(By.cssSelector(task)).getText();
        String points = driver.findElement(By.cssSelector(point)).getText();
        String number = points.replaceAll("[^\\d.]+", "");
        System.out.println(number);
        System.out.println(taskName);
        System.out.println(points);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.AddBtn));
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);

        List<WebElement> SubTaskCheckbox = driver.findElements(calendarLocators.subTaskCheckbox);
        int randomInd = new Random().nextInt(SubTaskCheckbox.size());

        SubTaskCheckbox.get(randomInd).click();
        general.clickElement(calendarLocators.submit);
        WebElement body = driver.findElement(calendarLocators.body);
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.TaskStatus));
        System.out.println(driver.findElement(calendarLocators.TaskStatus).getText());
        general.assertThatElementContains(Constants.IN_PROGRESS_STATUS, calendarLocators.TaskStatus);

    }

    @Test
    public void checkCompletedStatus() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));

        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calendarLocators.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String point = "div:nth-child(" + randomIndex + ") > div > div > div.Cal_right__YUxVV > p";

        String taskName = driver.findElement(By.cssSelector(task)).getText();
        String points = driver.findElement(By.cssSelector(point)).getText();

        String number = points.replaceAll("[^\\d.]+", "");

        System.out.println(number);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.AddBtn));

        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));

        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);

        List<WebElement> SubTaskCheckbox = driver.findElements(calendarLocators.subTaskCheckbox);

        for (WebElement checkbox : SubTaskCheckbox) {
            checkbox.click();
        }

        general.clickElement(calendarLocators.submit);
        WebElement body = driver.findElement(calendarLocators.body);
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();

        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.TaskStatus));
        System.out.println(driver.findElement(calendarLocators.TaskStatus).getText());
        general.assertThatElementContains(Constants.COMPLETED_STATUS, calendarLocators.TaskStatus);

    }

    @Test
    public void taskAdditionOnPastDaysIsImpossible() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));

        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        List<WebElement> dateElements = driver.findElements(calendarLocators.days);
        String[] taskDays = {   Constants.MONDAY, Constants.TUESDAY, Constants.WEDNESDAY, Constants.THURSDAY,
                                Constants.FRIDAY, Constants.SATURDAY, Constants.SUNDAY };
        for (WebElement dateElement : dateElements) {
            String dateText = dateElement.getText();
            String[] parts = General.extractDateParts(dateText);
            // String dayText = parts[0];
            String date = parts[1];

            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));

            LocalDate websiteDate = LocalDate.of(LocalDate.now().getYear(), month, day);
            DayOfWeek websiteDayOfWeek = websiteDate.getDayOfWeek();
            int websiteDayIndex = websiteDayOfWeek.getValue() - 1;

            String taskDay = taskDays[websiteDayIndex];

            if (websiteDate.isAfter(LocalDate.now()) || websiteDate.isEqual(LocalDate.now())) {
                System.out.println(Constants.CAN_ADD_A_TASK_FOR + date + Constants.ON + taskDay);
                String columnId =String.format("//div[text()='+'][@id='%s_____1']", date);
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

    @Test
    public void addTaskFromDashboard(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests();
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField,dto.getValidEmail());
        general.enterText(loginLocators.passwordField,dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));

        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        String columnId = String.format("[id='%s_____n']", general.getFormattedDate());
        WebElement columnElement = driver.findElement(By.cssSelector(columnId));
        columnElement.click();
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calendarLocators.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;
        checkboxes.get(randomIndex - 1).click();

        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String taskName = driver.findElement(By.cssSelector(task)).getText();

        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
    }


}
