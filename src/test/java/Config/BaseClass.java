package Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.Duration;

public class BaseClass {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeMethod
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver_win32 (1)/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public static void afterClass() {
        driver.quit();
        String tempFolder = System.getProperty("java.io.tmpdir");
        deleteDirectories(tempFolder, "scoped_dir");
    }


    private static void deleteDirectories(String directoryPath, String prefix) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.getName().startsWith(prefix)) {
                        if (file.isDirectory()) {
                            deleteDirectories(file.getAbsolutePath(), prefix);
                        } else {
                            if (!file.delete()) {
                                throw new RuntimeException("Failed to delete file: " + file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }
}
