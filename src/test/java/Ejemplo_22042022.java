import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ElementLocator;
import util.impl.ElementLocatorImpl;
import util.impl.ThreadSleeperImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class Ejemplo_22042022 {

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
    public void queryFinalsDates() {
        driver.get(BASE_URL);

        // Find academic services button
        WebElement academicServices = elementLocator.getElementBy(By.xpath("//*[@id=\"cuerpoder\"]/a[4]"));

        academicServices.click();

        // Find general access section
        WebElement generalAccess = elementLocator.getElementBy(By.xpath("//*[@id=\"sec-a1-caja\"]/div[2]/p[3]/a"));

        generalAccess.click();

        // Locate final exams link through featured table
        WebElement featuredTable = elementLocator.getElementBy(By.xpath("//*[@id=\"table2\"]/tbody/tr[3]/td/div/table"));

        List<WebElement> tableRows = featuredTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        navigateToFinalExamsQuery(tableRows);

        // Input query
        WebElement inputAssignment = elementLocator.getElementBy(By.xpath("//*[@id=\"principal\"]/div/center/table/tbody/tr/td/table[1]/tbody/tr[2]/td/form/table/tbody/tr/td/table/tbody/tr[4]/td[2]/p/input"));
        inputAssignment.sendKeys("Programacion I");

        WebElement searchByAssignmentButton = elementLocator.getElementBy(By.xpath("//*[@id=\"principal\"]/div/center/table/tbody/tr/td/table[1]/tbody/tr[2]/td/form/table/tbody/tr/td/table/tbody/tr[4]/td[3]/p/input"));
        searchByAssignmentButton.click();

        // Find assignment selector
        WebElement selector = elementLocator.getElementBy(By.xpath("//*[@id=\"principal\"]/div/center/table/tbody/tr/td/form/center/table/tbody/tr/td/table/tbody/tr[4]/td[2]/select"));

        List<WebElement> options = selector.findElements(By.tagName("option"));

        // Match assignment with code 11074
        Optional<WebElement> assignment = options.stream().filter(option -> option.getText().contains("11074")).findFirst();
        assignment.orElseThrow(RuntimeException::new).click();

        WebElement searchButton = elementLocator.getElementBy(By.xpath("//*[@id=\"principal\"]/div/center/table/tbody/tr/td/form/center/table/tbody/tr/td/table/tbody/tr[4]/td[3]/p/input"));
        searchButton.click();

        WebElement tableFinalDates = elementLocator.getElementBy(By.xpath("//*[@id=\"principal\"]/div/center/table/tbody/tr/td/table[2]"));
        List<WebElement> finalInstances = tableFinalDates.findElements(By.tagName("tr"));

        int examCount = 0;

        for (WebElement finalInstance : finalInstances) {
            try {
                WebElement firstTd = finalInstance.findElements(By.tagName("td")).get(0);
                String possibleDate = firstTd.getText();
                if (LocalDate.parse(possibleDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")).isAfter(LocalDate.now())) {
                    examCount += 1;
                }
            }
            catch (IndexOutOfBoundsException | DateTimeParseException ignored) {}
        }

        Assert.assertEquals( examCount, 2);

        driver.close();
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
