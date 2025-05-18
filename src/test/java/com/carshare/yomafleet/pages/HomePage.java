package com.carshare.yomafleet.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

import static com.carshare.yomafleet.base.TestBase.driver;
import static org.testng.Assert.assertTrue;

public class HomePage {
    private static final String REQUIRED_INPUT_FIELD_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='The %s field is required.']";
    private final WebDriverWait wait;

    // Web Elements
    @FindBy(xpath = "//img[@alt='Yoma Car Share' and contains(@src, 'yomafleet_carshare_main_logo') and contains(@class, 'object-fit')]")
    public WebElement logoImage;

    @FindBy(xpath = "//a[contains(@class, 'text-textBlack') and contains(text(), 'Find A Car')]")
    public WebElement linkFindCar;

    @FindBy(id = "SimulateButton")
    public WebElement findCarButton;

    @FindBy(xpath = "//input[@type='checkbox' and contains(@class, 'form-check-input')]")
    public WebElement chkReturnLocation;

    @FindBy(id = "react-select-pickup_location-input")
    private WebElement pickupLocationDropdown;

    @FindBy(id = "react-select-return_location-input")
    private WebElement returnLocationDropdown;

    @FindBy(name = "pickup_date")
    private WebElement pickupDateInput;

    @FindBy(name = "pickup_time")
    private WebElement pickupTimeInput;

    @FindBy(name = "return_date")
    private WebElement returnDateInput;

    @FindBy(name = "return_time")
    private WebElement returnTimeInput;

    @FindBy(xpath = "//p[contains(text(), 'Found')]//span[@class='text-secondary']")
    private WebElement labelCountResult;

    @FindBy(xpath = "//a[@href='/locations' and contains(@class, 'text-secondary') and text()='Help me find a location']")
    private WebElement findLocationLink;

    @FindBy(xpath = "//h1[text()='Location Directory']")
    private WebElement locationDirectoryHeader;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Validation methods
    public boolean isFieldValidationErrorDisplayed(String fieldName) {
        String xpath = String.format(REQUIRED_INPUT_FIELD_ERROR, fieldName);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void verifyRequiredFieldsValidationMessages() {
        String[] requiredFields = {
                "Pickup Location", "Pickup Date", "Pickup Time",
                "Return Date", "Return Time"
        };

        for (String field : requiredFields) {
            assertTrue(isFieldValidationErrorDisplayed(field),
                    String.format("Validation message should appear for %s", field));
        }
    }

    // Navigation methods
    public void navigateToLocationDirectory() throws InterruptedException {
        findLocationLink.click();
        findLocationLink.click();
        assertTrue(locationDirectoryHeader.isDisplayed(), "Location Directory page should be displayed");
    }

    // Dropdown selection
    private void selectDropdownOption(WebElement dropdown, String option) {
        dropdown.click();
        String optionXpath = String.format("//div[contains(@class, 'select__option') and normalize-space(text())='%s']", option);
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
        optionElement.click();
    }

    public void selectPickupLocation(String location) {
        selectDropdownOption(pickupLocationDropdown, location);
    }

    public void selectReturnLocation(String location) {
        selectDropdownOption(returnLocationDropdown, location);
    }

    // Date and Time selection methods
    private void selectDate(WebElement dateInput, String year, String month, String day) {
        wait.until(ExpectedConditions.elementToBeClickable(dateInput)).click();

        WebElement yearInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//input[@class='numInput cur-year']")));
        yearInput.clear();
        yearInput.sendKeys(year);

        WebElement monthDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//select[@class='flatpickr-monthDropdown-months']")));
        new Select(monthDropdown).selectByVisibleText(month);

        WebElement dayElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//span[@class='flatpickr-day' and text()='%s']", day))));
        dayElement.click();
    }

    private void selectTime(WebElement timeInput, String hour, String minute, String timePeriod) {
        wait.until(ExpectedConditions.elementToBeClickable(timeInput)).click();

        WebElement hourInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//input[@class='numInput flatpickr-hour']")));
        hourInput.clear();
        hourInput.sendKeys(hour);

        WebElement minuteInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//input[@class='numInput flatpickr-minute']")));
        minuteInput.clear();
        minuteInput.sendKeys(minute);

        WebElement timePeriodToggle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//span[@class='flatpickr-am-pm']")));

        if (!timePeriodToggle.getText().trim().equalsIgnoreCase(timePeriod)) {
            timePeriodToggle.click();
        }
    }

    public String getFoundCarsCount() {
        return labelCountResult.getText();
    }

    public void searchForAvailableCars(SearchParameters parameters) {
        fillSearchForm(parameters);
        findCarButton.click();
    }

    public void searchWithDifferentReturnLocation(SearchParameters parameters, String returnLocation) {
        chkReturnLocation.click();
        fillSearchForm(parameters);
        selectReturnLocation(returnLocation);
        findCarButton.click();
    }

    private void fillSearchForm(SearchParameters parameters) {
        selectDate(pickupDateInput, parameters.pickupYear, parameters.pickupMonth, parameters.pickupDay);
        selectTime(pickupTimeInput, parameters.pickupHour, parameters.pickupMinute, parameters.pickupTimePeriod);
        selectPickupLocation(parameters.location);
        selectDate(returnDateInput, parameters.returnYear, parameters.returnMonth, parameters.returnDay);
        selectTime(returnTimeInput, parameters.returnHour, parameters.returnMinute, parameters.returnTimePeriod);
    }

    public static class SearchParameters {
        public final String pickupYear;
        public final String pickupMonth;
        public final String pickupDay;
        public final String returnYear;
        public final String returnMonth;
        public final String returnDay;
        public final String location;
        public final String pickupHour;
        public final String pickupMinute;
        public final String pickupTimePeriod;
        public final String returnHour;
        public final String returnMinute;
        public final String returnTimePeriod;

        public SearchParameters(String pickupYear, String pickupMonth, String pickupDay,
                                String returnYear, String returnMonth, String returnDay,
                                String location, String pickupHour, String pickupMinute,
                                String pickupTimePeriod, String returnHour, String returnMinute,
                                String returnTimePeriod) {
            this.pickupYear = pickupYear;
            this.pickupMonth = pickupMonth;
            this.pickupDay = pickupDay;
            this.returnYear = returnYear;
            this.returnMonth = returnMonth;
            this.returnDay = returnDay;
            this.location = location;
            this.pickupHour = pickupHour;
            this.pickupMinute = pickupMinute;
            this.pickupTimePeriod = pickupTimePeriod;
            this.returnHour = returnHour;
            this.returnMinute = returnMinute;
            this.returnTimePeriod = returnTimePeriod;
        }
    }
}