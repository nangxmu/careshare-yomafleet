package com.carshare.yomafleet.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static com.carshare.yomafleet.base.TestBase.driver;

public class SignUpPage {
    private final WebDriverWait wait;

    // Navigation elements
    @FindBy(xpath = "(//a[@href=\"#\" and contains(@class, 'text-textBlack') and text()='Sign Up'])[2]")
    private WebElement signUpLink;

    @FindBy(xpath = "(//a[@href=\"#\" and contains(@class, 'text-textBlack') and text()='Sign In'])[2]")
    private WebElement signInLink;

    // Individual sign up form elements
    @FindBy(xpath = "//button[@type='button' and normalize-space(text())='Join Now']")
    private WebElement joinNowButton;

    @FindBy(id = "fullName")
    private WebElement fullNameInput;

    @FindBy(xpath = "//input[@type = 'email' and @placeholder = 'Enter Email']")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'p-2') and text()='SUBMIT']")
    private WebElement submitButton;

    @FindBy(xpath = "//h2[@class = 'text-center font-bold font-source-serif leading-tight text-2xl mb-7 dark:text-dOnBg']")
    private WebElement indSignupHeading;

    // Corporate registration elements
    @FindBy(xpath = "//h1[contains(@class, 'font-serif') and contains(@class, 'font-bold') and contains(@class, 'leading-tight')]")
    private WebElement corpSignupHeading;

    @FindBy(xpath = "//label[.//span[normalize-space(text())='I want to create a new account for my company']]//input[@type='checkbox']")
    private WebElement newAccountCheckbox;

    @FindBy(xpath = "//label[.//span[normalize-space(text())='I want to join my company existing account']]//input[@type='checkbox']")
    private WebElement existingAccountCheckbox;

    @FindBy(xpath = "//h2[contains(@class, 'prose') and contains(text(), 'Join as a Corporate')]/following::button[contains(text(), 'Join Now')]")
    private WebElement corpJoinNowButton;

    @FindBy(id = "job_title")
    private WebElement inputJobTitle;

    @FindBy(id = "company_name")
    private WebElement inputCompanyName;

    @FindBy(id = "contact_person")
    private WebElement inputContactPerson;

    @FindBy(id = "national_number")
    private WebElement inputPhoneNumber;

    @FindBy(id = "react-select-type-input")
    private WebElement selectTypeInquiry;

    @FindBy(id = "react-select-industry-input")
    private WebElement selectIndustry;

    @FindBy(id = "react-select-country_code-input")
    private WebElement selectCountryCode;

    @FindBy(xpath = "//button[text()='OK' and contains(@class, 'swal-button--confirm')]")
    public WebElement OKButton;

    // Common error message patterns
    private static final String REQUIRED_INPUT_FIELD_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='Please enter %s.']";
    private static final String REQUIRED_SELECT_FIELD_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='Please select %s.']";
    private static final String COUNTRY_CODE_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='The ext field is required.']";
    private static final String INVALID_EMAIL_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='Please enter valid email address.']";
    private static final String INVALID_PHONE_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='Please enter valid phone number.']";
    private static final String INVALID_PASSWORD_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(.)='The password must be at least 8 characters.']";
    private static final String PASSWORD_MISMATCH_ERROR = "//label[contains(@class, 'text-red-500') and normalize-space(text())=\"The password doesn't match.\"]";

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Common validation methods
    public boolean isValidationErrorDisplayed(String errorType, String fieldName) {
        String xpath;
        switch (errorType.toLowerCase()) {
            case "required_input":
                xpath = String.format(REQUIRED_INPUT_FIELD_ERROR, fieldName);
                break;
            case "required_select":
                xpath = String.format(REQUIRED_SELECT_FIELD_ERROR, fieldName);
                break;
            case "country_code":
                xpath = COUNTRY_CODE_ERROR;
                break;
            case "email":
                xpath = INVALID_EMAIL_ERROR;
                break;
            case "phone":
                xpath = INVALID_PHONE_ERROR;
                break;
            case "password":
                xpath = INVALID_PASSWORD_ERROR;
                break;
            case "password_mismatch":
                xpath = PASSWORD_MISMATCH_ERROR;
                break;
            default:
                throw new IllegalArgumentException("Unknown error type: " + errorType);
        }

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Add these helper methods for individual account validation
    public void verifyIndividualAccountRequiredFields() {
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "your full name"),
                "Validation message should appear for empty full name");
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "your email"),
                "Validation message should appear for empty email");
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "your password"),
                "Validation message should appear for empty password");
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "your confirm password"),
                "Validation message should appear for empty confirm password");
    }

    // Add these helper methods for corporate account validation
    public void verifyCorporateAccountRequiredFields() {
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "company name"),
                "Validation message should appear for empty company name");
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "contact person"),
                "Validation message should appear for empty contact person");
        Assert.assertTrue(isValidationErrorDisplayed("country_code", ""),
                "Validation message should appear for unselected country code");
        Assert.assertTrue(isValidationErrorDisplayed("required_select", "type of inquiry"),
                "Validation message should appear for unselected type of inquiry");
        Assert.assertTrue(isValidationErrorDisplayed("required_select", "Industry"),
                "Validation message should appear for unselected industry");
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "your email"),
                "Validation message should appear for empty email");
        Assert.assertTrue(isValidationErrorDisplayed("required_input", "valid phone number"),
                "Validation message should appear for empty phone number");
    }

    // Individual account methods
    public void submitIndividualAccountCreationForm(String fullName, String email, String password, String confirmPassword) {
        //navigateToSignUpPage();
        clickJoinNow();
        enterFullName(fullName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        clickSubmit();
    }

    public void navigateToSignUpPage() {
        signUpLink.click();
        wait.until(ExpectedConditions.visibilityOf(joinNowButton));
    }

    public void clickJoinNow() {
        joinNowButton.click();
        wait.until(ExpectedConditions.visibilityOf(fullNameInput));
    }

    public void enterFullName(String fullName) {
        fullNameInput.clear();
        fullNameInput.sendKeys(fullName);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public String getIndividualSignupHeadingText() {
        return indSignupHeading.getText();
    }

    public boolean isIndividualSignupHeadingDisplayed() {
        try {
            return indSignupHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // Corporate account methods
    public void selectNewCorporateAccountOption() {
        newAccountCheckbox.click();
    }

    public void selectExistingCorporateAccountOption() {
        existingAccountCheckbox.click();
    }

    public void clickJoinNowForCorporate() {
        corpJoinNowButton.click();
    }

    public String getCorporateSignupHeadingText() {
        return corpSignupHeading.getText();
    }

    public boolean isCorporateSignupHeadingDisplayed() {
        try {
            return corpSignupHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void submitCorporateNewAccountCreationForm(String companyName, String contactPerson, String email,
                                                      String phoneNumber, String type, String industry, String countryCode, String jobTitle) {
        selectNewCorporateAccountOption();
        clickJoinNowForCorporate();
        enterCompanyName(companyName);
        enterContactPerson(contactPerson);
        enterEmail(email);
        enterPhoneNumber(phoneNumber);
        selectInquiryType(type);
        selectIndustry(industry);
        enterJobTitle(jobTitle);
        selectCountryCode(countryCode);
        clickSubmit();
    }

    public void submitCorporateExistingAccountCreationForm(String companyName, String contactPerson, String email,
                                                           String phoneNumber, String type, String industry, String countryCode, String jobTitle) {
        selectExistingCorporateAccountOption();
        clickJoinNowForCorporate();
        enterCompanyName(companyName);
        enterContactPerson(contactPerson);
        enterEmail(email);
        enterPhoneNumber(phoneNumber);
        selectInquiryType(type);
        selectIndustry(industry);
        enterJobTitle(jobTitle);
        selectCountryCode(countryCode);
        clickSubmit();
    }

    public void enterCompanyName(String companyName) {
        inputCompanyName.clear();
        inputCompanyName.sendKeys(companyName);
    }

    public void enterContactPerson(String contactPerson) {
        inputContactPerson.clear();
        inputContactPerson.sendKeys(contactPerson);
    }

    public void enterPhoneNumber(String phoneNumber) {
        inputPhoneNumber.clear();
        inputPhoneNumber.sendKeys(phoneNumber);
    }

    public void enterJobTitle(String jobTitle) {
        inputJobTitle.clear();
        inputJobTitle.sendKeys(jobTitle);
    }

    private void selectOption(String option) {
        WebElement value = driver.findElement(By.xpath("//div[contains(@class, 'select__option') and normalize-space(text())='"+option+"']"));
        value.click();
    }

    public void selectInquiryType(String type) {
        selectTypeInquiry.click();
        selectOption(type);
    }

    public void selectIndustry(String industry) {
        selectIndustry.click();
        selectOption(industry);
    }

    public void selectCountryCode(String code) {
        selectCountryCode.click();
        selectOption(code);
    }
}