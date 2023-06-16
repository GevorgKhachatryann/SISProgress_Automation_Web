package DTO;

import Config.Constants;
import org.testng.annotations.DataProvider;

import java.time.Instant;

public class UserDTO {
    private static long randomNum = Instant.now().toEpochMilli();
    private static String email;
    private static String username;
    private static String id;
    private static String token;
    private static String registrationMail;
    private static int randomIndex;
    private static String percent;
    private String fullName = "Web User Test";
    private String updatedName = "Web";
    private String password = "Test1234*";
    private String countryNumValue = "AM";
    private String grade = "Up to 9th grade";
    private String TenGrade = "10th grade or above";
    private String mobileNumber = "+374 999999999999";
    private String updatedMobileNumber = "+37496666666";
    private String country = "Armenia";
    private String university = "Duke University";
    private String academics = "Aerospace Engineering Certificate";
    private String school = "Phillips Academy Andover";
    private String admText = "nurse";
    private String validEmail = "userweb@test.com";
    private String InvalidEmail = "userweb@test.cm";
    private String mail = "wetera4831@favilu.com";
    private String InvalidPassword = "Test1234";
    private String validUserName = "Web User Test";

    public static String changedPass = "Test12345*";
    public String feedback = "Overall, the task provided was relatively easy and straightforward. The instructions were clear and concise, making it easy to understand the expected outcome.";
    public String question = "I'm preparing for the SAT and would like to know if your site offers any study resources or practice materials specifically tailored to the SAT?";

    public String getFullName() {
        return fullName;
    }
    public String getUsername() {
        return username;
    }
    public String getFeedback() {
        return feedback;
    }

    public String getQuestion() {
        return question;
    }

    public String getUpdatedName(){
        return updatedName;
    }
    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
    public String getPercent() {
        return percent;
    }

    public String getToken(){
        return token;
    }
    public int getRandomIndex(){
        return randomIndex;
    }
    public String getRegistrationMail(){
        return registrationMail;
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
    public String getTenGrade(){
        return TenGrade;
    }
    public String getSchool(){
        return school;
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
    public void setId(String id){
        this.id = id;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void setToken(String token){
        this.token = token;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setRegistrationMail(String registrationMail){
        this.registrationMail = registrationMail;
    }
    public void setRandomIndex(int randomIndex){
       this.randomIndex = randomIndex;
    }
    public String getMailForUpdate(){
        return mail;
   }
    public String getChangedPass(){
        return changedPass;
   }
    @DataProvider(name = "searchVariations")
    public static Object[][] searchVariations() {
        return new Object[][]{
                {"Recommendation", 1},
                {"Re", 3},
                {"RECOMMENdation", 1},
                {"rec", 1},
                {"ReCoMm", 1},
                {"RECOMM", 1},
                {"recommendation", 1},
                {"RECOMMENDATION", 1},
                {"RE1", 0}
        };
    }
    @DataProvider(name = "TaskSearch")
    public static Object[][] searchVariation() {
        return new Object[][]{
                {"YOUTH", 3},
                {"youth", 3},
                {"YOUTH123", 0},
                {"y0uth", 0},
                {"YOUTH!", 0},
                {"Youth", 3},
        };
    }
}
