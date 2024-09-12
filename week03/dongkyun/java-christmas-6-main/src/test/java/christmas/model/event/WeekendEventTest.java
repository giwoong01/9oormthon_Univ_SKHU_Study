package christmas.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.model.calender.DateInfo;
import christmas.model.menu.Menu;
import christmas.model.order.Order;
import christmas.model.order.Orders;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WeekendEventTest {
    private static final int EVENT_DISCOUNT_UNIT = 2_023;

    @Test
    @DisplayName("이벤트 기간과 주말이 아닌 날에 이벤트가 적용되는지 확인할 수 있다.")
    public void testIsApply() {
        WeekendEvent weekendEvent = new WeekendEvent();
        DateInfo weekendDate = new DateInfo(23); // 주말
        DateInfo weekdayDate = new DateInfo(18);  // 평일

        assertThat(weekendEvent.isApply(weekendDate)).isFalse();
        assertThat(weekendEvent.isApply(weekdayDate)).isTrue();
    }

    @Test
    @DisplayName("이벤트 기간 내에서 메인 메뉴에 따른 할인 금액을 계산할 수 있다.")
    public void testCalculateDiscount() {
        WeekendEvent weekendEvent = new WeekendEvent();
        DateInfo date = new DateInfo(18); //  평일
        Orders orders = new Orders(List.of(
                new Order(Menu.T_BONE_STEAK, 2),
                new Order(Menu.BBQ_RIBS, 1),
                new Order(Menu.ICE_CREAM, 1)
        ));

        int discount = weekendEvent.calculateDiscount(date, orders);

        assertThat(discount).isEqualTo(3 * EVENT_DISCOUNT_UNIT);
        assertThat(weekendEvent.getBenefit()).isEqualTo(3 * EVENT_DISCOUNT_UNIT);
    }

    @Test
    @DisplayName("이벤트 기간이 아닌 날에는 할인 금액이 0이다.")
    public void testCalculateDiscountWithNonEventDate() {
        WeekendEvent weekendEvent = new WeekendEvent();
        DateInfo date = new DateInfo(30); // 12월 30일, 평일
        Orders orders = new Orders(List.of(
                new Order(Menu.MUSHROOM_SOUP, 1)
        ));

        int discount = weekendEvent.calculateDiscount(date, orders);

        assertThat(discount).isEqualTo(0);
        assertThat(weekendEvent.getBenefit()).isEqualTo(0);
    }

    @Test
    @DisplayName("이벤트 적용시 올바른 EventInventory 값이 반환된다.")
    public void testGetEvent() {
        WeekendEvent weekendEvent = new WeekendEvent();

        EventInventory eventInventory = weekendEvent.getEvent();

        assertThat(eventInventory).isEqualTo(EventInventory.WEEKEND_DISCOUNT);
    }
}
