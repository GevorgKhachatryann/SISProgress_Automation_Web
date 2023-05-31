package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;


public class NotificationsTest extends BaseClass {

    @Test
    public void getNotification() {
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

        general.performTask(driver, general, taskPage, wait, calendarLocators);
        general.clickElement(homePageLocators.homeIcon);
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.bell));
        general.click(driver, homePageLocators.bell);
        general.assertThatElementContains(Constants.CONGRATS_NOTIFICATION, homePageLocators.messageTitle);

    }
}
