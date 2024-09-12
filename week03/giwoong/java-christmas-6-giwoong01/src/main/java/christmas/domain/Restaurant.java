package christmas.domain;

import static christmas.message.MessageConstants.FIVE_THOUSAND;
import static christmas.message.MessageConstants.NOT_THING;
import static christmas.message.MessageConstants.ONE;
import static christmas.message.MessageConstants.ONE_HUNDRED;
import static christmas.message.MessageConstants.ONE_HUNDRED_TWENTY_THOUSAND;
import static christmas.message.MessageConstants.ONE_THOUSAND;
import static christmas.message.MessageConstants.SANTA;
import static christmas.message.MessageConstants.STAR;
import static christmas.message.MessageConstants.TEN_THOUSAND;
import static christmas.message.MessageConstants.TREE;
import static christmas.message.MessageConstants.TWENTY_FIVE_THOUSAND;
import static christmas.message.MessageConstants.TWENTY_SIX;
import static christmas.message.MessageConstants.TWENTY_THOUSAND;
import static christmas.message.MessageConstants.TWO_THOUSAND_TWENTY_THREE;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuCategory;
import java.util.Arrays;
import java.util.List;

public class Restaurant {
    public boolean isPresentation(int totalPrice) {
        return totalPrice >= ONE_HUNDRED_TWENTY_THOUSAND;
    }

    public boolean isChristmasDiscount(int date) {
        return date < TWENTY_SIX;
    }

    public boolean isWeekend(int date) {
        for (Integer weekendDate : Date.WEEKEND.getDates()) {
            if (weekendDate.equals(date)) {
                return true;
            }
        }

        return false;
    }

    public boolean isWeekday(int date) {
        for (Integer weekendDate : Date.WEEKDAY.getDates()) {
            if (weekendDate.equals(date)) {
                return true;
            }
        }

        return false;
    }

    public boolean isStar(int date) {
        for (Integer starDate : Date.STAR.getDates()) {
            if (starDate.equals(date)) {
                return true;
            }
        }

        return false;
    }

    public int christmasDiscount(int date) {
        int discount = ONE_THOUSAND;

        discount += ONE_HUNDRED * (date - ONE);

        return discount;
    }

    public int weekdayDiscount(Buyer buyer) {
        List<MenuAndCount> menuAndCounts = buyer.getMenuAndCounts();

        int count = menuAndCounts.stream()
                .filter(mac -> Arrays.stream(Menu.values())
                        .anyMatch(menu -> menu.getCategory().equals(MenuCategory.DESSERT)
                                && menu.getName().contains(mac.getMenuName())))
                .mapToInt(MenuAndCount::getCount)
                .sum();

        return TWO_THOUSAND_TWENTY_THREE * count;
    }

    public int weekendDiscount(Buyer buyer) {
        List<MenuAndCount> menuAndCounts = buyer.getMenuAndCounts();

        int count = menuAndCounts.stream()
                .filter(mac -> Arrays.stream(Menu.values())
                        .anyMatch(menu -> menu.getCategory().equals(MenuCategory.MAIN)
                                && menu.getName().contains(mac.getMenuName())))
                .mapToInt(MenuAndCount::getCount)
                .sum();

        return TWO_THOUSAND_TWENTY_THREE * count;
    }

    public int starDiscount() {
        return ONE_THOUSAND;
    }

    public int presentationDiscount() {
        return TWENTY_FIVE_THOUSAND;
    }

    public String eventBadge(int calculateTotalDiscount) {
        if (calculateTotalDiscount >= TWENTY_THOUSAND) {
            return SANTA;
        }

        if (calculateTotalDiscount >= TEN_THOUSAND) {
            return TREE;
        }

        if (calculateTotalDiscount >= FIVE_THOUSAND) {
            return STAR;
        }

        return NOT_THING;
    }
}
