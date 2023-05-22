package methods;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Set;

public class TabControl {
    private WebDriver driver;
    public TabControl(WebDriver driver) {
        this.driver = driver;
    }


    public void openNewTab(){
        ((JavascriptExecutor) driver).executeScript("window.open();");
    }
    public void switchTab(){
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));
    }

    public void switchTabToLeft(){
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-2));
    }
    public void switchToNextTab() {
        // Get the current window handle
        String currentWindowHandle = driver.getWindowHandle();

        // Get all the window handles
        Set<String> windowHandles = driver.getWindowHandles();

        // Find the index of the current window handle
        int currentIndex = 0;
        int totalWindows = windowHandles.size();
        int i = 0;
        for (String windowHandle : windowHandles) {
            if (windowHandle.equals(currentWindowHandle)) {
                currentIndex = i;
                break;
            }
            i++;
        }

        // Calculate the index of the next tab
        int nextIndex = (currentIndex + 1) % totalWindows;

        // Switch to the next tab
        String nextWindowHandle = (String) windowHandles.toArray()[nextIndex];
        driver.switchTo().window(nextWindowHandle);
    }

}
