package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.Constants;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    public static ExtentReports extent;
    public static ExtentTest test;
    public static WebDriver driver;
    public static ExtentTest logger;

    @BeforeTest
    public void beforeTestMethod() {
        // Path where Extent Report will be generated
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";

        // Create Spark reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Automation Test Results");
        sparkReporter.config().setDocumentTitle("Test Execution Report");
        sparkReporter.config().setTheme(Theme.DARK);
        // Initialize ExtentReports and attach reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system/environment details
        extent.setSystemInfo("OS", "MacOS");
        extent.setSystemInfo("Tester", "Nischay");
        extent.setSystemInfo("Browser", "Chrome");
    }

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethodMethod(String browser, Method testMethod) {
        logger = extent.createTest(testMethod.getName());
        setupDriver(browser);
        driver.manage().window().maximize();
        driver.get(Constants.url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }


    @AfterMethod
    public void afterMethodMethod(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.SUCCESS) {
                logger.pass("Test Passed");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                logger.fail("Test Failed: " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SKIP) {
                logger.skip("Test Skipped: " + result.getThrowable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @AfterTest
    public void afterTestMethod() {
        extent.flush();
    }


    public void setupDriver(String browser) {
        if (browser == null) {
            throw new IllegalArgumentException("Browser parameter is null! Please provide a valid browser.");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                driver.manage().window().maximize();
                break;

            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                driver.manage().window().maximize();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().deleteAllCookies();
    }


}
