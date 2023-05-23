package Tests;

import Config.BaseClass;
import Config.URL;
import DTO.UserDTO;
import Locators.*;
import methods.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseClass {

    @Test
    public void registerWithValidData(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        TabControl tabControl = new TabControl(driver);
        MailLocators mailLocators = new MailLocators();
        TempMailMethods tempMailMeth = new TempMailMethods(driver);
        RegistrationLocators registrationLocators = new RegistrationLocators();
        RegistrationPage registrationPage = new RegistrationPage(driver);

        driver.get(URL.Email_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.uniqueMail));
        tempMailMeth.getMail(mailLocators.uniqueMail);

        System.out.println(dto.getEmail());

        tabControl.openNewTab();
        tabControl.switchTab();

        driver.get(URL.Registration_URL);

        general.enterText(registrationLocators.usernameField, dto.getFullName());
        general.enterText(registrationLocators.emailField, dto.getEmail());
        general.enterText(registrationLocators.passwordField, dto.getPassword());
        general.enterText(registrationLocators.confirmPasswordField, dto.getPassword());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        registrationPage.enterDate("2000", "03", "20");
        registrationPage.selectCountry(dto.getCountry());
        registrationPage.selectGrade(dto.getGrade());
        general.clickElement(registrationLocators.nextButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationLocators.uniDropDown));
        general.selectFromFancyDropdown(registrationLocators.uniDropDown, registrationLocators.uniClass, dto.getUniversity());
        general.selectFromFancyDropdown(registrationLocators.academicDropDown, registrationLocators.academicClass, dto.getAcademics());
        general.clickElement(registrationLocators.fall2024);
        general.clickElement(registrationLocators.early);
        general.clickElement(registrationLocators.yes);
        general.clickElement(registrationLocators.yess);
        general.clickElement(registrationLocators.nextButton2);
        general.enterText(registrationLocators.admTextbox, dto.getAdmText());
        general.clickElement(registrationLocators.privacyPolicy);
        general.clickElement(registrationLocators.submit);
        general.clickElement(registrationLocators.verifyButton);
        tabControl.switchTabToLeft();
        general.scroll(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailLocators.indexSection));
        WebElement element = driver.findElement(mailLocators.indexSection);
        element.click();
        general.scroll(13);
        driver.findElement(mailLocators.verifyInMail).click();
        general.assertThatPageContains(registrationLocators.registrationVerifyAssert);
        general.scroll(2);
    }


}


