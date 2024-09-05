package christmas.model.manager;

import christmas.constant.menu.MenuConstant;
import christmas.constant.menu.MenuKind;
import christmas.model.Badge;
import christmas.model.Event;
import christmas.model.Menu;
import christmas.model.discountevent.DDay;
import christmas.model.discountevent.Gift;
import christmas.model.discountevent.SpecialDay;
import christmas.model.discountevent.Week;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class EventManager {

    private final List<Event> events;
    private Badge badge;
    private boolean isGiftEvent = false;
    private static final int D_DAY_EVENT_LAST_DAY = 25;
    private static final int SPECIAL_EVENT_SUNDAY_OTHER_DAY = 25;
    private static final int GIFT_CONDITION_PRICE = 120000;
    private static final int EVENT_YEAR = 2023;
    private static final int EVENT_MONTH = 12;
    private static final int CALENDAR_MONTH_OFFSET = 1;
    private static final int SUNDAY = 1;
    private static final int DISCOUNT_MULTIPLIER = -1;

    public EventManager() {
        events = new ArrayList<>();
    }

    public void settingEvent(int date, List<Menu> menus, int beforeDiscountTotalPrice) {
        checkChristmasDDayEvent(date);
        checkWeekEvent(date, menus);
        checkSpecialDayEvent(date);
        checkGiftEvent(beforeDiscountTotalPrice);
    }

    public void setBadgeEvent(int benefit) {
        badge = new Badge(benefit * DISCOUNT_MULTIPLIER);
    }

    private void checkChristmasDDayEvent(int date) {
        if (date <= D_DAY_EVENT_LAST_DAY) {
            events.add(new DDay(date));
        }
    }

    private void checkWeekEvent(int date, List<Menu> menus) {
        if (menus.stream()
                .allMatch(this::checkWeekEventKindNotContain)) {
            return;
        }
        events.add(new Week(date, menus));
    }

    private boolean checkWeekEventKindNotContain(Menu menu) {
        return menu.isMenuKind(MenuKind.DESSERT) && menu.isMenuKind(MenuKind.MAIN);
    }

    private void checkSpecialDayEvent(int date) {
        if (getWeek(date) == SUNDAY || date == SPECIAL_EVENT_SUNDAY_OTHER_DAY) {
            events.add(new SpecialDay());
        }
    }

    private void checkGiftEvent(int price) {
        if (price >= GIFT_CONDITION_PRICE) {
            events.add(new Gift());
            isGiftEvent = true;
        }
    }

    public String containGift() {
        if (isGiftEvent) {
            return MenuConstant.CHAMPAGNE.getMenuName() + " 1개";
        }

        return "없음";
    }

    public static int getWeek(int date) {
        Calendar calendar = Calendar.getInstance();
        int year = EVENT_YEAR;
        int month = EVENT_MONTH - CALENDAR_MONTH_OFFSET;

        calendar.set(year, month, date);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public String getBadgeName() {
        return badge.getBadgeName();
    }

    public boolean isGiftEvent() {
        return isGiftEvent;
    }
}
