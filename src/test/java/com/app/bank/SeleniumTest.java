package com.app.bank;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {

    @LocalServerPort
    private int localPort;

    private String serverUrl;
    private static WebDriver driver;

    @BeforeAll
    public static void init() {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void checkPageNameHelper(String name) {
        assertEquals(name, driver.getTitle());
        System.out.println("Navigated to correct webpage");
    }

    public void checkHomePage() throws InterruptedException {

        this.checkPageNameHelper("Home Page");

        Thread.sleep(3000);  // Let the user actually see something!
    }

    @Test
    @Order(1)
    public void addNewClientFlowTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get("http://localhost:" + localPort);

        this.checkHomePage();

        By searchButton = By.id("new_client");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedTitle2 = "New client";
        this.checkPageNameHelper(expectedTitle2);

        String cnp = "2980626350073";
        By searchInput1 = By.name("CNP");
        wait.until(presenceOfElementLocated(searchInput1));
        driver.findElement(searchInput1).sendKeys(cnp);

        Thread.sleep(3000);  // Let the user actually see something!

        String name = "Alexandra Veres";
        By searchInput2 = By.name("clientName");
        wait.until(presenceOfElementLocated(searchInput2));
        driver.findElement(searchInput2).sendKeys(name);

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedTitle3 = "Clients list";
        this.checkPageNameHelper(expectedTitle3);

        Thread.sleep(3000);  // Let the user actually see something!

        String innerText = driver.findElement(By.id("client-list")).getText();

        String expectedClientList = "CNP Client name Balance RON Balance EURO\n" +
                                    "2980626350073 Alexandra Veres 0.0 0.0";
        assertEquals(expectedClientList, innerText);
    }

    @Test
    @Order(2)
    public void addNewClientWithEmptyNameFlowTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get("http://localhost:" + localPort);

        this.checkHomePage();

        By searchButton = By.id("new_client");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedTitle2 = "New client";
        this.checkPageNameHelper(expectedTitle2);

        String cnp = "2980626350073";
        By searchInput1 = By.name("CNP");
        wait.until(presenceOfElementLocated(searchInput1));
        driver.findElement(searchInput1).sendKeys(cnp);

        Thread.sleep(3000);  // Let the user actually see something!

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedTitle3 = "Client error";
        this.checkPageNameHelper(expectedTitle3);

        Thread.sleep(3000);  // Let the user actually see something!

        String text = driver.findElement(By.id("error")).getText();

        String expectedErrorText = "The client account you tried to add had invalid credentials! Please try again.";
        assertEquals(expectedErrorText, text);
    }

    @Test
    @Order(3)
    public void addNewClientWithEmptyCNPFlowTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get("http://localhost:" + localPort);

        this.checkHomePage();

        By searchButton = By.id("new_client");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedTitle2 = "New client";
        this.checkPageNameHelper(expectedTitle2);

        String name = "Alexandra Veres";
        By searchInput2 = By.name("clientName");
        wait.until(presenceOfElementLocated(searchInput2));
        driver.findElement(searchInput2).sendKeys(name);

        Thread.sleep(3000);  // Let the user actually see something!

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedTitle3 = "Client error";
        this.checkPageNameHelper(expectedTitle3);

        Thread.sleep(3000);  // Let the user actually see something!

        String text = driver.findElement(By.id("error")).getText();

        String expectedErrorText = "The client account you tried to add had invalid credentials! Please try again.";
        assertEquals(expectedErrorText, text);
    }

    @Test
    @Order(4)
    public void depositEuroFlowTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get("http://localhost:" + localPort);

        this.checkHomePage();

        By searchButton = By.id("deposit");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedPageTitle2 = "Deposit money";
        this.checkPageNameHelper(expectedPageTitle2);

        String cnp = "2980626350073";
        By searchInput1 = By.id("CNP");
        wait.until(presenceOfElementLocated(searchInput1));
        driver.findElement(searchInput1).sendKeys(cnp);

        Thread.sleep(3000);  // Let the user actually see something!

        Select dropdownCurrency = new Select(driver.findElement(By.name("currency")));
        dropdownCurrency.selectByVisibleText("EURO");

        int depositMoney = 100;
        By searchInput2 = By.id("sum");
        wait.until(presenceOfElementLocated(searchInput2));
        driver.findElement(searchInput2).sendKeys("" + depositMoney);

        Thread.sleep(3000);  // Let the user actually see something!

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedPageTitle3 = "Clients list";
        this.checkPageNameHelper(expectedPageTitle3);

        Thread.sleep(3000);  // Let the user actually see something!

        String innerText = driver.findElement(By.id("client-list")).getText();

        String expectedClientList = "CNP Client name Balance RON Balance EURO\n" +
                                    "2980626350073 Alexandra Veres 0.0 100.0";
        assertEquals(expectedClientList, innerText);
    }

    @Test
    @Order(5)
    public void depositRonFlowTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get("http://localhost:" + localPort);

        this.checkHomePage();

        By searchButton = By.id("deposit");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedPageTitle2 = "Deposit money";
        this.checkPageNameHelper(expectedPageTitle2);

        String cnp = "2980626350073";
        By searchInput1 = By.id("CNP");
        wait.until(presenceOfElementLocated(searchInput1));
        driver.findElement(searchInput1).sendKeys(cnp);

        Thread.sleep(3000);  // Let the user actually see something!

        Select dropdownCurrency = new Select(driver.findElement(By.name("currency")));
        dropdownCurrency.selectByVisibleText("RON");

        int depositMoney = 9900;
        By searchInput2 = By.id("sum");
        wait.until(presenceOfElementLocated(searchInput2));
        driver.findElement(searchInput2).sendKeys("" + depositMoney);

        Thread.sleep(3000);  // Let the user actually see something!

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedPageTitle3 = "Clients list";
        this.checkPageNameHelper(expectedPageTitle3);

        Thread.sleep(3000);  // Let the user actually see something!

        String innerText = driver.findElement(By.id("client-list")).getText();

        String expectedClientList = "CNP Client name Balance RON Balance EURO\n" +
                                    "2980626350073 Alexandra Veres 9900.0 100.0";
        assertEquals(expectedClientList, innerText);
    }

    @Test
    @Order(6)
    public void withdrawRonFlowTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get("http://localhost:" + localPort);

        this.checkHomePage();

        By searchButton = By.id("withdraw");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedPageTitle2 = "Withdraw money";
        this.checkPageNameHelper(expectedPageTitle2);

        String cnp = "2980626350073";
        By searchInput1 = By.id("CNP");
        wait.until(presenceOfElementLocated(searchInput1));
        driver.findElement(searchInput1).sendKeys(cnp);

        Thread.sleep(3000);  // Let the user actually see something!

        Select dropdownCurrency = new Select(driver.findElement(By.name("currency")));
        dropdownCurrency.selectByVisibleText("RON");

        int withdrawSum = 4000;
        By searchInput2 = By.id("sum");
        wait.until(presenceOfElementLocated(searchInput2));
        driver.findElement(searchInput2).sendKeys("" + withdrawSum);

        Thread.sleep(3000);  // Let the user actually see something!

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        Thread.sleep(3000);  // Let the user actually see something!

        String expectedPageTitle3 = "Clients list";
        this.checkPageNameHelper(expectedPageTitle3);

        Thread.sleep(3000);  // Let the user actually see something!

        String innerText = driver.findElement(By.id("client-list")).getText();

        String expectedClientList = "CNP Client name Balance RON Balance EURO\n" +
                                    "2980626350073 Alexandra Veres 5900.0 100.0";
        assertEquals(expectedClientList, innerText);
    }
}