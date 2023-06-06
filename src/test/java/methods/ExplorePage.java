package methods;

import Config.BaseClass;
import Locators.ExploreLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ExplorePage extends BaseClass {
    private final WebDriver driver;

    public ExplorePage(WebDriver driver) {
        this.driver = driver;
    }

    public String performExploreDropdownSelection(WebDriver driver, By dropdownSelector, By boxSelector) {
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownSelector));
        List<WebElement> elements = driver.findElements(dropdownSelector);
        System.out.println(elements.size());
        System.out.println(elements);

        Random random = new Random();
        int randomIndex = random.nextInt(elements.size());
        WebElement randomElement = elements.get(randomIndex);
        randomElement.click();
        System.out.println(randomIndex);

        List<WebElement> containerElements = driver.findElements(boxSelector);
        int randomContainerIndex = random.nextInt(containerElements.size());
        WebElement randomContainerElement = containerElements.get(randomContainerIndex);
        System.out.println(randomContainerIndex);
        randomContainerElement.click();

        String boxTitle = driver.findElement(By.cssSelector("[class=\"FacultyBlock_container__YHa2f\"]:nth-child("+(randomContainerIndex+1)+")>div>[class=\"FacultyBlock_title__+8qyI\"]")).getText();
        System.out.println(boxTitle);

        return boxTitle;
    }
    public void performSearch(String searchValue) {
        General general = new General(driver);
        ExploreLocators exploreLocators = new ExploreLocators();

        general.clickElement(exploreLocators.activities);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(exploreLocators.searchBox));
        general.enterText(exploreLocators.searchBox, searchValue);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getDropdownLength() {
        ExploreLocators exploreLocators = new ExploreLocators();

        WebElement dropdownElement = driver.findElement(exploreLocators.dropdownElement);
        List<WebElement> dropdownItems = dropdownElement.findElements(exploreLocators.searchItems);

        return dropdownItems.size();
    }


}
