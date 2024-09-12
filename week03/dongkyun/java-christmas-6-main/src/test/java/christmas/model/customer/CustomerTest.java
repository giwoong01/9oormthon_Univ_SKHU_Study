package christmas.model.customer;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.model.calender.DateInfo;
import christmas.model.event.FreeGiftEvent;
import christmas.model.menu.Menu;
import christmas.model.order.Order;
import christmas.model.order.Orders;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    private static final int FREE_GIFT_PRICE = 25_000;

    @Test
    @DisplayName("Customer 객체가 올바르게 생성되는지 확인한다.")
    public void testCustomerCreation() {
        DateInfo date = new DateInfo(25); // 2023년 12월 25일
        Orders orders = new Orders(List.of(new Order(Menu.MUSHROOM_SOUP, 3)));

        Customer customer = new Customer(date, orders);

        assertThat(customer.getEvents()).isNotEmpty();
        assertThat(customer.getReceivedDiscounts()).isGreaterThanOrEqualTo(0);
        assertThat(customer.getBadge()).isNotNull();
    }

    @Test
    @DisplayName("calculateBenefits 메서드가 올바르게 할인 금액을 계산하는지 확인한다.")
    public void testCalculateBenefits() {
        DateInfo date = new DateInfo(1);
        Orders orders = new Orders(List.of(
                new Order(Menu.MUSHROOM_SOUP, 3),
                new Order(Menu.CAESAR_SALAD, 1)
        ));

        Customer customer = new Customer(date, orders);

        assertThat(customer.getReceivedDiscounts()).isEqualTo(1000);
    }

    @Test
    @DisplayName("getFreeGiftEvent 메서드가 FreeGiftEvent를 반환하는지 확인한다.")
    public void testGetFreeGiftEvent() {
        DateInfo date = new DateInfo(25);
        Orders orders = new Orders(List.of(new Order(Menu.BBQ_RIBS, 10)));

        Customer customer = new Customer(date, orders);

        FreeGiftEvent freeGiftEvent = customer.getFreeGiftEvent();
        assertThat(freeGiftEvent).isInstanceOf(FreeGiftEvent.class);
        assertThat(freeGiftEvent.getFreeGift()).isEqualTo(Menu.CHAMPAGNE.getName());
    }

    @Test
    @DisplayName("getExpectPaymentAmount 메서드가 예상 결제 금액을 올바르게 계산하는지 확인한다.")
    public void testGetExpectPaymentAmount() {
        DateInfo date = new DateInfo(25);
        Orders orders = new Orders(List.of(
                new Order(Menu.MUSHROOM_SOUP, 3),
                new Order(Menu.CAESAR_SALAD, 1)
        ));

        Customer customer = new Customer(date, orders);

        int totalPrice = orders.getTotalPrice();
        int expectedDiscounts = customer.getReceivedDiscounts();
        int expectedAmount = totalPrice - expectedDiscounts + FREE_GIFT_PRICE;

        assertThat(customer.getExpectPaymentAmount(orders)).isEqualTo(expectedAmount);
    }
}
