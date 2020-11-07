package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ElementLocator {

    WebElement getElementBy(By locator);

    WebElement getElementClickableBy(By locator);

    List<WebElement> getElementsBy(By productEntryList);
}
