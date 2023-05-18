package methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


public class RegistrationPage {

    private WebDriver driver;
    private By countryDropdown = By.className("RegisterOne_arrowBottom__86Gfn");
    private By gradeDropdown = By.cssSelector("[placeholder=\"Grade level\"]");
    private By dateField = By.className("MuiSvgIcon-root");
    private By dateArrow = By.className("MuiPickersCalendarHeader-switchViewIcon");


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterDate(String year, String month, String day ){
        driver.findElement(dateField).click();
        driver.findElement(dateArrow).click();
        driver.findElement(By.xpath("//*[text()='" + year + "']")).click();
        driver.findElement(By.cssSelector(".MuiTypography-root:nth-child("+month+")")).click();
        driver.findElement(By.xpath("//*[text()='" + day + "']")).click();
    }
    public void selectCountry(String countryValue){
        driver.findElement(countryDropdown).click();
        driver.findElement(By.xpath ("//*[contains(@class, 'Countries_countryName__XU42W') and contains(text(), \'"+countryValue+"\')]")).click();
    }

    public void selectGrade(String grade){
        driver.findElement(gradeDropdown).click();
        driver.findElement(By.xpath ("//*[contains(@class, 'WhichClass_countryName__+VCbE') and contains(text(), \'"+grade+"\')]")).click();
    }

}
