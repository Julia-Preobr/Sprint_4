package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private String name;
    private String surname;
    private String address;
    private String metro;
    private String phone;
    private String browser;

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "Москва, ул. Ленина, д. 1", "Китай-город", "12345678901", "chrome"},
                {"Анна", "Петрова", "Санкт-Петербург, пл. Революции, д. 5", "Невский проспект", "09876543210", "firefox"}
        });
    }

    public OrderTest(String name, String surname, String address, String metro, String phone, String browser) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.browser = browser;
    }

    @Before
    public void setUp() {
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        }
        driver.get(MainPage.TEST_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver, wait);
    }

    @Test
    public void testOrderCreation() {
        testOrderButton(MainPage.ORDER_BUTTON_TOP);
        driver.navigate().refresh(); // Сбрасываем страницу для следующего теста
        testOrderButton(MainPage.ORDER_BUTTON_BOTTOM);
    }

    private void testOrderButton(By orderButton) {
        driver.findElement(orderButton).click();
        mainPage.fillOrderForm(name, surname, address, metro, phone);

        // Оформление заказа
        wait.until(ExpectedConditions.elementToBeClickable(MainPage.LAST_SUBMIT_BUTTON)).click();

        // Подтверждение
        By divLocator = MainPage.CONFIRMATION_BUTTON;
        wait.until(ExpectedConditions.visibilityOfElementLocated(divLocator))
                .findElement(MainPage.PARENT_ELEMENT)
                .findElement(MainPage.YES_BUTTON)
                .click();

        assertTrue(driver.findElement(MainPage.SUCCESS_MESSAGE).isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}