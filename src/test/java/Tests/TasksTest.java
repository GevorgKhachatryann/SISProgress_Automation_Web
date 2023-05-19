package Tests;

import DTO.UserDTO;
import Locators.CalendarLocators;
import Locators.HomePageLocators;
import Locators.LoginLocators;
import methods.ApiRequests;
import methods.General;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


public class TasksTest {

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
    public void addTaskFunctionality() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(calLoc.calendarIcon);
        general.clickElement(calLoc.AddTask);
        By checkboxLocator = calLoc.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calLoc.checkbox);
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

        general.clickElement(calLoc.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calLoc.firstTask));
        general.clickElement(calLoc.firstTask);
        general.assertThatElementContains(taskName, calLoc.TasksName);
        WebElement body = driver.findElement(By.tagName("body"));
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains("Planned", calLoc.TaskStatus);

    }

    @Test
    public void addTaskForTomorrow() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 20);
        general.clickElement(calLoc.calendarIcon);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("dd.MM"));

        String dateLocator = String.format("//p[contains(text(), '%s')]", formattedDate);
        WebElement dateElement = driver.findElement(By.xpath(dateLocator));

        WebElement addButton = dateElement.findElement(By.xpath("//div[contains(text(), '+')][@id='" + formattedDate + "_____1']"));
        addButton.click();

        By checkboxLocator = calLoc.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calLoc.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();

        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String point = "div:nth-child(" + randomIndex + ") > div > div > div.Cal_right__YUxVV > p";

        String taskName = driver.findElement(By.cssSelector(task)).getText();
        String points = driver.findElement(By.cssSelector(point)).getText();
        String number = points.replaceAll("[^\\d.]+", ""); //points for example 5 (5 points)


        general.clickElement(calLoc.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.firstTask));

        general.clickElement(calLoc.firstTask);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calLoc.TasksName));
        general.assertThatElementContains(taskName, calLoc.TasksName);
        WebElement body = driver.findElement(By.tagName("body"));
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();

        general.assertThatElementContains("Planned", calLoc.TaskStatus);

        String tomorrowDate = general.getFormattedTomorrowDate();

        general.assertThatElementContains("You can start this task from " + tomorrowDate, calLoc.taskFuture);


    }


    @Test
    public void deleteTask() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));
        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 20);
        general.clickElement(calLoc.calendarIcon);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("dd.MM"));
        String dateLocator = String.format("//p[contains(text(), '%s')]", formattedDate);
        WebElement dateElement = driver.findElement(By.xpath(dateLocator));

        WebElement addButton = dateElement.findElement(By.xpath("//div[contains(text(), '+')][@id='" + formattedDate + "_____1']"));
        addButton.click();
        By checkboxLocator = calLoc.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calLoc.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;

        checkboxes.get(randomIndex - 1).click();
        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";

        String taskName = driver.findElement(By.cssSelector(task)).getText();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.AddBtn));

        general.clickElement(calLoc.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.firstTask));

        general.clickElement(calLoc.firstTask);
        general.assertThatElementContains(taskName, calLoc.TasksName);
        WebElement body = driver.findElement(By.tagName("body"));
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains("Planned", calLoc.TaskStatus);
        String tomorrowDate = general.getFormattedTomorrowDate();
        general.assertThatElementContains("You can start this task from " + tomorrowDate, calLoc.taskFuture);

        general.clickElement(calLoc.deleteBtn);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(calLoc.firstTask));
        Assert.assertFalse(general.isElementDisplayed(calLoc.firstTask));

    }

    @Test
    public void checkInProgressStatus() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));

        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(calLoc.calendarIcon);
        general.clickElement(calLoc.AddTask);
        By checkboxLocator = calLoc.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calLoc.checkbox);
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
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.AddBtn));

        general.clickElement(calLoc.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.firstTask));

        general.clickElement(calLoc.firstTask);
        general.assertThatElementContains(taskName, calLoc.TasksName);

        List<WebElement> SubTaskCheckbox = driver.findElements(calLoc.subTaskCheckbox);
        int randomInd = new Random().nextInt(SubTaskCheckbox.size());

        SubTaskCheckbox.get(randomInd).click();
        general.clickElement(calLoc.submit);
        WebElement body = driver.findElement(By.tagName("body"));
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();

        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.TaskStatus));
        System.out.println(driver.findElement(calLoc.TaskStatus).getText());
        general.assertThatElementContains("In Progress", calLoc.TaskStatus);

    }

    @Test
    public void checkCompletedStatus() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();

        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));

        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(calLoc.calendarIcon);
        general.clickElement(calLoc.AddTask);
        By checkboxLocator = calLoc.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calLoc.checkbox);
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
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.AddBtn));

        general.clickElement(calLoc.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.firstTask));

        general.clickElement(calLoc.firstTask);
        general.assertThatElementContains(taskName, calLoc.TasksName);

        List<WebElement> SubTaskCheckbox = driver.findElements(calLoc.subTaskCheckbox);

        for (WebElement checkbox : SubTaskCheckbox) {
            checkbox.click();
        }

        general.clickElement(calLoc.submit);
        WebElement body = driver.findElement(By.tagName("body"));
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();

        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calLoc.TaskStatus));
        System.out.println(driver.findElement(calLoc.TaskStatus).getText());
        general.assertThatElementContains("Completed", calLoc.TaskStatus);

    }

    @Test
    public void taskAdditionOnPastDaysIsImpossible() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));

        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        general.clickElement(calLoc.calendarIcon);
        List<WebElement> dateElements = driver.findElements(By.className("Cal_nameWithDays__9S0U7"));
        String[] taskDays = {  "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday" };

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
                System.out.println("Can add a task for " + date + " on " + taskDay);
                String columnId =String.format("//div[text()='+'][@id='%s_____1']", date);
                WebElement columnElement = driver.findElement(By.xpath(columnId));
                columnElement.click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(calLoc.modal));
                boolean isModalDisplayed = driver.findElement(calLoc.modal).isDisplayed();

                if (isModalDisplayed) {
                    System.out.println("Modal is opened for " + date);
                    driver.findElement(calLoc.closeModal).click();
                }
            } else {
                System.out.println("Cannot add a task for " + date + " on " + taskDay);
            }

        }

    }

    @Test
    public void addTaskFromDashboard(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        LoginLocators logLoc = new LoginLocators();
        ApiRequests requests = new ApiRequests();
        CalendarLocators calLoc = new CalendarLocators();
        HomePageLocators homeLoc = new HomePageLocators();
        String endpoint = "https://sisprogress.online/register/ForTest";
        requests.postRequest(endpoint);

        driver.get("https://sisprogress.com/login");

        driver.findElement(logLoc.loginField).clear();
        driver.findElement(logLoc.loginField).sendKeys(dto.getValidEmail());
        driver.findElement(logLoc.passwordField).clear();
        driver.findElement(logLoc.passwordField).sendKeys(dto.getPassword());
        driver.findElement(logLoc.loginButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeLoc.userName));

        general.waitAndAssertUntilTextContains(homeLoc.userName, dto.getFullName(), 10);
        String columnId =String.format("[id='%s_____n']", general.getFormattedDate());
        WebElement columnElement = driver.findElement(By.cssSelector(columnId));
        columnElement.click();
        By checkboxLocator = calLoc.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        List<WebElement> checkboxes = driver.findElements(calLoc.checkbox);
        int randomIndex = new Random().nextInt(checkboxes.size()) + 1;
        checkboxes.get(randomIndex - 1).click();

        String task = "div:nth-child(" + randomIndex + ") div.Cal_left__L23rE > p";
        String taskName = driver.findElement(By.cssSelector(task)).getText();

        general.clickElement(calLoc.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calLoc.firstTask));
        general.clickElement(calLoc.firstTask);
        general.assertThatElementContains(taskName, calLoc.TasksName);
        WebElement body = driver.findElement(By.tagName("body"));
        Actions actions = new Actions(driver);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains("Planned", calLoc.TaskStatus);
    }


}
