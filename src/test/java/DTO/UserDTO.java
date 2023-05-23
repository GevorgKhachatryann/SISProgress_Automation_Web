package DTO;

import java.time.Instant;

public class UserDTO {
    private static long randomNum = Instant.now().toEpochMilli();
    private static String email;
    private String fullName = "Web User Test";
    private String updatedName = "Web";
    private String password = "Test1234*";
    private String countryNumValue = "AM";
    private String grade = "Up to 9th grade";
    private String mobileNumber = "+374 999999999999";
    private String updatedMobileNumber = "+37496666666";
    private String country = "Armenia";
    private String university = "Duke University";
    private String academics = "Aerospace Engineering Certificate";
    private String admText = "nurse";
    private String validEmail = "userweb@test.com";
    private String InvalidEmail = "userweb@test.cm";
    private String mail = "wetera4831@favilu.com";
    private String InvalidPassword = "Test1234";
    private String validUserName = "Web User Test";

    public static String changedPass = "Test12345*";


    public String getFullName() {
        return fullName;
    }
    public String getUpdatedName(){
        return updatedName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getCountryNumValue(){
        return countryNumValue;
    }
    public String getMobileNumber(){
        return mobileNumber;
    }
    public String getUpdatedMobileNumber(){
        return updatedMobileNumber;
    }
    public String getCountry(){
        return country;
    }
    public String getGrade(){
        return  grade;
    }
    public String getUniversity(){
        return  university;
    }
    public String getAcademics(){
        return  academics;
    }
    public String getAdmText(){
        return admText;
    }
    public String getValidEmail(){
        return validEmail;
    }
    public String getInvalidEmail(){
        return InvalidEmail;
    }
    public String getInValidPassword(){
        return InvalidPassword;
    }
    public String getValidUserName(){
        return validUserName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getMailForUpdate(){
        return mail;
   }
    public String getChangedPass(){
        return changedPass;
   }
}
