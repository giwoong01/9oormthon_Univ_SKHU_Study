package christmas.model.event;

import christmas.model.calender.DateInfo;
import christmas.model.menu.Menu;
import christmas.model.order.Orders;

public class FreeGiftEvent implements Event {
    private static final int EVENT_MONTH = 12;
    private static final int EVENT_MIN_DAY = 1;
    private static final int EVENT_MAX_DAY = 31;
    private static final int FREE_GIFT_STANDARD = 120_000;

    private String freeGift = "없음";
    private Integer freeGiftCount = 0;
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
        if (orders.getTotalPrice() >= FREE_GIFT_STANDARD) {
            freeGiftCount++;
            freeGift = Menu.CHAMPAGNE.getName();
            benefit = Menu.CHAMPAGNE.getPrice();
        }
        return benefit;
    }

    public String getFreeGift() {
        return freeGift;
    }

    public int getFreeGiftCount() {
        return freeGiftCount;
    }

    @Override
    public int getBenefit() {
        return benefit;
    }

    @Override
    public EventInventory getEvent() {
        return EventInventory.GIVEAWAY_DISCOUNT;
    }
}
