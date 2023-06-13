package Locators;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Arrays;

public class RegistrationLocators {
    public By uniDropDown = By.cssSelector("[placeholder=\"University\"]");
    public By dreamUniDropdown = By.cssSelector("[placeholder=\"Dream University\"]");
    public By academicDropDown = By.cssSelector("[placeholder=\"Academic\"]");
    public By highSchoolDropdown = By.cssSelector("[placeholder=\"High school\"]");
    public By academicProgram = By.cssSelector("[placeholder=\"Academic Program (choose up to 3)\"]");
    public By usernameField = By.className("RegisterOne_name__kyZuy");
    public By fullNameField = By.className("Reg_name__XzJTg");
    public By passwordField = By.className("RegisterOne_password__vilN8");
    public By regPass = By.cssSelector("#password");
    public By fall2024 = By.id("start");
    public By early = By.id("early");
    public By yes = By.id("yes");
    public By yess = By.id("yess");
    public By confirm = By.cssSelector("[name=\"comf\"]");
    public By forgetPassField = By.className("FormChange_password__Iwy8f");
    public By emailField = By.className("RegisterOne_mail__+-frM");
    public By emailForReg = By.className("Reg_mail__ah7BY");
    public By confirmPasswordField = By.cssSelector("[placeholder = \"Confirm Password\"]");
    public By confirmRegPass = By.cssSelector("[placeholder=\"Confirm Password\"]");
    public By countryNumDropdown = By.className("PhoneInputCountrySelect");
    public By nextButton = By.className("RegisterOne_btn__AIT2E");
    public By mobileNumField = By.className("PhoneInputInput");
    public By dropdownSign = By.className("Dropdown_arrowTop__Yt6A+");
    public By nextButton2 = By.className("RegisterTwo_bt__BM+ks");
    public By submit = By.className("Quest_bt__yQtES");
    public By submitRegistration = By.className("Reg_bt__H-94T");
    public By admTextbox = By.xpath("//*[@id=\"Quest\"]/div/div/div/div/div[2]/div/form/div[5]/textarea");
    public By verifyButton = By.className("Verify_btn__voN7H");
    public By sendVerification = By.xpath("//*[@id=\"root\"]/main/section/div/div/button");
    public By privacyPolicy = By.className("LoginForm_unChecked__KapKf");
    public String uniClass= "Update_countryName__5dMn4";
    public String universityClass = "DropdownList_countryName__qjn2X";
    public String academicClass= "Update_countryName__5dMn4";
    public String schools = "Schoole_countryName__4vSw-";
    public String academicClasses= "DropdownList_ru__jkB39";
    public ArrayList<String> registrationVerifyAssert = new ArrayList<String>(Arrays.asList("Congratulations!", "Your email has been successfully verified."));
    public By getStarted = By.className("Massages_btnr__N7W4x");
    public By fromUS = By.id("startt");
    public By achievements = By.id("ach");
    public By tests = By.id("btn");
    public By activityDropdown = By.className("Activity_pass__KA+JM");
    public By check = By.cssSelector("[type=\"checkbox\"]");
    public By mailError = By.className("Reg_errorText__xBxO6");
    public By header = By.tagName("header");
    public By passNotMatch = By.className("Reg_errorText__xBxO6");
    public By emailExist = By.cssSelector("#root > main > section > div > div > div > div > form > p");

}
