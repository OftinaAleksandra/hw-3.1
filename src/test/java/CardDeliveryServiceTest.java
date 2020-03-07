import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {

    @BeforeAll
    public static void setUp() {
       // System.setProperty("chromeoptions.args", "--no-sandbox,--headless,--disable-dev-shm-usage");
    }

    @Test
    void shouldSubmitRequestOne() {
        open("http://localhost:9999");

        $$("[type='text']").first().setValue("Казань");
        $("[type='tel']").setValue("15032020");
        $("[data-test-id=name] input").setValue("Петров Петр");
        $("[name='phone']").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $(withText("Встреча успешно")).waitUntil(visible, 5000);
        close();
    }

    @Test
    void shouldSubmitRequest()  {
        open("http://localhost:9999");

        $$("[type='text']").first().setValue("Ка");
        $(byText("Казань")).click();

        selectDate(7);

        $("[data-test-id=name] input").setValue("Петров Петр");
        $("[name='phone']").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $(withText("Встреча успешно")).waitUntil(visible, 5000);
        close();
    }

    private void selectDate(int shift) {
        clickOnCalendar();
        ElementsCollection calendar = currentCalendar();
        shiftDate(findCurrentDate(calendar), shift, calendar);
    }

    private void shiftDate(int currentDate, int shift, ElementsCollection calendar) {
        int maxDate = findMaxDate(calendar);
        if (currentDate + shift <= maxDate) {
            SelenideElement element = findElementWithValue(calendar, currentDate + shift);
            element.click();
        } else {
            nextMonth();
            shiftDate(1, shift - (maxDate - currentDate) - 1, currentCalendar());
        }
    }

    private void clickOnCalendar() {
        SelenideElement button = $x("//span[contains(@class, 'icon_name_calendar')]");
        button.click(); //*[@id="root"]/div/form/fieldset/div[6]/div[2]/div/button/span/span[2]
    }

    private void nextMonth() {
        SelenideElement button = $x("//div[contains(@class, 'calendar__arrow') and @data-step='1']");
        button.click();
    }

    private ElementsCollection currentCalendar() {
        return $$x("//td[@data-day]");
    }

    private int findCurrentDate(ElementsCollection calendar) {
        Calendar calendar1 = Calendar.getInstance();
        int Date = calendar1.get(Calendar.DAY_OF_MONTH);
        SelenideElement currentDateElement = $(byText(Integer.toString(Date)));
        return extractDate(currentDateElement);
    }

    private int extractDate(SelenideElement element) {
        String currentDateStr = element.innerText();
        return Integer.parseInt(currentDateStr);
    }

    private int findMaxDate(ElementsCollection calendar) {
        return calendar.stream()
                .map(this::extractDate)
                .reduce(-1, Math::max);
    }

    private SelenideElement findElementWithValue(ElementsCollection calendar, int value) {
        return calendar.stream()
                .map(e -> Pair.of(e, extractDate(e)))
                .filter(p -> p.getRight() == value)
                .map(Pair::getLeft)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
