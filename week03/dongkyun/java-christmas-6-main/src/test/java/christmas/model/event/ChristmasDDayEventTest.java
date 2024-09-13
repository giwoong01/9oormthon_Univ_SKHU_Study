package christmas.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.model.calender.DateInfo;
import christmas.model.order.Orders;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChristmasDDayEventTest {

    @Test
    @DisplayName("이벤트 기간에 해당하는 날짜인지 확인할 수 있다.")
    public void testIsApply() {
        // given
        ChristmasDDayEvent christmasDDayEvent = new ChristmasDDayEvent();
        DateInfo dateInEvent = new DateInfo(15);

        // when & then
        assertThat(christmasDDayEvent.isApply(dateInEvent)).isTrue();
    }

    @Test
    @DisplayName("이벤트 기간 내에서 날짜에 따른 할인 금액을 계산할 수 있다.")
    public void testCalculateDiscount() {
        // given
        ChristmasDDayEvent christmasDDayEvent = new ChristmasDDayEvent();
        DateInfo date = new DateInfo(10);
        Orders orders = new Orders(List.of());

        // when
        int discount = christmasDDayEvent.calculateDiscount(date, orders);

        // then
        int expectedDiscount = 900 + (10 * 100);
        assertThat(discount).isEqualTo(expectedDiscount);
        assertThat(christmasDDayEvent.getBenefit()).isEqualTo(expectedDiscount);
    }

    @Test
    @DisplayName("이벤트 적용시 올바른 EventInventory 값이 반환된다.")
    public void testGetEvent() {
        // given
        ChristmasDDayEvent christmasDDayEvent = new ChristmasDDayEvent();

        // when
        EventInventory eventInventory = christmasDDayEvent.getEvent();

        // then
        assertThat(eventInventory).isEqualTo(EventInventory.CHRISTMAS_D_DAY_DISCOUNT);
    }
}
