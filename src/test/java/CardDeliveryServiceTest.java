import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {
    @BeforeAll
    public static void setUp() {
        System.setProperty("chromeoptions.args", "--no-sandbox,--headless,--disable-dev-shm-usage");
    }

    @Test
    void shouldSubmitRequest()  {
        open("http://localhost:9999");

        $$("[type='text']").first().setValue("По русски");

        $("[type='tel']").setValue("05052020");
        $("[data-test-id=name] input").setValue("Петров Петр");
        $("[name='phone']").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $ (withText("Забронировать")).click();
        $ (withText("Успешно")).waitUntil(visible, 15000);
        $ (withText("Встреча успешно")).waitUntil(visible, 5000);
    }
}

