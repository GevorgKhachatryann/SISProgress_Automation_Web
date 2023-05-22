package Locators;

import org.openqa.selenium.By;

public class SettingsLocators {
    public By settingsIcon = By.className("SideBar_img__5u3Xu");
    public By menuSection = By.className("Menu_btn__Hy5ib");
    public By name = By.className("Pick_one__5ODKL");
    public By personalDetailsName = By.className("Personal_name__lvDyu");
    public By mailInput = By.className("MailSettings_name__hTaVX");
    public By countryDrop = By.className("Personal_password__wyGWc");
    public By country = By.xpath ("//*[@id=\"root\"]/div/main/div/div[4]/div/form/div[2]/div[2]/div/div[2]/div/p[2]");
    public By updateBtn = By.xpath("//button[contains(text(),'Update')]");
    public By modal = By.className("Mod_parent__98zB4");
    public By discardBtn = By.xpath("//button[contains(text(),'Discard')]");
    public By sendVerifyBtn = By.className("ModalPrimary_btn__eJMny");
    public By addSecondary = By.className("MailSettings_adde__4dOqJ");
    public By secondaryInput = By.xpath("//*[@id=\"root\"]/div/main/div/div[4]/div/div[2]/form[2]/div/div[1]/div/input");
    public By secondaryUpdate = By.className("MailSettings_btn__U7fL4");
    public By sendSecondaryVerify = By.xpath("//*[@id=\"button\"]");

}

