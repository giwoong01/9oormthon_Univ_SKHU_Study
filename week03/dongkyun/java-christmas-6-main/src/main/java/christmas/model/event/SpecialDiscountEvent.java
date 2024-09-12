package christmas.model.event;

import christmas.model.calender.DateInfo;
import christmas.model.order.Orders;

public class SpecialDiscountEvent implements Event {
    private static final int EVENT_MONTH = 12;
    private static final int EVENT_MIN_DAY = 1;
    private static final int EVENT_MAX_DAY = 31;
    private static final String CHRISTMAS_EVENT_DAY = "12월 25일";
    private static final int EVENT_DISCOUNT_UNIT = 1_000;

    private int benefit;

    @Override
    public boolean isApply(DateInfo date) {
        return isEventPeriod(date) && isSpecialDay(date);
    }

    private static boolean isEventPeriod(DateInfo date) {
        return date.getMonth() == EVENT_MONTH &&
                date.getDay() >= EVENT_MIN_DAY &&
                date.getDay() <= EVENT_MAX_DAY;
    }

    private static boolean isSpecialDay(DateInfo date) {
        return date.isSunday() || date.toString().equals(CHRISTMAS_EVENT_DAY);
    }

    @Override
    public int calculateDiscount(DateInfo date, Orders orders) {
        benefit = EVENT_DISCOUNT_UNIT;

        return benefit;
    }

    @Override
    public int getBenefit() {
        return benefit;
    }

    @Override
    public EventInventory getEvent() {
        return EventInventory.STAR_DISCOUNT;
    }
}
