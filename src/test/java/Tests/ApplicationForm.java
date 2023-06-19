package Tests;

import Config.BaseClass;
import Config.Constants;
import Config.URL;
import DTO.UserDTO;
import Locators.RegistrationLocators;
import Locators.ApplicationFormLocators;
import methods.ApiRequests;
import methods.General;
import methods.RegistrationPage;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ApplicationForm extends BaseClass {
    @Test
    public void submitStudentApplicationForm(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        ApplicationFormLocators appLocators = new ApplicationFormLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();

        driver.get(URL.FINANCIAL_AID);
        general.enterText(appLocators.fullName,dto.getFullName());
        requests.generateRandomEmailForTest();
        general.enterText(appLocators.email,dto.getEmail());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        general.clickElement(appLocators.arrowBtn);
        general.clickElement(appLocators.country);
        general.enterText(appLocators.school,dto.getSchool());
        registrationPage.selectGrade(dto.getGrade());
        general.enterText(appLocators.gpa, Constants.TEN);
        general.enterText(appLocators.counselor,dto.getUpdatedName());
        requests.generateRandomEmailForTest();
        general.enterText(appLocators.counselorEmail,dto.getEmail());
        general.upload(Constants.TESVAN_PNG,appLocators.uploadFile);
        general.enterText(appLocators.firstTextarea,dto.getText());
        general.enterText(appLocators.secondTextarea,dto.getText());
        general.upload(Constants.TESVAN_PNG,appLocators.secondUpload);
        general.clickElement(appLocators.submit);
        general.assertThatElementContains(Constants.APPLICATION_FORM_SENT,appLocators.studentForm);
    }
    @Test
    public void submitCounselorReferralForm(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        ApplicationFormLocators appLocators = new ApplicationFormLocators();

        driver.get(URL.FINANCIAL_AID);
        general.enterText(appLocators.counselorName,dto.getFullName());
        requests.generateRandomEmailForTest();
        general.enterText(appLocators.counselorsEmail,dto.getEmail());
        general.selectFromDropdown(appLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(appLocators.mobileNumField, dto.getMobileNumber());
        general.clickElement(appLocators.counselorCountry);
        general.clickElement(appLocators.country);
        general.enterText(appLocators.counselorSchool,dto.getSchool());
        general.enterText(appLocators.applicantName,dto.getUpdatedName());
        requests.generateRandomEmailForTest();
        general.enterText(appLocators.applicantEmail, dto.getEmail());
        general.clickElement(appLocators.gradeSign);
        general.clickElement(appLocators.grade);
        general.enterText(appLocators.applicantGPA, Constants.TEN);
        general.enterText(appLocators.text,dto.getText());
        general.clickElement(appLocators.submitBtn);
        general.assertThatElementContains(Constants.APPLICATION_FORM_SENT,appLocators.studentForm);
    }
    @Test
    public void verifyFilesTooLargeErrorMessageDisplayed(){
        UserDTO dto = new UserDTO();
        General general = new General(driver);
        ApiRequests requests = new ApiRequests(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        ApplicationFormLocators appLocators = new ApplicationFormLocators();
        RegistrationLocators registrationLocators = new RegistrationLocators();

        driver.get(URL.FINANCIAL_AID);
        general.enterText(appLocators.fullName,dto.getFullName());
        requests.generateRandomEmailForTest();
        general.enterText(appLocators.email,dto.getEmail());
        general.selectFromDropdown(registrationLocators.countryNumDropdown, dto.getCountryNumValue());
        general.enterText(registrationLocators.mobileNumField, dto.getMobileNumber());
        general.clickElement(appLocators.arrowBtn);
        general.clickElement(appLocators.country);
        general.enterText(appLocators.school,dto.getSchool());
        registrationPage.selectGrade(dto.getGrade());
        general.enterText(appLocators.gpa, Constants.TEN);
        general.enterText(appLocators.counselor,dto.getUpdatedName());
        requests.generateRandomEmailForTest();
        general.enterText(appLocators.counselorEmail,dto.getEmail());
        general.upload(Constants.TEST_MATERIAL,appLocators.uploadFile);
        general.enterText(appLocators.firstTextarea,dto.getText());
        general.enterText(appLocators.secondTextarea,dto.getText());
        general.upload(Constants.TESVAN_PNG,appLocators.secondUpload);
        general.clickElement(appLocators.submit);
        general.assertThatElementContains(Constants.FILES_ARE_TOO_LARGE,appLocators.largeFile);
    }
}
