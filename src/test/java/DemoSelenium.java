import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import util.ElementLocator;
import util.impl.ElementLocatorImpl;
import util.impl.ThreadSleeperImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DemoSelenium {

    WebDriver driver;

    ElementLocator elementLocator;

    public static final String URL = "https://www.geeksforgeeks.org";

    public static final int FORCED_DELAY = 500;

    private String username;
    private String password;

    @BeforeClass
    public void setup() {
        // Setear el path del driver
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");

        // Instanciar y maximizar la ventana
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Instanciar helpers
        elementLocator = new ElementLocatorImpl(driver);

        // Leer config
        readConfigValues();
    }

    private void readConfigValues() {
        Properties props = new Properties();
        String propFileName = "src/test/resources/application.properties";

        try (InputStream inputStream = new FileInputStream(propFileName)) {
            props.load(inputStream);
            this.username = props.getProperty("username");
            this.password = props.getProperty("password");
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }


    @Test(priority = 10)
    public void login(){
        driver.get(URL);

        WebElement signInBtn = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userProfileId\"]/a")));

        signInBtn.click();

        WebElement loginInput = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"luser\"]")));

        loginInput.click();

        loginInput.sendKeys(this.username);

        WebElement pwdInput = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[3]/div/div[2]/div[1]/section[1]/form/div[3]/input")));

        pwdInput.sendKeys(this.password);

        WebElement actualSignInBtn = driver.findElement(By.xpath("//*[@id=\"Login\"]/button"));
        actualSignInBtn.click();
    }

    @Test(priority = 20)
    public void skipEncuestaSiAparece(){
        try {
            WebElement element = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div[2]/form/div[10]/button[2]")));
            element.click();
        }
        catch (Exception e){
            System.out.println("La encuesta no fue salteada.");
        }
    }

    @Test(priority = 30)
    public void pantallaInicioPostLogin(){
        WebElement homeLogo = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/a[2]")));

        Assert.assertTrue(homeLogo.isDisplayed());
    }


    @AfterClass
    public void tearDown(){
        ThreadSleeperImpl.sleep(10000);
        driver.close();
    }
}