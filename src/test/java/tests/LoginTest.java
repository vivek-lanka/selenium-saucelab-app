package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;
import utils.DriverFactory;
import utils.ScreenshotListener;

@Listeners({ScreenshotListener.class})
public class LoginTest {

    public static WebDriver driver;
    public static LoginPage loginPage;
    public static InventoryPage inventoryPage;
    public static String baseUrl;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        baseUrl = Config.URL;
        driver.get(baseUrl);
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get(baseUrl);
    }

    @AfterClass( alwaysRun = true)
    public void tearDown(){
        DriverFactory.closeDriver();
    }

    @Test(priority = 0)
    public void validate_valid_user_login() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        loginPage.login(Config.STANDARD_USER, Config.PASSWORD);
        inventoryPage.verifyUserOnInventoryPage();
        inventoryPage.logout();
    }

    @Test(priority = 1)
    public void validate_locked_out_user_login_failure() {
        loginPage = new LoginPage(driver);
        loginPage.login(Config.LOCKED_USER, Config.PASSWORD);
        loginPage.hasLockedOutError();
    }

    @Test(priority = 2)
    public void validate_invalid_password_login_failure() {
        loginPage = new LoginPage(driver);
        loginPage.login(Config.STANDARD_USER, "random");
        loginPage.hasUsernamePasswordError();
    }

    @Test(priority = 3)
    public void validate_empty_field_login_failure() {
        loginPage = new LoginPage(driver);
        loginPage.clickLogin();
        loginPage.hasEmptyFieldsError();
    }

}
