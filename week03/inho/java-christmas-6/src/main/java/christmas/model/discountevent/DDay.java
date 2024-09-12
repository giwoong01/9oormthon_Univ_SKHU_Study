package christmas.model.discountevent;

import christmas.constant.event.EventName;
import christmas.model.Event;

public class DDay extends Event {
    public DDay(int date) {
        final int init = 1000;
        final int increase = 100;
        final int defaultDay = 1;

        discountPrice = init + increase * (date - defaultDay);

        eventName = EventName.EVENT_D_DAY;
    }
}
