package Locators;

import org.openqa.selenium.By;

public class ContactLocators {
    public By contactUs = By.className("ContactUs_title__4CGUq");
    public By fullName = By.cssSelector("#cont > div > form > div.FormContactUs_text__gDvR9 > input");
    public By email = By.className("FormContactUs_mail__mQSq8");
    public By question = By.className("FormContactUs_help__ieZyU");
    public By send = By.className("FormContactUs_btn__zfyHo");
    public By sendBtn = By.className("FormContactUs_btnn__kud7a");
    public By messageSent = By.xpath("//p[contains(text(),'Your message has been sent successfully.')]");
}
