package pages;

import org.openqa.selenium.By;

public class MainPage {
    // Заголовок страницы
    public static final By PAGE_TITLE = By.tagName("h1");

    // Кнопка «Заказать» (верхняя)
    public static final By ORDER_BUTTON_TOP = By.xpath("//button[text()='Заказать']");

    // Кнопка «Заказать» (нижняя)
    public static final By ORDER_BUTTON_BOTTOM = By.xpath("(//button[text()='Заказать'])[last()]");

    // Выпадающий список вопросов FAQ
    public static final By FAQ_BUTTON = By.xpath("//div[@class = 'accordion__button']");

    // Текст, появляющийся после нажатия на кнопку FAQ
    public static final By FAQ_TEXT = By.xpath("//div[contains(@class, 'accordion__panel')]");

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

    // Кнопка оформления заказа
    public static final By SUBMIT_BUTTON = By.xpath("//button[contains(text(), 'Заказать')]");

    // Сообщение об успешном создании заказа
    public static final By SUCCESS_MESSAGE = By.xpath("//div[contains(text(), 'Заказ оформлен')]");

    // Вопросы о важном
    public static final By FAQ_QUESTION = By.xpath("//button[contains(text(), 'Сколько это стоит? И как оплатить?')]" +
            "|//button[contains(text(), 'Хочу сразу несколько самокатов! Так можно?')]" +
            "|//button[contains(text(), 'Как рассчитывается время аренды?')]" +
            "|//button[contains(text(), 'Можно ли заказать самокат прямо на сегодня?')]" +
            "|//button[contains(text(), 'Можно ли продлить заказ или вернуть самокат раньше?')]" +
            "|//button[contains(text(), 'Вы привозите зарядку вместе с самокатом?')]" +
            "|//button[contains(text(), 'Можно ли отменить заказ?')]" +
            "|//button[contains(text(), 'Я живу за МКАДом, привезёте?')]");
}
