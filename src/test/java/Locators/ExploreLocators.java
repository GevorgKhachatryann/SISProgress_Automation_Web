package Locators;

import org.openqa.selenium.By;

public class ExploreLocators {
    public By exploreIcon = By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div[3]/a/div/img");
    public By exploreDropdown = By.className("FacultyBlock_top__vUqSY");
    public By exploreBox = By.cssSelector("[class=\"FacultyBlock_container__YHa2f\"]  button");
    public By recommendation = By.cssSelector("#root > div > main > div > div:nth-child(4) > p");
    public By activities = By.className("MyTaskSearch_pass__FvPGD");
    public By searchBox = By.className("MyTaskSearch_inp__nb+BO");
    public By searchItems = By.cssSelector(".MyTaskSearch_countryName__tp4Dg");
    public By dropdownElement = By.className("MyTaskSearch_container__y+bbF");
}
