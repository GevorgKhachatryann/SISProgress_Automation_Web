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
    public By emailBox =  By.className("MailSettings_name__hTaVX");
    public By backToSettings = By.xpath("//*[@id=\"root\"]/main/div/div/a");
    public By removeSecondary = By.className("MailSettings_add__NesG7");
    public By deleteBtn = By.xpath("//button[contains(text(),'Delete account')]");
    public By reasonField = By.className("DeletePop_area__3+VTA");
    public By deleteNext = By.className("DeletePop_btns__l1cY3");
    public By enableNext = By.className("DeletePop_btn__DcS69");
    public By passForDelete = By.className("PassDelete_password__Gpgrh");
    public By submit = By.className("PassDelete_btn__-quG5");
    public By accountDeletedMessage = By.className("DeleteMessage_titlesr__ISdZs");
    public By skip = By.className("DeletePop_skip__8c1hF");
    public By cancel = By.className("PassDelete_btnn__3j+qO");
    public By deactivate = By.xpath("//button[contains(text(),'Deactivate My account')]");
    public By deactivateReason = By.className("DeactivePop_area__LTy7i");
    public By reasonNext = By.className("DeactivePop_btn__9vfh9");
    public By passForDeactivate = By.className("DeactivePass_password__AEa-E");
    public By submitDeactivate = By.className("DeactivePass_btn__Q8OeS");
    public By deactivatedMessage = By.className("DeactivateMessage_titlesr__u60Wj");
    public By deactivateSkipBtn = By.className("DeactivePop_skip__5xtNN");
    public By cancelBtn = By.className("DeactivePass_btnn__kJJjp");
    public By verifyMessage = By.xpath("//*[contains(text(), 'verify your secondary email')]");
    public By emailInUseMessage = By.className("ModalMail_err__UJ9Do");
}

