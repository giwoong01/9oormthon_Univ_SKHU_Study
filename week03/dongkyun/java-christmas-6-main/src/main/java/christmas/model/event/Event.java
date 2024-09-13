package christmas.model.event;

import christmas.model.calender.DateInfo;
import christmas.model.order.Orders;

public interface Event {
    boolean isApply(DateInfo date);

    int calculateDiscount(DateInfo date, Orders orders);

    int getBenefit();

    EventInventory getEvent();
}
