package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
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
    }

    @Test
    public void testOrderCreation() {
        testOrderButton(MainPage.ORDER_BUTTON_TOP);
        driver.navigate().refresh(); // Сбрасываем страницу для следующего теста
        testOrderButton(MainPage.ORDER_BUTTON_BOTTOM);
    }

    private void testOrderButton(By orderButton) {
        driver.findElement(orderButton).click();
        fillOrderForm();

        // Оформление заказа
        wait.until(ExpectedConditions.elementToBeClickable(MainPage.LAST_SUBMIT_BUTTON)).click();

        //driver.findElement(MainPage.SUBMIT_BUTTON).click();

        // ПОдтверждение
        By divLocator = MainPage.CONFIRMATION_BUTTON; // Замените на нужный текст
        wait.until(ExpectedConditions.visibilityOfElementLocated(divLocator))
                .findElement(MainPage.PARENT_ELEMENT)
                .findElement(MainPage.YES_BUTTON)
                .click();

        assertTrue(driver.findElement(MainPage.SUCCESS_MESSAGE).isDisplayed());
    }

    private void fillOrderForm() {
        driver.findElement(MainPage.NAME_INPUT).sendKeys(name);
        driver.findElement(MainPage.SURNAME_INPUT).sendKeys(surname);
        driver.findElement(MainPage.ADDRESS_INPUT).sendKeys(address);
        driver.findElement(MainPage.METRO_INPUT).sendKeys(metro);

        // Здесь вводим метро
        By metroValue = By.xpath("//input[@value='" + metro + "']");
        driver.findElement(metroValue).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(metroValue).sendKeys(Keys.ENTER);

        driver.findElement(MainPage.PHONE_INPUT).sendKeys(phone);
        try {
            driver.findElement(MainPage.COOKIES_BUTTON).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        driver.findElement(MainPage.NEXT_BUTTON).click();

        // Выбор даты
        driver.findElement(MainPage.DATE_PICKER).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker")));

        // Получаем следующую дату
        LocalDate nextDate = LocalDate.now().plusDays(1);
        String formattedDate = nextDate.format(DateTimeFormatter.ofPattern("d"));

        // В XPath используем форматированную дату для выбора элемента
        String dateXpath = String.format("//div[contains(@class, 'react-datepicker__day') and text()='%s']", formattedDate);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dateXpath))).click();

        // Выбор срока аренды
        driver.findElement(MainPage.RENT_DURATION_DROPDOWN).click(); // Открываем выпадающий список сроков аренды

        // Ожидание и выбор одного из вариантов; например, выбираем первый элемент:
        wait.until(ExpectedConditions.visibilityOfElementLocated(MainPage.RENT_DURATION_OPTION));
        driver.findElement(MainPage.RENT_DURATION_OPTION).click(); // Здесь нужно убедиться, что этот элемент правильно определен в MainPage

        // Выбор цвета самоката
        driver.findElement(MainPage.COLOR_RADIO_BUTTON).click(); // Выберите черный или серый

        // Ввод комментария
        driver.findElement(MainPage.COMMENT_INPUT).sendKeys("ПРИВЕТ");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}