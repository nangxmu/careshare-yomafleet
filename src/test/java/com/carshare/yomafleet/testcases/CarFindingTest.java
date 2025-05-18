package com.carshare.yomafleet.testcases;
import com.carshare.yomafleet.base.TestBase;
import com.carshare.yomafleet.pages.HomePage;
import org.testng.annotations.*;

import java.io.FileNotFoundException;

public class CarFindingTest extends TestBase {
    private HomePage homePage;
    private static final HomePage.SearchParameters DEFAULT_SEARCH_PARAMS = new HomePage.SearchParameters(
            "2025", "June", "25",
            "2025", "June", "27",
            "Taunggyi Max Energy , Taunggyi",
            "4", "00", "PM",
            "5", "30", "AM"
    );

    public CarFindingTest() throws FileNotFoundException {
    }

    @BeforeSuite
    public void setUp() {
        Initialization();
        homePage = new HomePage(driver);
    }

    @BeforeMethod
    public void NavigateToFindACarPage() throws InterruptedException {
        homePage.findCarButton.click();
        Thread.sleep(3000);
    }

    @AfterMethod
    public void NavigateToHomePage(){
        driver.navigate().back();
    }


    @Test(priority = 1, description = "Verify user can navigate to Location Directory pag")
    public void verifyNavigationToLocationDirectory() throws InterruptedException {
        homePage.navigateToLocationDirectory();
    }

    @Test(priority = 2, description = "Verify basic car search functionality with valid parameters")
    public void verifyCarSearchFunctionality() {
        homePage.searchForAvailableCars(DEFAULT_SEARCH_PARAMS);
        System.out.println("Found " + homePage.getFoundCarsCount() + " cars");
    }

    @Test(priority = 3, description = "Verify user can search for cars with different return location")
    public void verifyReturnToDifferentLocation() {
        String returnLocation = "Times City Yangon , Kamayut Township";
        homePage.searchWithDifferentReturnLocation(DEFAULT_SEARCH_PARAMS, returnLocation);

        String count = homePage.getFoundCarsCount();
        System.out.println("Found " + count + " cars when returning to " + returnLocation);
    }

    @Test(priority = 4, description = "Verify validation messages appear when required fields are missing")
    public void verifyRequiredFieldValidations() {
        homePage.verifyRequiredFieldsValidationMessages();
    }

    @AfterSuite
    public void tearDown(){
        driver.quit();
    }
}