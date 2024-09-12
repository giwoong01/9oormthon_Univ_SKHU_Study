package christmas.model.discountevent;

import christmas.constant.event.EventName;
import christmas.model.Event;

public class SpecialDay extends Event {
    public SpecialDay() {
        discountPrice = 1000;
        eventName = EventName.EVENT_SPECIAL_DAY;
    }
}
