package christmas.model.discountevent;

import christmas.constant.event.EventName;
import org.junit.jupiter.api.Test;

import static christmas.view.OutputView.formatPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DDayTest {
    @Test
    void 성공적으로_DDay_이벤트_생성() {
        // Given
        int date = 5;
        int expectedDiscountPrice = 1000 + 100 * (date - 1);

        // When
        DDay dDayEvent = new DDay(date);

        // Then
        assertEquals(expectedDiscountPrice * -1, dDayEvent.getDiscountPrice());
    }
}
