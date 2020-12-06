package com.app.bank;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTest {

    @LocalServerPort
    private int localPort;

    private String serverUrl;
    private WebDriver driver;

    @BeforeAll
    public static void init() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void initServerUrl() {
        this.serverUrl = "http://localhost:" + localPort;
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void check_page_name_helper(String name) {
        try {
            assertEquals(name, driver.getTitle());
            System.out.println("Navigated to correct webpage");
        } catch (Throwable pageNavigationError) {
            System.out.println("Didn't navigate to correct webpage");
        }
    }


    @Test
    public void addNewClientFlowTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get(serverUrl);

        String expectedTitle1 = "Home Page";
        this.check_page_name_helper(expectedTitle1);

        By searchButton = By.id("new_client");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        String expectedTitle2 = "New client";
        this.check_page_name_helper(expectedTitle2);

        String cnp = "2980626350073";
        By searchInput1 = By.name("CNP");
        wait.until(presenceOfElementLocated(searchInput1));
        driver.findElement(searchInput1).sendKeys(cnp);

        String name = "Alexandra Veres";
        By searchInput2 = By.name("clientName");
        wait.until(presenceOfElementLocated(searchInput2));
        driver.findElement(searchInput2).sendKeys(name);

        By searchButton2 = By.id("submit-button");
        wait.until(elementToBeClickable(searchButton2));
        driver.findElement(searchButton2).click();

        String expectedTitle3 = "Clients list";
        this.check_page_name_helper(expectedTitle3);

        //String innerText = driver.findElement(
        //        By.xpath("//table/tbody/tr[2]/td[1]")).getText();
        //System.out.println(innerText);
    }
}
/*
    @Test
    public void whenInsertText_thenTextIsDisplayed() {
        String message = "Hello";
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get(serverUrl);
        By searchInput = By.id("input-id");
        wait.until(presenceOfElementLocated(searchInput));
        driver.findElement(searchInput).sendKeys(message);
        By searchButton = By.id("greeting-button");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();
        assertEquals(message, driver.findElement(By.id("button-pressed"))
                .getText());

    }

    @Test
    public void whenTextIsNotInserted_thenTextIsNotDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        driver.get(serverUrl);

        By searchButton = By.id("greeting-button");
        wait.until(elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();

        assertEquals("Error! Please insert a value", driver.findElement(By.id("button-pressed"))
                .getText());

    }*/