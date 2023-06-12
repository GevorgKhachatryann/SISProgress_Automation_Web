package Locators;

import org.openqa.selenium.By;

public class HomePageLocators {
    public By userName = By.className("NavBare_name__0BQfc");
    public By homeIcon = By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div[1]/a/div/img");
    public By bell = By.cssSelector("div.Bell_main__7ySzZ > img");
    public By messageTitle = By.className("Bell_titles__A+WXa");
    public By percent = By.className("CircularProgressbar-text");

}
