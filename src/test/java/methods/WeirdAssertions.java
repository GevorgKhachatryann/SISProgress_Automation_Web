package methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class WeirdAssertions {
    private WebDriver driver;
    public WeirdAssertions(WebDriver driver){
        this.driver = driver;
    }
    public void waitAndAssertUntilValueContains(By locator, String text  , int waitingDuration){
        WebElement element = driver.findElement(locator);
        System.out.println(element.getAttribute("value"));
        Assert.assertEquals(text, element.getAttribute("value"));
    }
}
