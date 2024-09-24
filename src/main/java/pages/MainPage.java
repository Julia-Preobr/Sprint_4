package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainPage {
    public static final String TEST_URL = "https://qa-scooter.praktikum-services.ru/";

    // Заголовок страницы
    public static final By PAGE_TITLE = By.tagName("h1");

    public static final By PARENT_ELEMENT = By.xpath("..");
    public static final By YES_BUTTON = By.xpath(".//button[contains(text(), 'Да')]");

    public static final By REACT_DATE_PICKER = By.className("react-datepicker");

    // Кнопка «Заказать» (верхняя)
    public static final By ORDER_BUTTON_TOP = By.xpath("//button[text()='Заказать']");

    // Кнопка «Заказать» (нижняя)
    public static final By ORDER_BUTTON_BOTTOM = By.xpath("(//button[text()='Заказать'])[last()]");

    // Поля для заполнения формы заказа
    public static final By NAME_INPUT = By.xpath("//input[@placeholder='* Имя']");
    public static final By SURNAME_INPUT = By.xpath("//input[@placeholder='* Фамилия']");
    public static final By ADDRESS_INPUT = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    public static final By METRO_INPUT = By.xpath("//input[@placeholder='* Станция метро']");
    public static final By PHONE_INPUT = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']"); // 12 символов
    public static final By NEXT_BUTTON = By.xpath("//button[text()='Далее']");
    public static final By COOKIES_BUTTON = By.xpath("//button[contains(text(), 'да все привыкли')]");

    // Поля выбора даты и срока аренды
    public static final By DATE_PICKER = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    public static final By RENT_DURATION_DROPDOWN = By.xpath("//div[text()='* Срок аренды']");
    public static final By RENT_DURATION_OPTION = By.xpath("//div[contains(@class, 'Dropdown-option')]");

    // Выбор цвета самоката
    public static final By COLOR_RADIO_BUTTON = By.xpath("//input[@id='black' or @id='grey']");
    public static final By COMMENT_INPUT = By.xpath("//input[@placeholder='Комментарий для курьера']");

    public static final By CONFIRMATION_BUTTON = By.xpath("//div[contains(text(), 'Хотите оформить заказ?')]");

    // Кнопка оформления заказа
    public static final By SUBMIT_BUTTON = By.xpath("//button[contains(text(), 'Заказать')]");
    public static final By LAST_SUBMIT_BUTTON = By.xpath("(//button[contains(text(), 'Заказать')])[last()]");

    // Сообщение об успешном создании заказа
    public static final By SUCCESS_MESSAGE = By.xpath("//div[contains(text(), 'Заказ оформлен')]");

    // Выпадающий список вопросов FAQ
    public static final By ACCORDION_BUTTON = By.xpath("//div[@class='accordion__button']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    // Текст, появляющийся после нажатия на кнопку FAQ
    public static By getDaySelectorOnDate(String formattedDate) {
        String dateXpath = String.format("//div[contains(@class, 'react-datepicker__day') and text()='%s']", formattedDate);
        return By.xpath(dateXpath);
    }

    // Текст, появляющийся после нажатия на кнопку FAQ
    public static By getPByText(String text) {
        return By.xpath("//div[contains(@class, 'accordion__panel')]/p[contains(text(), '" + text + "')]");
    }

    public static By getInputWithText(String text) {
        return By.xpath("//input[@value='" + text + "']");
    }

    public static void clickElementsWithText(List<WebElement> elements, String text) {
        WebElement button = filterElementsWithText(elements, text);
        if (button == null) {
            throw new RuntimeException("Не найден элемент с текстом: " + text);
        }
        button.click();
    }

    public static WebElement filterElementsWithText(List<WebElement> elements, String text) {
        for (WebElement element : elements) {
            if (element.getText().contains(text)) {
                return element;
            }
        }
        return null;
    }

    public void fillOrderForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(MainPage.NAME_INPUT).sendKeys(name);
        driver.findElement(MainPage.SURNAME_INPUT).sendKeys(surname);
        driver.findElement(MainPage.ADDRESS_INPUT).sendKeys(address);
        driver.findElement(MainPage.METRO_INPUT).sendKeys(metro);

        // Здесь вводим метро
        By metroValue = MainPage.getInputWithText(metro);
        driver.findElement(metroValue).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(metroValue).sendKeys(Keys.ENTER);

        driver.findElement(MainPage.PHONE_INPUT).sendKeys(phone);
        driver.findElements(MainPage.COOKIES_BUTTON).forEach(button -> button.click());
        driver.findElement(MainPage.NEXT_BUTTON).click();

        // Выбор даты
        driver.findElement(MainPage.DATE_PICKER).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(MainPage.REACT_DATE_PICKER));

        // Получаем следующую дату
        LocalDate nextDate = LocalDate.now().plusDays(1);
        String formattedDate = nextDate.format(DateTimeFormatter.ofPattern("d"));

        // В XPath используем форматированную дату для выбора элемента
        wait.until(ExpectedConditions.visibilityOfElementLocated(MainPage.getDaySelectorOnDate(formattedDate))).click();

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

    public boolean isAnswerDisplayed(By answerLocator) {
        return driver.findElement(answerLocator).isDisplayed();
    }
}
