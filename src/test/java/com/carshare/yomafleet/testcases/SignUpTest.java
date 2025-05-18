package com.carshare.yomafleet.testcases;
import com.carshare.yomafleet.base.TestBase;
import com.carshare.yomafleet.pages.HomePage;
import com.carshare.yomafleet.pages.SignUpPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;

public class SignUpTest extends TestBase {
    SignUpPage signUpPage;
    HomePage homePage;

    public SignUpTest() throws FileNotFoundException {
    }

    @BeforeSuite
    public void setUp() {
        Initialization();
        signUpPage = new SignUpPage(driver);
        homePage = new HomePage(driver);
    }

    @BeforeMethod
    public void NavigateToAccountRegisterPage() throws InterruptedException {
        signUpPage.navigateToSignUpPage();
        Thread.sleep(3000);
    }

    @AfterMethod
    public void NavigateToHomePage(){
        driver.navigate().back();
        driver.navigate().back();
    }

    // Individual Account Tests
    @Test(priority = 1, description = "Verify successful individual account registration with valid credentials")
    public void testSuccessfulIndividualAccountRegistration() {
        signUpPage.submitIndividualAccountCreationForm(
                "Test User",
                "testuser@demo.com",
                "ValidPass123!",
                "ValidPass123!"
        );
        // SKIP OTP SUBMISSION STEP
    }

    @Test(priority = 2, description = "Verify error when passwords don't match for individual account")
    public void testIndividualAccountPasswordMismatchError() {
        signUpPage.submitIndividualAccountCreationForm(
                "Test User",
                "test@example.com",
                "Password123!",
                "DifferentPassword123!"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("password_mismatch", ""),
                "Error should be displayed when passwords don't match");
    }

    @Test(priority = 3, description = "Verify error for invalid email formats in individual account")
    public void testIndividualAccountInvalidEmailFormats() {
        signUpPage.submitIndividualAccountCreationForm(
                "Test User",
                "invalidemail",
                "Password123!",
                "Password123!"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("email", ""),
                "Should reject invalid email format");
    }

    @Test(priority = 4, description = "Verify validation messages for empty required fields in individual account")
    public void testIndividualAccountEmptyFieldValidation() {
        signUpPage.clickJoinNow();
        signUpPage.clickSubmit();

        // Verify all required field validations
        signUpPage.verifyIndividualAccountRequiredFields();
    }

    @Test(priority = 5, description = "Verify invalid password format validation in individual account")
    public void testIndividualAccountInvalidPasswordError() {
        signUpPage.submitIndividualAccountCreationForm(
                "Test User",
                "test@example.com",
                "12345",
                "12345"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("password", ""),
                "Error should be displayed when password is too short");
    }

    // Corporate New Account Tests
    @Test(priority = 6, description = "Verify successful new corporate account registration with valid credentials")
    public void testSuccessfulNewCorporateAccountRegistration() {
        signUpPage.submitCorporateNewAccountCreationForm(
                "Test Company",
                "Test User",
                "testuser@company.com",
                "1234567890",
                "Open a new corporate account",
                "Technology",
                "\uD83C\uDDF2\uD83C\uDDF2 MM (+95)",
                "Manager"
        );
        signUpPage.OKButton.click();
    }

    @Test(priority = 7, description = "Verify validation messages for empty required fields in new corporate account")
    public void testNewCorporateAccountEmptyFieldValidation() {
        signUpPage.selectNewCorporateAccountOption();
        signUpPage.clickJoinNowForCorporate();
        signUpPage.clickSubmit();

        // Verify all required field validations
        signUpPage.verifyCorporateAccountRequiredFields();
    }

    @Test(priority = 8, description = "Verify invalid email format in new corporate account registration")
    public void testNewCorporateAccountInvalidEmail() {
        signUpPage.submitCorporateNewAccountCreationForm(
                "Test Company",
                "Test User",
                "invalidemail",
                "1234567890",
                "Open a new corporate account",
                "Technology",
                "\uD83C\uDDF2\uD83C\uDDF2 MM (+95)",
                "Manager"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("email", ""),
                "Should reject invalid email format in new corporate account");
    }

