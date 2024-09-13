package christmas.model.discountevent;

import christmas.constant.menu.MenuConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftTest {
    @Test
    void 성공적으로_Gift_이벤트_생성() {
        // Given
        int expectedDiscountPrice = MenuConstant.CHAMPAGNE.getManuPrice();

        // When
        Gift giftEvent = new Gift();

        // Then
        assertEquals(expectedDiscountPrice*-1, giftEvent.getDiscountPrice());
    }
}
