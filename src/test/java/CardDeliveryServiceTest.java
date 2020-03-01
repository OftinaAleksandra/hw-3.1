import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {
    @Test
    void shouldSubmitRequest()  {
        open("http://localhost:9999");

        $$("[type='text']").first().setValue("Ка");
        $ (byText("Казань")).click();
        $("[data-test-id=name] input").setValue("Петров Петр");
        $("[name='phone']").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $ (withText("Забронировать")).click();
        $ (withText("Успешно!")).waitUntil(visible, 15000);
        $ (withText("Встреча успешно")).waitUntil(visible, 5000);
    }
}
