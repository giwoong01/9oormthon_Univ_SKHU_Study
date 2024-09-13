package christmas.model.discountevent;

import christmas.constant.menu.MenuConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialDayTest {
    @Test
    void 성공적으로_SpecialDay_이벤트_생성() {
        // Given
        int expectedDiscountPrice = -1000;

        // When
        SpecialDay specialDayEvent = new SpecialDay();

        // Then
        assertEquals(expectedDiscountPrice, specialDayEvent.getDiscountPrice());
    }
}
