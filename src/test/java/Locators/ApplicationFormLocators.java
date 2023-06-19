package Locators;

import DTO.UserDTO;
import org.openqa.selenium.By;

public class ApplicationFormLocators {
    public By fullName = By.cssSelector(" div.Application_inp__di2Kd > div:nth-child(1) > div:nth-child(1) > input");
    public By email = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[1]/div[1]/div[2]/input");
    public By arrowBtn = By.className("Application_arrowBottom__ed0qz");
    public By country = By.xpath ("//*[contains(@class, 'Countries_countryName__XU42W') and contains(text(), 'Armenia')]");
    public By school = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[1]/div[2]/div[1]/input");
    public By gpa = By.cssSelector("[placeholder=\"GPA\"]");
    public By counselor = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[1]/div[2]/div[4]/input");
    public By counselorEmail = By.cssSelector(" div.Application_inp__di2Kd > div:nth-child(2) > div:nth-child(5) > input");
    public By uploadFile = By.xpath("//*[@id=\"fileInput\"]");
    public By secondUpload = By.xpath("//*[@id=\"ownPost\"]");
    public By firstTextarea = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[2]/textarea");
    public By secondTextarea = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[3]/textarea");
    public By submit = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[6]/button");
    public By studentForm = By.xpath("//p[contains(text(),'Your application form successfully sent ')]");
    public By counselorName = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[1]/div[1]/input");
    public By counselorsEmail = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[1]/div[2]/input");
    public By countryNumDropdown = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[1]/div[3]/div/div/select");
    public By mobileNumField = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[1]/div[3]/div/input");
    public By counselorCountry = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[1]/div[4]/div/span");
    public By counselorSchool = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[1]/div[5]/input");
    public By applicantName = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[2]/div[1]/input");
    public By applicantEmail = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[2]/div[2]/input");
    public By applicantGPA = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[2]/div[4]/input");
    public By gradeSign = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[1]/div[2]/div[3]/div/span");
    public By grade = By.xpath("//*[contains(@class, 'WhichClass_countryName__+VCbE') and contains(text(),'Up to 9th grade')]");
    public By text = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[2]/textarea");
    public By submitBtn = By.xpath("//*[@id=\"root\"]/main/section[3]/form/div/div[4]/button");
    public By largeFile = By.xpath("//*[@id=\"root\"]/main/section[2]/form/div/div[1]/div[1]/div[5]/p[2]");
}
