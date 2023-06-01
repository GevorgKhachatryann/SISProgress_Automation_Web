package methods;

import DTO.UserDTO;
import Locators.CalendarLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class General {
    private final WebDriver driver;
    public static WebDriverWait wait;

    public General(WebDriver driver) {
        this.driver = driver;
    }

    private static final List<String> DELETE_ACCOUNT_REASONS = new ArrayList<>();
    private static List<String> ACCOUNT_DEACTIVATION_REASONS = new ArrayList<>();


    static {
        DELETE_ACCOUNT_REASONS.add("No longer need the account");
        DELETE_ACCOUNT_REASONS.add("Found an alternative service");
        DELETE_ACCOUNT_REASONS.add("Privacy concerns");
        DELETE_ACCOUNT_REASONS.add("Account is no longer active");
        DELETE_ACCOUNT_REASONS.add("Don't like this site");
        DELETE_ACCOUNT_REASONS.add("Too many ads on the site");
        DELETE_ACCOUNT_REASONS.add("Difficult to navigate the site");
        DELETE_ACCOUNT_REASONS.add("Unsatisfactory user experience");
        DELETE_ACCOUNT_REASONS.add("Security concerns");
    }

    static {
        ACCOUNT_DEACTIVATION_REASONS.add("No longer need the account");
        ACCOUNT_DEACTIVATION_REASONS.add("Found an alternative service");
        ACCOUNT_DEACTIVATION_REASONS.add("Privacy concerns");
        ACCOUNT_DEACTIVATION_REASONS.add("Account is no longer active");
        ACCOUNT_DEACTIVATION_REASONS.add("Don't like this site");
        ACCOUNT_DEACTIVATION_REASONS.add("Too many ads on the site");
        ACCOUNT_DEACTIVATION_REASONS.add("Difficult to navigate the site");
        ACCOUNT_DEACTIVATION_REASONS.add("Unsatisfactory user experience");
        ACCOUNT_DEACTIVATION_REASONS.add("Security concerns");
    }

    public int totalPoints = 0;
    public double targetPoints = 0.3 * 320;  // 30% of 320
    public double lastPoints = 0;
    public Set<Integer> previousIndexes = new HashSet<>();
    public int totalPointsFor20 = 0;
    public double targetPointsFor20 = 0.01 * 300;  // 30% of 320
    public double lastPointsFor20 = 0;
    public Set<Integer> previousIndexesFor20 = new HashSet<>();
    public double filteredText = 0;


    public void performTask(WebDriver driver, General general, TaskPage taskPage, WebDriverWait wait,
                            CalendarLocators calendarLocators) {
        do {
            wait.until(ExpectedConditions.elementToBeClickable(calendarLocators.AddTask));

            general.clickElement(calendarLocators.AddTask);

            int randomIndex;
            do {
                wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
                wait.until(ExpectedConditions.elementToBeClickable(calendarLocators.checkbox));

                randomIndex = taskPage.clickRandomCheckbox(driver, calendarLocators.checkbox);
                System.out.println("index: " + randomIndex);
            } while (previousIndexes.contains(randomIndex));
            previousIndexes.add(randomIndex);

            String taskName = taskPage.getTaskName(driver, randomIndex);
            String points = taskPage.getPoints(driver, randomIndex);
            String number = points.replaceAll("[^\\d.]+", "");
            System.out.println(number);
            System.out.println(taskName);
            System.out.println(points);

            general.clickElement(calendarLocators.AddBtn);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Planned')]")));
            driver.findElement(By.xpath("//p[contains(text(),'Planned')]")).click();
            taskPage.clickAllSubTaskCheckboxes(driver, calendarLocators.subTaskCheckbox);
            String sub = "//*[@id=\"statusModal___" + randomIndex + "\"]/div/div[2]/div/div[2]/button";
            System.out.println(sub);
            driver.findElement(By.xpath(sub)).click();

            double taskPoints = Double.parseDouble(number);
            totalPoints += taskPoints;
            lastPoints = taskPoints;

            System.out.println(totalPoints);
            System.out.println(previousIndexes);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (!(totalPoints >= targetPoints));

        System.out.println("Total Points: " + totalPoints);
        System.out.println("Last Points: " + lastPoints);
    }

    public void performFewTasks(WebDriver driver, General general, TaskPage taskPage, WebDriverWait wait,
                                CalendarLocators calendarLocators) {
        UserDTO dto = new UserDTO();
        do {
            wait.until(ExpectedConditions.elementToBeClickable(calendarLocators.AddTask));

            general.clickElement(calendarLocators.AddTask);

            int randomIndex;
            do {
                wait.until(ExpectedConditions.visibilityOfElementLocated(calendarLocators.checkbox));
                wait.until(ExpectedConditions.elementToBeClickable(calendarLocators.checkbox));

                randomIndex = taskPage.clickRandomCheckbox(driver, calendarLocators.checkbox);
                System.out.println("index: " + randomIndex);
            } while (previousIndexesFor20.contains(randomIndex));
            previousIndexesFor20.add(randomIndex);

            String taskName = taskPage.getTaskName(driver, randomIndex);
            String points = taskPage.getPoints(driver, randomIndex);
            String number = points.replaceAll("[^\\d.]+", "");
            System.out.println(number);
            System.out.println(taskName);
            System.out.println(points);

            general.clickElement(calendarLocators.AddBtn);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Planned')]")));
            driver.findElement(By.xpath("//p[contains(text(),'Planned')]")).click();

            taskPage.clickAllSubTaskCheckboxes(driver, calendarLocators.subTaskCheckbox);
            String sub = "//*[@id=\"statusModal___" + randomIndex + "\"]/div/div[2]/div/div[2]/button";
            System.out.println(sub);
            driver.findElement(By.xpath(sub)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Completed')]")));

            List<WebElement> elements = driver.findElements(By.xpath("//p[contains(text(),'Completed')]"));

            if (elements.isEmpty()) {
                break;
            }

            WebElement lastElement = elements.get(elements.size() - 1);
            lastElement.click();

            WebElement element = driver.findElement(By.xpath("//*[@id='statusModal___" + randomIndex + "']/div/div[2]/div/div[1]/div[5]/div[2]/p[2]"));

            String text = element.getText();

            filteredText = Double.parseDouble(text.replaceAll("[^0-9.]", ""));

            System.out.println("Filtered Text: " + Double.parseDouble(String.valueOf(filteredText)));
            driver.findElement(By.xpath("//*[@id='statusModal___" + randomIndex + "']/div/div[1]/span")).click();

            totalPointsFor20 += filteredText;

        } while (!(totalPointsFor20 >= targetPointsFor20));

        double roundedTotalPointsFor20 = Math.round(filteredText * 10) / 10.0;

            if (roundedTotalPointsFor20 == (int) roundedTotalPointsFor20) {
                System.out.println("Total points: " + (int) roundedTotalPointsFor20);
            } else {
                System.out.println("Total points: " + roundedTotalPointsFor20);
            }
        double percentage = (double) totalPointsFor20 / 300 * 100;
        double roundedPercentage = Math.round(percentage * 10) / 10.0;

            if (roundedPercentage == (int) roundedPercentage) {
                System.out.println("Percentage: " + (int) roundedPercentage);
            } else {
                System.out.println("Percentage: " + roundedPercentage);
            }
        dto.setPercent(String.valueOf(roundedPercentage));
        double percent = roundedPercentage;
        String formattedPercent;

            if (percent % 1 == 0) {
                formattedPercent = String.format("%.0f", percent);
            } else {
                formattedPercent = String.format("%.1f", percent);
            }

        System.out.println(formattedPercent);
        dto.setPercent(formattedPercent);
    }

    public double extractTaskPoints(String points) {
        String number = points.replaceAll("[^\\d.]+", "");
        return Double.parseDouble(number);
    }

    public void click(WebDriver driver, By selector) {
        WebElement element = driver.findElement(selector);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void selectFromFancyDropdown(By arrowSelector, String classSelector, String value) {
        driver.findElement(arrowSelector).click();
        driver.findElement(By.xpath("//*[contains(@class, \'" + classSelector + "\') and contains(text(), \'" + value + "\')]")).click();
    }

    public void clickElement(By selector) {
        driver.findElement(selector).click();
    }

    public void enterText(By selector, String username) {
        driver.findElement(selector).clear();
        driver.findElement(selector).click();
        driver.findElement(selector).sendKeys(username);
    }

    public void scroll(int num) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long scrollHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        long scrollTo = scrollHeight / num;
        js.executeScript("window.scrollTo(0, arguments[0]);", scrollTo);

    }

    public void selectFromDropdown(By selector, String countryValue) {
        Select dropdown = new Select(driver.findElement(selector));
        dropdown.selectByValue(countryValue);
    }

    public void explicitWait(By locator, int waitingDuration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingDuration));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void assertThatPageContains(ArrayList<String> expected) {
        for (int a = 0; a < expected.size(); a++) {
            Assert.assertTrue(driver.getPageSource().contains(expected.get(a)));
        }
    }

    public void waitAndAssertThatEquals(String text, By locatorToCompare) {
        WebElement element = driver.findElement(locatorToCompare);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        String compareWith = driver.findElement(locatorToCompare).getText();
        Assert.assertEquals(text, compareWith);
    }

    public void assertThatValueEquals(String text, By locatorToCompare) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(locatorToCompare, "value", text));
        String compareWith = driver.findElement(locatorToCompare).getAttribute("value");
        Assert.assertEquals(text, compareWith);
    }

    public void assertThatElementContains(String text, By locatorToCompare) {
        String compareWith = driver.findElement(locatorToCompare).getText();
        Assert.assertTrue(compareWith.contains(text));
    }

    public void waitAndAssertUntilTextContains(By locator, String text, int waitingDuration) {
        WebElement element = driver.findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingDuration));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        Assert.assertTrue(element.getText().contains(text));
    }

    public void scrollPageToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public String getFormattedDate() {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

        String formattedDate = currentDate.format(formatter);
        return formattedDate;

    }

    public String getFormattedTomorrowDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d");
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        String formattedMonthAndDate = tomorrowDate.format(dateFormatter);

        int dayOfMonth = tomorrowDate.getDayOfMonth();
        String suffix;
        if (dayOfMonth >= 11 && dayOfMonth <= 13) {
            suffix = "th";
        } else {
            int lastDigit = dayOfMonth % 10;
            switch (lastDigit) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        formattedMonthAndDate += suffix;
        return formattedMonthAndDate;
    }

    public static String[] extractDateParts(String dateText) {

        String[] parts = dateText.split("\\s+");
        String dayText = parts[0];
        String date = parts[1];

        return new String[]{dayText, date};
    }

    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String generateDeleteReason() {
        int randomIndex = new Random().nextInt(DELETE_ACCOUNT_REASONS.size());
        return DELETE_ACCOUNT_REASONS.get(randomIndex);
    }

    public boolean isDisabled(WebDriver driver, By cssSelector) {
        WebElement element = driver.findElement(cssSelector);
        String disabledAttribute = element.getAttribute("disabled");
        return disabledAttribute != null;
    }

    public boolean urlDoesNotContainPath(String url, String path) {
        return !url.contains(path);
    }

    public String generateDeactivationReason() {
        int randomIndex = new Random().nextInt(ACCOUNT_DEACTIVATION_REASONS.size());
        return ACCOUNT_DEACTIVATION_REASONS.get(randomIndex);
    }
}