package pages;

import org.openqa.selenium.By;

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
    public static final By COOKIES_BUTTON = By.xpath("//button[text()='да все привыкли']");

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
}
