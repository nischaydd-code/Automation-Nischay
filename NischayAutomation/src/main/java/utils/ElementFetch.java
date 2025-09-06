package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementFetch {

    private WebDriver driver;

    public ElementFetch(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getWebElement(String identifierType, String identifierValue) {
        switch (identifierType.toLowerCase()) {
            case "id":
                return driver.findElement(By.id(identifierValue));

            case "name":
                return driver.findElement(By.name(identifierValue));

            case "classname":
            case "class":
                return driver.findElement(By.className(identifierValue));

            case "xpath":
                return driver.findElement(By.xpath(identifierValue));

            case "css":
            case "cssselector":
                return driver.findElement(By.cssSelector(identifierValue));

            case "linktext":
                return driver.findElement(By.linkText(identifierValue));

            case "partiallinktext":
                return driver.findElement(By.partialLinkText(identifierValue));

            case "tagname":
                return driver.findElement(By.tagName(identifierValue));

            default:
                throw new IllegalArgumentException("Invalid identifier type: " + identifierType);
        }
    }


    public List<WebElement> getWebElements(String identifierType, String identifierValue) {
        switch (identifierType.toLowerCase()) {
            case "id":
                return driver.findElements(By.id(identifierValue));
            case "name":
                return driver.findElements(By.name(identifierValue));
            case "classname":
            case "class":
                return driver.findElements(By.className(identifierValue));
            case "xpath":
                return driver.findElements(By.xpath(identifierValue));
            case "css":
            case "cssselector":
                return driver.findElements(By.cssSelector(identifierValue));
            case "linktext":
                return driver.findElements(By.linkText(identifierValue));
            case "partiallinktext":
                return driver.findElements(By.partialLinkText(identifierValue));
            case "tagname":
                return driver.findElements(By.tagName(identifierValue));
            default:
                throw new IllegalArgumentException("Invalid identifier type: " + identifierType);
        }
    }
}
