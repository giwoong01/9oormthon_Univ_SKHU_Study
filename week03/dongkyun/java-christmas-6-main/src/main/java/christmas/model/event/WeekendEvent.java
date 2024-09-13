package christmas.model.event;

import christmas.model.calender.DateInfo;
import christmas.model.menu.MenuCategory;
import christmas.model.order.Orders;

public class WeekendEvent implements Event {
    private static final int EVENT_MONTH = 12;
    private static final int EVENT_MIN_DAY = 1;
    private static final int EVENT_MAX_DAY = 31;
    private static final int EVENT_DISCOUNT_UNIT = 2_023;
    private int benefit;

    @Override
    public boolean isApply(DateInfo date) {
        return isEventPeriod(date) && !date.isWeekend();
    }

    private static boolean isEventPeriod(DateInfo date) {
        return date.getMonth() == EVENT_MONTH &&
                date.getDay() >= EVENT_MIN_DAY &&
                date.getDay() <= EVENT_MAX_DAY;
    }

    @Override
    public int calculateDiscount(DateInfo date, Orders orders) {
        benefit = orders.orders().stream()
                .filter(order -> order.menu().getCategory() == MenuCategory.MAIN)
                .mapToInt(order -> order.count() * EVENT_DISCOUNT_UNIT)
                .sum();

        return benefit;
    }

    @Override
    public int getBenefit() {
        return benefit;
    }

    @Override
    public EventInventory getEvent() {
        return EventInventory.WEEKEND_DISCOUNT;
    }
}
