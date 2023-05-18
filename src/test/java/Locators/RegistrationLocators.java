package Locators;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Arrays;

public class RegistrationLocators {
    public By uniDropDown = By.cssSelector("[placeholder=\"University\"]");
    public By academicDropDown = By.cssSelector("[placeholder=\"Academic\"]");
    public By usernameField = By.className("RegisterOne_name__kyZuy");
    public By passwordField = By.className("RegisterOne_password__vilN8");
    public By fall2024 = By.id("start");
    public By early = By.id("early");
    public By yes = By.id("yes");
    public By yess = By.id("yess");
    public By confirm = By.cssSelector("[name=\"comf\"]");
    public By emailField = By.className("RegisterOne_mail__+-frM");
    public By confirmPasswordField = By.cssSelector("[placeholder = \"Confirm Password\"]");
    public By countryNumDropdown = By.className("PhoneInputCountrySelect");
    public By nextButton = By.className("RegisterOne_btn__AIT2E");
    public By mobileNumField = By.className("PhoneInputInput");
    public By nextButton2 = By.className("RegisterTwo_bt__BM+ks");
    public By submit = By.className("RegisterNine_bt__AnYT7");
    public By admTextbox = By.className("RegisterNine_area__Dsu5J");
    public By verifyButton = By.className("Verify_btn__voN7H");
    public By privacyPolicy = By.className("LoginForm_unChecked__KapKf");
    public String uniClass= "Update_countryName__5dMn4";
    public String academicClass= "Update_countryName__5dMn4";
    public ArrayList<String> registrationVerifyAssert = new ArrayList<String>(Arrays.asList("Congratulations!", "Your email has been successfully verified."));
    public By goToLogIN = By.className("Massages_btnr__N7W4x");
}