    @Test(priority = 9, description = "Verify invalid phone number format in new corporate account")
    public void testNewCorporateAccountInvalidPhoneNumber() {
        signUpPage.submitCorporateNewAccountCreationForm(
                "Test Company",
                "Test User",
                "testuser@company.com",
                "invalidphone",
                "Open a new corporate account",
                "Technology",
                "\uD83C\uDDF2\uD83C\uDDF2 MM (+95)",
                "Manager"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("phone", ""),
                "Should reject invalid phone number format");
    }

    // Corporate Existing Account Tests
    @Test(priority = 10, description = "Verify successful existing corporate account registration with valid credentials")
    public void testSuccessfulExistingCorporateAccountRegistration() throws InterruptedException {
        signUpPage.submitCorporateExistingAccountCreationForm(
                "Existing Company",
                "Test Employee",
                "employee@company.com",
                "1234567890",
                "Update corporate information",
                "Finance",
                "\uD83C\uDDF2\uD83C\uDDF2 MM (+95)",
                "Accountant"
        );
        Thread.sleep(3000);
        signUpPage.OKButton.click();
    }

    @Test(priority = 11, description = "Verify validation messages for empty required fields in existing corporate account")
    public void testExistingCorporateAccountEmptyFieldValidation() {
        signUpPage.selectExistingCorporateAccountOption();
        signUpPage.clickJoinNowForCorporate();
        signUpPage.clickSubmit();

        // Verify all required field validations
        signUpPage.verifyCorporateAccountRequiredFields();
    }

    @Test(priority = 12, description = "Verify invalid email format in existing corporate account registration")
    public void testExistingCorporateAccountInvalidEmail() {
        signUpPage.submitCorporateExistingAccountCreationForm(
                "Existing Company",
                "Test Employee",
                "invalidemail",
                "1234567890",
                "Add a new member",
                "Finance",
                "\uD83C\uDDF2\uD83C\uDDF2 MM (+95)",
                "Accountant"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("email", ""),
                "Should reject invalid email format in existing corporate account");
    }

    @Test(priority = 13, description = "Verify invalid phone number format in existing corporate account registration")
    public void testExistingCorporateAccountInvalidPhoneNumber() {
        signUpPage.submitCorporateExistingAccountCreationForm(
                "Existing Company",
                "Test Employee",
                "employee@company.com",
                "invalidphone",
                "Add a new member",
                "Finance",
                "\uD83C\uDDF2\uD83C\uDDF2 MM (+95)",
                "Accountant"
        );
        Assert.assertTrue(signUpPage.isValidationErrorDisplayed("phone", ""),
                "Should reject invalid phone number format in existing corporate account");
    }

    @Test(priority = 14, description = "Verify individual account page heading is displayed correctly")
    public void testIndividualSignupPageHeading() {
        signUpPage.clickJoinNow();

        Assert.assertTrue(signUpPage.isIndividualSignupHeadingDisplayed(),
                "Individual account signup page heading should be visible");
        Assert.assertEquals(signUpPage.getIndividualSignupHeadingText(), "Join as Individual",
                "Individual account page heading text should match expected value");
    }

    @Test(priority = 15, description = "Verify corporate new account page heading is displayed correctly")
    public void testCorporateNewAccountHeading() {
        signUpPage.selectNewCorporateAccountOption();
        signUpPage.clickJoinNowForCorporate();

        // Verify heading is displayed and has correct text
        Assert.assertTrue(signUpPage.isCorporateSignupHeadingDisplayed(),
                "Corporate new account signup page heading should be visible");
        Assert.assertEquals(signUpPage.getCorporateSignupHeadingText(), "Yoma Car Share Corporate Account",
                "Corporate new account page heading text should match expected value");
    }

    @Test(priority = 16, description = "Verify corporate existing account page heading is displayed correctly")
    public void testCorporateExistingAccountHeading() {
        signUpPage.selectExistingCorporateAccountOption();
        signUpPage.clickJoinNowForCorporate();

        // Verify heading is displayed and has correct text
        Assert.assertTrue(signUpPage.isCorporateSignupHeadingDisplayed(),
                "Corporate existing account signup page heading should be visible");
        Assert.assertEquals(signUpPage.getCorporateSignupHeadingText(), "Yoma Car Share Corporate Account",
                "Corporate existing account page heading text should match expected value");
    }

    @AfterSuite
    public void tearDown(){
        driver.quit();
    }

}