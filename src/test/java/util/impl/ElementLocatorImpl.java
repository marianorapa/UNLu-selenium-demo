package util.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ElementLocator;

import java.util.List;

public class ElementLocatorImpl implements ElementLocator {

    private static final long TIMEOUT = 8;
    private final WebDriver driver;

    public ElementLocatorImpl(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getElementBy(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement getElementClickableBy(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> getElementsBy(By productEntryList) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(productEntryList));
    }
}
