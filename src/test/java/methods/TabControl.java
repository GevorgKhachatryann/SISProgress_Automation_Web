package methods;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

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
}
