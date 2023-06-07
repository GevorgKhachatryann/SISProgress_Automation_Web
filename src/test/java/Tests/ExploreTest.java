package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.ApiRequests;
import methods.ExplorePage;
import methods.General;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;


public class ExploreTest extends BaseClass {

    @Test
    public void addTaskFromExplore() {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        ExplorePage explorePage = new ExplorePage(driver);
        LoginLocators loginLocators = new LoginLocators();
        ExploreLocators exploreLocators = new ExploreLocators();
        CalendarLocators calendarLocators = new CalendarLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(exploreLocators.exploreIcon);
        driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(exploreLocators.recommendation));
        general.clickElement(exploreLocators.recommendation);
        wait.until(ExpectedConditions.elementToBeClickable(exploreLocators.exploreDropdown));
        String title = explorePage.performExploreDropdownSelection(driver, exploreLocators.exploreDropdown, exploreLocators.exploreBox);
        general.clickElement(calendarLocators.calendarIcon);
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.firstTask));
        general.clickElement(calendarLocators.firstTask);
        general.assertThatElementContains(title, calendarLocators.title);

    }

    @Test(dataProvider = "searchVariations",  dataProviderClass = UserDTO.class)
    public void searchBox(String searchValue, int expectedLength) {
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        ExplorePage explorePage = new ExplorePage(driver);
        LoginLocators loginLocators = new LoginLocators();
        ExploreLocators exploreLocators = new ExploreLocators();
        HomePageLocators homePageLocators = new HomePageLocators();

        requests.postRequest(Constants.REGISTRATION_ENDPOINT);
        driver.get(URL.Login_URL);
        general.enterText(loginLocators.loginField, dto.getValidEmail());
        general.enterText(loginLocators.passwordField, dto.getPassword());
        general.clickElement(loginLocators.loginButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageLocators.userName));
        general.waitAndAssertUntilTextContains(homePageLocators.userName, dto.getFullName(), 10);
        general.clickElement(exploreLocators.exploreIcon);
        explorePage.performSearch(searchValue);
        general.verifyDropdownLength(expectedLength);

    }

}
