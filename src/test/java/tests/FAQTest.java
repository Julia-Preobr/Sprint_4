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
    private final String browser;
    private final String question;
    private final String answer;

    private WebDriver driver;

    public FAQTest(String browser, String question, String answer) {
        this.browser = browser;
        this.question = question;
        this.answer = answer;
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
                {browser, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {browser, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {browser, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {browser, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {browser, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {browser, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {browser, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {browser, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
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

        By answerLocator = MainPage.getPByText(answer);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(answerLocator)
        );

        // Проверка, что ответ отображается
        assertTrue(isAnswerDisplayed(answerLocator));
    }

    private void clickElementsWithText(By by, String text) {
        WebElement button = findElementsWithText(by, text);
        if (button == null) {
            throw new RuntimeException("Не найден элемент с текстом: " + text);
        }
        button.click();
    }

    private WebElement findElementsWithText(By by, String text) {
        List<WebElement> elements = driver.findElements(by);
        for (WebElement element : elements) {
            if (element.getText().contains(text)) {
                return element;
            }
        }
        return null;
    }

    public boolean isAnswerDisplayed(By answerLocator) {
        return driver.findElement(answerLocator).isDisplayed();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
