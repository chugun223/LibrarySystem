package ru.Knyazev_Timofey.NauJava.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookLoginSeleniumTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void seleniumTest() {
        driver.get("http://localhost:" + port + "/login");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        WebElement passwordInput = driver.findElement(By.name("password"));

        emailInput.sendKeys("ivanov@example.com");
        passwordInput.sendKeys("password");

        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));

        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/view/books/list"));

        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertEquals("Список книг", pageTitle.getText());

        WebElement booksTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
        assertTrue(booksTable.isDisplayed());

        List<WebElement> headers = booksTable.findElements(By.cssSelector("thead th"));
        assertEquals(6, headers.size());
        assertEquals("ID", headers.get(0).getText());
        assertEquals("Название", headers.get(1).getText());
        assertEquals("Автор", headers.get(2).getText());

        List<WebElement> bookRows = booksTable.findElements(By.cssSelector("tbody tr"));
        assertFalse(bookRows.isEmpty());

        WebElement logoutButton = driver.findElement(By.id("logoutButton"));

        logoutButton.click();

        wait.until(ExpectedConditions.urlContains("/login?logout"));

        WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        assertTrue(loginForm.isDisplayed());
    }
}
