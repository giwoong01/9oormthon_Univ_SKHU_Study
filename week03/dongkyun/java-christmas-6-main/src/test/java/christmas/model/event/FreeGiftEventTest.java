package christmas.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.model.calender.DateInfo;
import christmas.model.menu.Menu;
import christmas.model.order.Order;
import christmas.model.order.Orders;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FreeGiftEventTest {

    @Test
    @DisplayName("이벤트 기간에 해당하는 날짜인지 확인할 수 있다.")
    public void testIsApply() {
        // given
        FreeGiftEvent freeGiftEvent = new FreeGiftEvent();
        DateInfo dateInEvent = new DateInfo(15);

        // when & then
        assertThat(freeGiftEvent.isApply(dateInEvent)).isTrue();
    }

    @Test
    @DisplayName("총 주문 금액이 기준 금액 이상일 때 무료 선물이 지급된다.")
    public void testCalculateDiscountWithEligibleOrder() {
        // given
        FreeGiftEvent freeGiftEvent = new FreeGiftEvent();
        DateInfo date = new DateInfo(15);
        Order order = new Order(Menu.RED_WINE, 2);
        Orders orders = new Orders(List.of(order));

        // when
        int benefit = freeGiftEvent.calculateDiscount(date, orders);

        // then
        assertThat(benefit).isEqualTo(Menu.CHAMPAGNE.getPrice());
        assertThat(freeGiftEvent.getFreeGift()).isEqualTo(Menu.CHAMPAGNE.getName());
        assertThat(freeGiftEvent.getFreeGiftCount()).isEqualTo(1);
        assertThat(freeGiftEvent.getBenefit()).isEqualTo(benefit);
    }

    @Test
    @DisplayName("총 주문 금액이 기준 금액 미만일 때 무료 선물이 지급되지 않는다.")
    public void testCalculateDiscountWithNonEligibleOrder() {
        // given
        FreeGiftEvent freeGiftEvent = new FreeGiftEvent();
        DateInfo date = new DateInfo(15);
        Order order = new Order(Menu.MUSHROOM_SOUP, 10);
        Orders orders = new Orders(List.of(order));

        // when
        int benefit = freeGiftEvent.calculateDiscount(date, orders);

        // then
        assertThat(benefit).isEqualTo(0);
        assertThat(freeGiftEvent.getFreeGift()).isEqualTo("없음");
        assertThat(freeGiftEvent.getFreeGiftCount()).isEqualTo(0);
        assertThat(freeGiftEvent.getBenefit()).isEqualTo(benefit);
    }

    @Test
    @DisplayName("다시 계산 시 무료 선물 횟수가 증가한다.")
    public void testCalculateDiscountMultipleTimes() {
        // given
        FreeGiftEvent freeGiftEvent = new FreeGiftEvent();
        DateInfo date = new DateInfo(15);
        Order order = new Order(Menu.RED_WINE, 2);
        Orders orders = new Orders(List.of(order));

        // first calculation
        freeGiftEvent.calculateDiscount(date, orders);

        // when
        int benefit = freeGiftEvent.calculateDiscount(date, orders);

        // then
        assertThat(benefit).isEqualTo(Menu.CHAMPAGNE.getPrice());
        assertThat(freeGiftEvent.getFreeGift()).isEqualTo(Menu.CHAMPAGNE.getName());
        assertThat(freeGiftEvent.getFreeGiftCount()).isEqualTo(2);
        assertThat(freeGiftEvent.getBenefit()).isEqualTo(benefit);
    }
}
