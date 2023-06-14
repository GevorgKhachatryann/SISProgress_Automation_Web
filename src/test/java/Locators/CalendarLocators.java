package Locators;

import org.openqa.selenium.By;

public class CalendarLocators {
    public By calendarIcon = By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div[2]/a/div/img");
    public By AddTask = By.className("Month_add__IkHct");
    public By checkbox = By.cssSelector(".LoginForm_unChecked__KapKf");
    public By subTaskCheckbox = By.cssSelector(".Modal_parent__PqnsG [class=\"LoginForm_unChecked__KapKf\"]");
    public By AddBtn = By.cssSelector("#myModal > div > button");
    public By firstTask = By.className("Cube_parent__saCh0");
    public By TasksName = By.className("StatusModalContent_titl2__sMIIq");
    public By TaskStatus = By.className("Cube_text__CMIMm");
    public By taskFuture = By.className("StatusModalContent_rtext__9yeVL");
    public By deleteBtn = By.className("StatusModalContent_submitee__RFyKV");
    public By submit =By.className("StatusModalContent_submite__ENbdP");
    public By closeModal = By.xpath("//*[@id=\"myModal\"]/div/div[1]/span");
    public By modal =  By.className("Modal_parent__PqnsG");
    public By days = By.className("Cal_nameWithDays__9S0U7");
    public By body = By.tagName("body");
    public By title = By.className("StatusModalContent_titl1__1SpYm");
    public By leaveFeedback = By.xpath("//button[contains(text(),'Leave your feedback')]");
    public By textField = By.className("ModalFit_area__OX9Ad");
    public By modalSubmit = By.xpath("//*/div/div/form/button");
    public By history = By.cssSelector("[class=\"ModalFit_history__TJdTW\"] > img");
    public By historyText = By.className("ModalFit_texts__ZHQ45");
}
