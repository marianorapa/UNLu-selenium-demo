## Introducción a tests funcionales con Selenium

Clonar el repositorio

    git clone https://github.com/marianorapa/UNLu-selenium-demo
    
Abrir el proyecto en el IDE de preferencia y ejecutar los tests.


### Armado de entorno
Un proyecto de tests funcionales con Selenium en Java no tiene una única estructura. En este caso, sin embargo, creamos un proyecto Maven de cero e incluimos las dependencias a Selenium API, Selenium ChromeDriver y Selenium Support.

Dado que Selenium WebDriver nos sirve para automatizar la interacción con un browser, necesitamos incorporar un framework de pruebas para poder crear tests. Es por ello que, dentro del POM, agregamos también la dependencia a TestNG. 

#### Estructura del proyecto 
A partir del proyecto Maven inicial y las dependencias mencionadas, incorporamos algunos archivos que se describen a continuación:

##### Suite de pruebas
Dentro de la raíz del proyecto encontramos el archivo ```testng-suite.xml```:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Default Suite">
    <test name="selenium-demo">
        <classes>
            <class name="DemoSelenium"/>
        </classes>
    </test> <!-- selenium-ejercicios -->
</suite> <!-- Default Suite -->
```
Se trata de un archivo descriptor que representa una suite de pruebas. Una suite es un conjunto de tests unitarios, donde cada uno de estos está representado por una clase o más. La idea es dar coherencia al conjunto de pruebas y organizarlas en un archivo xml para su posterior ejecución.  


##### Clase de tests
Dentro de ```src/test/java``` creamos la clase ```DemoSelenium``` con los siguientes métodos:

###### @BeforeClass
```
    @BeforeClass
    public void setup() {
        // Setear el path del driver
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");

        // Instanciar y maximizar la ventana
        driver = new ChromeDriver();
      ...
    }
```
- La anotación ```@BeforeClass``` es utilizada para indicarle a TestNG que el método debe ser ejecutado previo al resto de los tests. Definiremos en él las configuraciones previas tales como la instanciación de componentes. 
- La definición de la propiedad ```webdriver.chrome.driver``` se utiliza para indicarle a Selenium la ubicación del driver ejecutable que queremos que utilice. Recordemos que Selenium nos provee una API, pero que cada browser (siguiendo la especificación de W3) implementa la interfaz y distribuye el ejecutable. 

###### @Test
 ```
    @Test
    public void navigateToUrl(){
        driver.get(BASE_URL);
        ...
    }
```
- La anotación ```@Test``` se utiliza para definir cada test a ejecutar por el framework. 
- El resultado del test (*success* o *fail*) dependerá del contenido y la ejecución del mismo. Pueden utilizarse *asserts*, pero si el test no arroja excepciones, será considerado exitoso. Por el contrario, si se produce cualquier tipo de excepción (propia a Selenium o no), será un *fail*.

###### @AfterClass
```
    @AfterClass
    public void tearDown(){
        driver.close();
    }
```
- La anotación ```@AfterClass``` genera que TestNG ejecute el método previo a la finalización de la clase. Por ello, lo utilizamos para volver atrás las modificaciones generadas por los tests. En este caso, simplemente, cerramos el driver iniciado previamente.

#### Utils: ElementLocator
Hemos incorporado, adicionalmente, una clase que nos asiste en la localización de elementos del DOM. El objetivo es abstraernos, en parte, de las clases puntuales de Selenium que nos ayudan a obtener los componentes de una página HTML. 

```
public class ElementLocatorImpl implements ElementLocator {
    ...    
     public WebElement getElementBy(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }    
    ...
}
```
- En el fragmento previo de la implementación de ```ElementLocator``` se observa el método ```getElementBy()``` que devuelve un ```WebElement``` a partir de un ```By```. Se utiliza, para ello, un ```WebDriverWait``` junto a ```ExpectedConditions``` para devolver el elemento cuando esté presente en el documento.

### Interactuando con el DOM
Para poder probar una aplicación web mediante tests funcionales, nos interesa, de manera simplificada y a grandes rasgos, poder realizar tres acciones:
- Navegar a una URL
- Obtener un elemento del DOM
- Interactuar directamente con el elemento: clickearlo si es un botón o enlace, enviar texto si es un input, etc.

**Aclaración**: la interfaz WebDriver es la que abstrae el manejo del browser a través de, en este caso, ChromeDriver. Por lo tanto, será la que utilizaremos para poder navegar, obtener elementos, interactuar con ellos, etc. En todas las clases de tests tendremos el atributo ``WebDriver driver;`` que represente el manejador.

#### Navegar a una URL
``
driver.get("https://www.example.com");
``

#### Localizar un elemento
Podemos obtener el elemento directamente o mediante esperas dinámicas. Dado que hay demoras variables en la descarga de los elementos, puede que intentemos acceder a un componente que todavía no está disponible, lo cual arrojará una excepción haciendo que el test falle. 

```
WebElement button_classname = driver.findElement(By.className("btn-primary"));

WebElement button_tagname = driver.findElement(By.tagName("button"));

WebElement button_xpath = driver.findElement(By.className("//*[@id="submit-file"]"));

WebElement button_linktext = driver.findElement(By.className("Commit changes"));
```
El parámetro que espera recibir ``findElement()`` es el que indica cómo ubicar el elemento. Para eso, existen varias maneras:
- tagName: a partir del nombre de la etiqueta: h1, h2, p, etc.
- className: a partir de la clase css del elemento.
- cssSelector: el selector css. Puede obtenerse con botón derecho sobre el elemento en el inspector del browser
- xpath: el xpath del elemento. Puede obtenerse con botón derecho sobre el elemento en el inspector del browser
- linktext: a partir del texto presente en el enlace. 

##### Esperas dinámicas
Como mencionamos, puede que el elemento no se encuentre aún visible en el DOM, por lo que clickearlo, por ejemplo, generaría una excepción.

Selenium nos provee las interfaces ``WebDriverWait`` y ``ExpectedConditions`` para *esperar* a que se cumpla determinada condición al intentar recuperar un elemento. 

```
WebElement element_present = new WebDriverWait(driver, TIMEOUT)
        .until(ExpectedConditions
                    .presenceOfElementLocated(By.id("my-element"));
```
Tenemos otras alternativas para la espera; por ejemplo, que el elemento sea clickeable mediante `ExpectedConditions.elementToBeClickable();`
