package christmas.model.event;

import christmas.model.calender.DateInfo;
import christmas.model.order.Orders;

public class ChristmasDDayEvent implements Event {
    private static final int EVENT_MONTH = 12;
    private static final int EVENT_MIN_DAY = 1;
    private static final int EVENT_MAX_DAY = 25;
    private static final int START_MONEY = 900;
    private static final int DISCOUNT_UNIT = 100;
    private int benefit;

    @Override
    public boolean isApply(DateInfo date) {
        return isEventPeriod(date);
    }

    private static boolean isEventPeriod(DateInfo date) {
        return date.getMonth() == EVENT_MONTH &&
                date.getDay() >= EVENT_MIN_DAY &&
                date.getDay() <= EVENT_MAX_DAY;
    }

    @Override
    public int calculateDiscount(DateInfo date, Orders orders) {
        benefit = START_MONEY + (date.getDay() * DISCOUNT_UNIT);

        return benefit;
    }

    @Override
    public int getBenefit() {
        return benefit;
    }

    @Override
    public EventInventory getEvent() {
        return EventInventory.CHRISTMAS_D_DAY_DISCOUNT;
    }
}
