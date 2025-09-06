package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SuiteListener implements ITestListener, IAnnotationTransformer {


    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;

        try {
            // Get driver from test instance
            Object testClass = result.getInstance();
            driver = (WebDriver) testClass.getClass().getField("driver").get(testClass);

            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/";
            Files.createDirectories(Paths.get(screenshotDir));

            String screenshotPath = screenshotDir + result.getName() + ".png";
            Files.copy(srcFile.toPath(), Paths.get(screenshotPath));

            // Get logger from test instance
            ExtentTest logger = (ExtentTest) testClass.getClass().getField("logger").get(testClass);

            if (logger != null) {
                logger.fail("Test Failed. Screenshot attached.",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
