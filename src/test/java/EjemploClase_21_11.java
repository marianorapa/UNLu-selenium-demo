import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ElementLocator;
import util.impl.ElementLocatorImpl;
import util.impl.ThreadSleeperImpl;

import java.util.List;

public class EjemploClase_21_11 {

    WebDriver driver;

    ElementLocator elementLocator;

    private final static String BASE_URL = "https://www.duckduckgo.com";

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
    public void navigateToUrl(){
        driver.get(BASE_URL);
        ThreadSleeperImpl.sleep(1000);
    }

    @Test(priority = 20)
    public void efectuarBusqueda(){
        // Capturar elemento
        WebElement input = driver.findElement(By.id("search_form_input_homepage"));

        // Enviar texto
        input.sendKeys("Bicicleta rodado 29 mercadolibre");
        input.sendKeys("\n");
    }

    @Test(priority = 30)
    public void seleccionarPrimerElemento(){
        List<WebElement> resultados = driver.findElements(By.className("result"));

        resultados.get(0).click();
    }

    @Test(priority = 40)
    public void seleccionarElemento(){
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root-app\"]/div/div[1]/section/ol/li[1]/div/div/div[2]/div[2]/a[1]"));

        element.click();
    }

    @Test(priority = 50)
    public void hacerPregunta(){
        WebElement input = elementLocator.getElementBy(By.name("question"));

        input.sendKeys("Hola, hay stock?");

        WebElement btn = elementLocator.getElementBy(By.cssSelector("#questions > form > button"));

        btn.click();
    }


    @AfterClass
    public void tearDown(){
        ThreadSleeperImpl.sleep(2000);
        driver.close();
    }

}
