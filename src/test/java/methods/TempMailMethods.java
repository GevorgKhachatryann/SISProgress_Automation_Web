package methods;

import DTO.UserDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TempMailMethods {
    private WebDriver driver;
    public TempMailMethods(WebDriver driver) {
        this.driver = driver;
    }
    public void getMail(By selector){
        UserDTO dto = new UserDTO();
        WebElement textElement = driver.findElement(selector);
        String text = textElement.getText();
        dto.setEmail(text);
    }
}
