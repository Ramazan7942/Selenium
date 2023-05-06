package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.openqa.selenium.By.cssSelector;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldReturnSuccessIfFieldsFilledInCorrectly() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79293445567");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id='order-success']")).getText().strip();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldReturnSuccessfullyIfSurnameWithHyphen() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Петров Иван");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79293445567");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id='order-success']")).getText().strip();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

}