package Locators;

import org.openqa.selenium.By;

public class LoginLocators {
    public By loginField = By.cssSelector("[name=\"mail\"]");
    public By passwordField = By.cssSelector("[placeholder=\"Password\"]");
    public By loginButton = By.className("LoginForm_btn__FLvAr");
    public By Login  = By.cssSelector("a.Massages_btnr__N7W4x[href='/login']");
    public By formChangeLogin = By.className("FormChange_bt__nsc5s");
    public By errorMessage = By.className("LoginForm_error__S0aEu");
    public By forget = By.className("LoginForm_forgotstyle__YaFv9");
    public By emailField = By.cssSelector("[name=\"Email\"]");
    public By sendEmail = By.className("FormForgotPass_btn__UfUqW");
    public By changeButton = By.className("FormChange_btn__m2S4P");
    public By PassChangedMessage = By.className("FormChange_er__fGHER");
    public By PassErrorMessage = By.className("FormChange_error__xUPE+");
    public By logoutIcon = By.cssSelector("#root > div > div > div > div.SideBar_actt__qidHs > div.SideBar_parent__oir9z > svg");
    public By logoutBtn = By.xpath("//*[@id=\"ModalLog\"]/div/div/div/button[2]");
    public By emailChangedMessage = By.cssSelector("#root > main > div > div > p");
    public By verifyPrimaryBtn = By.className("VerifyPrimare_btn__KNVqH");
    public By cancelLogout = By.xpath("//*[@id=\"ModalLog\"]/div/div/div/button[1]");

}
