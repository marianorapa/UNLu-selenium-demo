import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.impl.ThreadSleeperImpl;

import java.io.IOException;
import java.util.List;

/**
 * Clase de partida para comenzar a automatizar con Selenium
 */
public class BaseExample {

    WebDriver driver;

    @BeforeClass
    public void setup() throws IOException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown(){
        ThreadSleeperImpl.sleep(10000);
        driver.close();
    }

}