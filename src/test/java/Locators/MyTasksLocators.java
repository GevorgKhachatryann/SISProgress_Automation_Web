package Locators;

import org.openqa.selenium.By;

public class MyTasksLocators {
    public By myTaskIcon = By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div[4]/a/div/img");
    public By taskName = By.xpath("//*[@id=\"root\"]/div/main/div/div[3]/div[3]/div[2]/p");
    public By status = By.cssSelector(".PositionCompany_stat__5JPF7");
}
