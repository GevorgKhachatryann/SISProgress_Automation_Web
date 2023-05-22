package Locators;

import org.openqa.selenium.By;

public class MailLocators {
    public By indexSection = By.xpath("//*[@id=\"__next\"]/div[3]/section[1]/div/div[3]/div[1]/div/div[2]/button/div");
    public By reset = By.xpath("//p[contains(@title, 'SIS Progress: Reset Password')]");
    public By uniqueMail = By.cssSelector(".h-full.w-full.cursor-pointer");
    public By verifyInMail = By.xpath("//div[contains(text(), 'Verify')]");
    public By resetPassword = By.xpath("//*[@id=\"button\"]");
}
