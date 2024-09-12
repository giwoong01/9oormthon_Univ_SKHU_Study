package christmas.model.discountevent;

import christmas.constant.event.EventName;
import christmas.constant.menu.MenuKind;
import christmas.model.Event;
import christmas.model.manager.EventManager;
import christmas.model.Menu;

import java.util.List;

public class Week extends Event {

    private final boolean isWeekend;
    private static final int WEEK_DISCOUNT_VALUE = 2023;
    private static final int SATURDAY = 7;
    private static final int FRIDAY = 6;

    public Week(int date, List<Menu> menus) {
        isWeekend = checkWeekends(date);
        discountPrice = getDiscountPrice(menus);
    }

    private boolean checkWeekends(int date) {
        int week = EventManager.getWeek(date);

        if (week == SATURDAY || week == FRIDAY) {
            eventName = EventName.EVENT_WEEKEND;

            return true;
        }
        eventName = EventName.EVENT_WEEKDAY;

        return false;
    }

    private int getDiscountPrice(List<Menu> menus) {
        if (isWeekend) {
            int mainOrderSize = calculateOrderSizeByManus(menus, MenuKind.MAIN);

            return mainOrderSize * WEEK_DISCOUNT_VALUE;
        }
        int dessertOrderSize = calculateOrderSizeByManus(menus, MenuKind.DESSERT);

        return dessertOrderSize * WEEK_DISCOUNT_VALUE;
    }

    private int calculateOrderSizeByManus(List<Menu> menus, MenuKind menuKind) {
        return menus.stream()
                .filter(menu -> menu.isMenuKind(menuKind))
                .mapToInt(Menu::getSize)
                .sum();
    }
}
