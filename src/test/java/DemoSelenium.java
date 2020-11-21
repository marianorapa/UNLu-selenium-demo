import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import util.ElementLocator;
import util.impl.ElementLocatorImpl;
import util.impl.ThreadSleeperImpl;

import java.util.List;


public class DemoSelenium {

    WebDriver driver;

    ElementLocator elementLocator;

    public static final String DUCKDUCKGO_URL = "https://www.duckduckgo.com";

    public static final int FORCED_DELAY = 500;

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

    @Test(priority = 10)
    public void busquedaEnAlgunEngine() throws InterruptedException {

        // Cargar la página
        driver.get(DUCKDUCKGO_URL);
        ThreadSleeperImpl.sleep(FORCED_DELAY);
        // Ubicar el input de búsqueda y enviar mensaje "bicicleta"
        WebElement inputBox = driver.findElement(By.id("search_form_input_homepage"));
        // Si falla, utilizar webDriverWait
//        WebElement inputBox = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("search_form_input_homepage")));

        inputBox.sendKeys("Bicicleta rodado 29 mercadolibre");
        ThreadSleeperImpl.sleep(FORCED_DELAY);

        inputBox.sendKeys("\n");

        // Obtener todos los resultados
        List<WebElement> elements = driver.findElements(By.className("result"));
        ThreadSleeperImpl.sleep(FORCED_DELAY);

        // Clickear el primero
        elements.get(0).click();

        // Esperar presence of element
        WebElement result = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("ui-search-item__title")));
        ThreadSleeperImpl.sleep(FORCED_DELAY);

        // Utilizar Actions para clickear el elemento
        Actions actions = new Actions(driver);
        actions.moveToElement(result).click().build().perform();

        // Obtener text area pregunta
        WebElement questionBox = driver.findElement(By.xpath("//*[@id=\"questions\"]/form/div/label/div[1]/textarea"));
        ThreadSleeperImpl.sleep(FORCED_DELAY);

        questionBox.sendKeys("Hola, hay stock?");
        ThreadSleeperImpl.sleep(FORCED_DELAY);

        // Obtener botón "preguntar"
        WebElement btnPreguntar = driver.findElement(By.xpath("//*[@id=\"questions\"]/form/button"));
        btnPreguntar.click();
    }


    @AfterClass
    public void tearDown(){
        ThreadSleeperImpl.sleep(2000);
        driver.close();
    }

}
