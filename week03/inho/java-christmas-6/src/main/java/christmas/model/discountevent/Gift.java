package christmas.model.discountevent;

import christmas.constant.event.EventName;
import christmas.constant.menu.MenuConstant;
import christmas.model.Event;

public class Gift extends Event {
    public Gift() {
        discountPrice = MenuConstant.CHAMPAGNE.getManuPrice();
        eventName = EventName.EVENT_GIFT;
    }
}
