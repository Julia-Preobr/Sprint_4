package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest {
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    private String question;
    private String browser;

    private WebDriver driver;

    public FAQTest(String browser, String question) {
        this.browser = browser;
        this.question = question;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> faqQuestions() {
        List<Object[]> questions = new ArrayList<>();
        questions.addAll(getBrowserTests(CHROME));
        questions.addAll(getBrowserTests(FIREFOX));
        return questions;
    }

    private static List<Object[]> getBrowserTests(String browser) {
        return Arrays.asList(new Object[][]{
                {browser, "Сколько это стоит? И как оплатить?"},
                {browser, "Хочу сразу несколько самокатов! Так можно?"},
                {browser, "Как рассчитывается время аренды?"},
                {browser, "Можно ли заказать самокат прямо на сегодня?"},
                {browser, "Можно ли продлить заказ или вернуть самокат раньше?"},
                {browser, "Вы привозите зарядку вместе с самокатом?"},
                {browser, "Можно ли отменить заказ?"},
                {browser, "Я живу за МКАДом, привезёте?"}
        });
    }

    @Before
    public void setUp() {
        if (browser.equals(CHROME)) {
            driver = new ChromeDriver();
        } else if (browser.equals(FIREFOX)) {
            driver = new FirefoxDriver();
        }
        driver.get(MainPage.TEST_URL);
    }

    @Test
    public void testFAQQuestions() {
        // Прокрутка страницы к FAQ
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Кликаем по вопросу
        clickElementsWithText(MainPage.ACCORDION_BUTTON, question);

        // Используйте WebDriverWait, чтобы проверить отображение ответа
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(MainPage.ACCORDION_PANEL));

        // Проверка, что ответ отображается
        assertTrue(isAnswerDisplayed());
    }

    private void clickElementsWithText(By by, String text) {
        List<WebElement> buttons = driver.findElements(by);
        for (WebElement button : buttons) {
            if (button.getText().contains(text)) {
                button.click();
                return;
            }
        }
        throw new RuntimeException("Вопрос не найден: " + text);
    }

    public boolean isAnswerDisplayed() {
        return driver.findElement(MainPage.ACCORDION_PANEL).isDisplayed();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
