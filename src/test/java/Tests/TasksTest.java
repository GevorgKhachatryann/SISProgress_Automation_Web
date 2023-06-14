package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.ApiRequests;
import methods.General;
import methods.TaskPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;



public class TasksTest extends BaseClass {

    @Test
    public void addTaskFunctionality() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));

        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        String points = taskPage.getPoints(driver, randomIndex);
        String number = points.replaceAll("[^\\d.]+", "");
        System.out.println(number);
        System.out.println(taskName);
        System.out.println(points);

        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);

    }

    @Test
    public void addTaskForTomorrow() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 20);
        general.clickElement(calendarLocators.calendarIcon);
        taskPage.addTaskAndSelectCheckbox(driver, wait, calendarLocators.checkbox);
        String taskName = taskPage.getTaskName(driver, dto.getRandomIndex());
        System.out.println(taskName);
        String points = taskPage.getPoints(driver, dto.getRandomIndex());
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.TasksName));
        System.out.println(driver.findElement(calendarLocators.TasksName).getText());
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
        String tomorrowDate = general.getFormattedTomorrowDate();
        general.assertThatElementContains(Constants.YOU_CAN_START_FROM + tomorrowDate, calendarLocators.taskFuture);


    }


    @Test
    public void deleteTask() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();
        Actions actions = new Actions(driver);

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 20);
        general.clickElement(calendarLocators.calendarIcon);
        taskPage.addTaskAndSelectCheckbox(driver, wait, calendarLocators.checkbox);
        String taskName = taskPage.getTaskName(driver, dto.getRandomIndex());
        System.out.println(taskName);
        String points = taskPage.getPoints(driver, dto.getRandomIndex());
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.AddBtn));
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        System.out.println(driver.findElement(calendarLocators.TasksName).getText());
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
        String tomorrowDate = general.getFormattedTomorrowDate();
        general.assertThatElementContains(Constants.YOU_CAN_START_FROM + tomorrowDate, calendarLocators.taskFuture);
        general.clickElement(calendarLocators.deleteBtn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(calendarLocators.firstTask));
        Assert.assertFalse(general.isElementDisplayed(calendarLocators.firstTask));

    }

    @Test
    public void checkInProgressStatus() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));
        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        String points = taskPage.getPoints(driver, randomIndex);
        String number = points.replaceAll("[^\\d.]+", "");
        System.out.println(number);
        System.out.println(taskName);
        System.out.println(points);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.AddBtn));
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.TasksName));
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        taskPage.clickRandomSubTaskCheckbox(driver, calendarLocators.subTaskCheckbox);
        general.clickElement(calendarLocators.submit);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.TaskStatus));
        System.out.println(driver.findElement(calendarLocators.TaskStatus).getText());
        general.assertThatElementContains(Constants.IN_PROGRESS_STATUS, calendarLocators.TaskStatus);
        System.out.println(Constants.IN_PROGRESS_STATUS);

    }

    @Test
    public void checkCompletedStatus() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));
        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        String points = taskPage.getPoints(driver, randomIndex);
        String number = points.replaceAll("[^\\d.]+", "");
        System.out.println(number);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.AddBtn));
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);

        taskPage.clickAllSubTaskCheckboxes(driver, calendarLocators.subTaskCheckbox);

        general.clickElement(calendarLocators.submit);
        WebElement body = driver.findElement(calendarLocators.body);
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
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        taskPage.processDateElements(driver, wait);

    }

    @Test
    public void addTaskFromDashboard() {
        UserDTO dto = new UserDTO();
        Actions actions = new Actions(driver);
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);

        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));

        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        String columnId = String.format("[id='%s_____n']", general.getFormattedDate());
        WebElement columnElement = driver.findElement(By.cssSelector(columnId));
        columnElement.click();
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));
        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
    }

    @Test
    public void checkMyTasksSection() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        Actions actions = new Actions(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        MyTasksLocators myTasksLocators = new MyTasksLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));
        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.TaskStatus));
        general.assertThatElementContains(Constants.PLANNED_STATUS, calendarLocators.TaskStatus);
        general.clickElement(myTasksLocators.myTaskIcon);
        wait.until(ExpectedConditions.visibilityOfElementLocated(myTasksLocators.taskName));
        general.assertThatElementContains(taskName,myTasksLocators.taskName);
        general.assertThatElementContains(Constants.PLANNED_STATUS,myTasksLocators.status);
        general.clickElement(calendarLocators.calendarIcon);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        taskPage.clickAllSubTaskCheckboxes(driver, calendarLocators.subTaskCheckbox);
        general.clickElement(calendarLocators.submit);
        WebElement body = driver.findElement(calendarLocators.body);
        actions.moveToElement(body).click().perform();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(calendarLocators.TaskStatus));
        general.assertThatElementContains(Constants.COMPLETED_STATUS, calendarLocators.TaskStatus);
        general.clickElement(myTasksLocators.myTaskIcon);
        wait.until(ExpectedConditions.visibilityOfElementLocated(myTasksLocators.taskName));
        general.assertThatElementContains(taskName,myTasksLocators.taskName);
        general.assertThatElementContains(Constants.COMPLETED_STATUS,myTasksLocators.status);
    }
    @Test
    public void checkFeedbackFunctionality(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));
        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        general.clickElement(calendarLocators.leaveFeedback);
        general.enterText(calendarLocators.textField,dto.getFeedback());
        general.clickElement(calendarLocators.modalSubmit);
        general.clickElement(calendarLocators.leaveFeedback);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.history));
        general.clickElement(calendarLocators.history);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.historyText));
        general.assertThatElementContains(dto.getFeedback(),calendarLocators.historyText);
    }
    @Test
    public void feedbackSubmitButtonDisabledByDefault(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        TaskPage taskPage = new TaskPage(driver);
        ApiRequests requests = new ApiRequests(driver);
        LoginLocators loginLocators = new LoginLocators();
        HomePageLocators homePageLocators = new HomePageLocators();
        CalendarLocators calendarLocators = new CalendarLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(calendarLocators.calendarIcon);
        general.clickElement(calendarLocators.AddTask);
        By checkboxLocator = calendarLocators.checkbox;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLocator));
        int randomIndex = general.clickRandomCheckbox(driver, checkboxLocator);
        String taskName = taskPage.getTaskName(driver, randomIndex);
        general.clickElement(calendarLocators.AddBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(taskName, calendarLocators.TasksName);
        general.clickElement(calendarLocators.leaveFeedback);
        general.isDisabled(driver,calendarLocators.modalSubmit);
    }
}
