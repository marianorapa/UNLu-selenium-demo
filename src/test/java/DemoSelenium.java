import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import util.ElementLocator;
import util.impl.ElementLocatorImpl;
import util.impl.ThreadSleeperImpl;

public class DemoSelenium {

    WebDriver driver;

    ElementLocator elementLocator;

    private final static String BASE_URL = "https://www.google.com";


    @BeforeClass
    public void setup() {
        // Setear el path del driver
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");

        // Instanciar y maximizar la ventana
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Instanciar helpers
        elementLocator = new ElementLocatorImpl(driver);

    }

    @Test
    public void navigateToUrl(){
        driver.get(BASE_URL);
        ThreadSleeperImpl.sleep(5000);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }

}
