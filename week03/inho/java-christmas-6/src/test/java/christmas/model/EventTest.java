package christmas.model;

import christmas.constant.event.EventName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {
    @Test
    void 할인된_가격을_올바르게_반환() {
        // Given
        Event event = new Event();
        event.discountPrice = 1000;
        event.eventName = EventName.EVENT_GIFT;

        // When
        int discountPrice = event.getDiscountPrice();

        // Then
        assertThat(discountPrice).isEqualTo(-1000);
    }

    @Test
    void 이벤트_정보를_올바르게_반환() {
        // Given
        Event event = new Event();
        event.discountPrice = 2000;
        event.eventName = EventName.EVENT_WEEKDAY;

        // When
        String eventDescription = event.toString();

        // Then
        assertThat(eventDescription).isEqualTo("평일 할인: -2,000원");
    }
}
