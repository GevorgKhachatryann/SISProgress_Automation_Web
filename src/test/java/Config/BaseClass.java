package Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseClass {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeMethod
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver_win32 (1)/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(21));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public static void afterClass() {
        driver.quit();
    }
}
