import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ElementLocator;
import util.impl.ElementLocatorImpl;

import java.util.List;
import java.util.Optional;

public class Ejemplo_Clase_22042022 {

    WebDriver driver;

    ElementLocator elementLocator;

    private final static String BASE_URL = "http://www.unlu.edu.ar";

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
    public void testFinalExams() {

        driver.get(BASE_URL);

        WebElement academicServices = elementLocator.getElementBy(By.xpath("//*[@id=\"cuerpoder\"]/a[4]"));

        academicServices.click();

        WebElement generalAccess = elementLocator.getElementBy(By.xpath("//*[@id=\"sec-a1-caja\"]/div[2]/p[3]/a"));

        generalAccess.click();

        // Locate final exams link through featured table
        WebElement featuredTable = elementLocator.getElementBy(By.xpath("//*[@id=\"table2\"]/tbody/tr[3]/td/div/table"));

        List<WebElement> tableRows = featuredTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        navigateToFinalExamsQuery(tableRows);

    }

    private void navigateToFinalExamsQuery(List<WebElement> tableRows) {
        for (WebElement row: tableRows) {
            Optional<WebElement> innerAnchor = getInnerRowAnchor(row);
            if (innerAnchor.isPresent()) {
                WebElement finalsAnchor = innerAnchor.get();
                if (finalsAnchor.getText().equals("Fechas de Exámenes Finales")) {
                    driver.get(finalsAnchor.getAttribute("href"));
                }
            }
        }
    }

    private Optional<WebElement> getInnerRowAnchor(WebElement row) {
        try {
            WebElement innerElement = row.findElement(By.tagName("td")).findElement(By.tagName("p")).findElement(By.tagName("b")).findElement(By.tagName("a"));
            return Optional.of(innerElement);
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }




}
