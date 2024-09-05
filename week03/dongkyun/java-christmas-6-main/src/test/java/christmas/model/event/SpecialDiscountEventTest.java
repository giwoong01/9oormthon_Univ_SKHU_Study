package christmas.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.model.calender.DateInfo;
import christmas.model.order.Orders;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecialDiscountEventTest {
    private static final int EVENT_DISCOUNT_UNIT = 1_000;


    @Test
    @DisplayName("이벤트 기간과 특별한 날에 이벤트가 적용되는지 확인할 수 있다.")
    public void testIsApply() {
        // given
        SpecialDiscountEvent specialDiscountEvent = new SpecialDiscountEvent();
        DateInfo christmasDay = new DateInfo(25);
        DateInfo sundayDate = new DateInfo(24);
        DateInfo regularDate = new DateInfo(15);

        // when & then
        assertThat(specialDiscountEvent.isApply(christmasDay)).isTrue();
        assertThat(specialDiscountEvent.isApply(sundayDate)).isTrue();
        assertThat(specialDiscountEvent.isApply(regularDate)).isFalse();
    }

    @Test
    @DisplayName("이벤트 기간 내에서 특별한 날에 따라 할인 금액을 계산할 수 있다.")
    public void testCalculateDiscount() {
        // given
        SpecialDiscountEvent specialDiscountEvent = new SpecialDiscountEvent();
        DateInfo christmasDay = new DateInfo(25);
        Orders orders = new Orders(List.of());

        // when
        int discount = specialDiscountEvent.calculateDiscount(christmasDay, orders);

        // then
        assertThat(discount).isEqualTo(EVENT_DISCOUNT_UNIT);
        assertThat(specialDiscountEvent.getBenefit()).isEqualTo(EVENT_DISCOUNT_UNIT);
    }

    @Test
    @DisplayName("이벤트 적용시 올바른 EventInventory 값이 반환된다.")
    public void testGetEvent() {
        // given
        SpecialDiscountEvent specialDiscountEvent = new SpecialDiscountEvent();

        // when
        EventInventory eventInventory = specialDiscountEvent.getEvent();

        // then
        assertThat(eventInventory).isEqualTo(EventInventory.STAR_DISCOUNT);
    }
}
