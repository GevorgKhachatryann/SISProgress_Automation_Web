package methods;

import DTO.UserDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;


public class RegistrationPage {

    private WebDriver driver;
    public static WebDriverWait wait;


    private By countryDropdown = By.className("RegisterOne_arrowBottom__86Gfn");
    private By gradeDropdown = By.cssSelector("[placeholder=\"Grade level\"]");
    private By dateField = By.className("MuiSvgIcon-root");
    private By dateArrow = By.className("MuiPickersCalendarHeader-switchViewIcon");


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

//    public void enterDate(String year, String month, String day ){
//        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
//        driver.findElement(dateField).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(dateArrow));
//        driver.findElement(dateArrow).click();
//        driver.findElement(By.xpath("//*[text()='" + year + "']")).click();
//        driver.findElement(By.cssSelector(".MuiTypography-root:nth-child("+month+")")).click();
//        driver.findElement(By.xpath("//*[text()='" + day + "']")).click();
//    }
    public void selectCountry(String countryValue){
        driver.findElement(countryDropdown).click();
        driver.findElement(By.xpath ("//*[contains(@class, 'Countries_countryName__XU42W') and contains(text(), \'"+countryValue+"\')]")).click();
    }

    public void selectGrade(String grade){
        driver.findElement(gradeDropdown).click();
        driver.findElement(By.xpath ("//*[contains(@class, 'WhichClass_countryName__+VCbE') and contains(text(), \'"+grade+"\')]")).click();
    }
    public void registration() throws IOException {
        UserDTO dto = new UserDTO();
        ApiRequests requests = new ApiRequests(driver);
        requests.generateRandomEmailForTest();
        requests.performRegistration(dto.getEmail());
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        System.out.println("Verification link: " + verificationLink);
        if (verificationLink != null) {
            driver.get(verificationLink);
        } else {
            System.out.println("Verification link not found!");
        }
    }
}
