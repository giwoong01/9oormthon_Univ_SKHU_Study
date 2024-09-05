package christmas.model.customer;

import christmas.message.ErrorMessage;
import christmas.model.calender.DateInfo;
import christmas.model.event.Badge;
import christmas.model.event.ChristmasDDayEvent;
import christmas.model.event.Event;
import christmas.model.event.FreeGiftEvent;
import christmas.model.event.SpecialDiscountEvent;
import christmas.model.event.WeekdayEvent;
import christmas.model.event.WeekendEvent;
import christmas.model.order.Orders;
import java.util.List;

public class Customer {
    private static final int START_BENEFIT_AMOUNT = 0;
    private static final int FREE_GIFT_PRICE = 25_000;

    private final Long customerId;
    private final List<Event> events;
    private final Badge badge;
    private final int receivedDiscounts;

    public Customer(DateInfo date, Orders orders) {
        this.customerId = CustomerIdGenerator.generateId();
        this.events = initializeEvents();
        this.receivedDiscounts = calculateBenefits(date, orders);
        this.badge = determineBadge(receivedDiscounts);
    }

    private List<Event> initializeEvents() {
        return List.of(
                new FreeGiftEvent(),
                new ChristmasDDayEvent(),
                new WeekdayEvent(),
                new WeekendEvent(),
                new SpecialDiscountEvent()
        );
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getReceivedDiscounts() {
        return receivedDiscounts;
    }

    public Badge getBadge() {
        return badge;
    }

    public int calculateBenefits(DateInfo date, Orders orders) {
        if (events.isEmpty()) {
            return START_BENEFIT_AMOUNT;
        }
        return events.stream()
                .filter(event -> event.isApply(date))
                .mapToInt(event -> event.calculateDiscount(date, orders))
                .sum();
    }

    private Badge determineBadge(int receivedDiscounts) {
        return Badge.fromDiscount(receivedDiscounts);
    }

    public FreeGiftEvent getFreeGiftEvent() {
        return events.stream()
                .filter(event -> event instanceof FreeGiftEvent)
                .map(event -> (FreeGiftEvent) event)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NON_EXIST_FREE_GIFT.getMessage()));
    }

    public int getExpectPaymentAmount(Orders orders) {
        if (receivedDiscounts == 0) {
            return orders.getTotalPrice();
        }
        return orders.getTotalPrice() - receivedDiscounts + FREE_GIFT_PRICE;
    }
}
