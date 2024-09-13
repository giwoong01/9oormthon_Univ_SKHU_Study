package christmas.model.manager;

import christmas.constant.menu.MenuConstant;
import christmas.model.Event;
import christmas.model.Menu;

import java.util.List;

public class OrderManager {
    public int getBeforeDiscountTotalPrice(List<Menu> menus) {
        return menus.stream()
                .mapToInt(Menu::getManuPrice)
                .sum();
    }

    public int getBenefitTotalPrice(List<Event> events) {
        return events.stream()
                .mapToInt(Event::getDiscountPrice)
                .sum();
    }

    public int getAfterDiscountTotalPrice(boolean isGift, int beforeDiscountTotalPrice, int benefitPrice) {
        if(isGift) {
            return beforeDiscountTotalPrice + benefitPrice + MenuConstant.CHAMPAGNE.getManuPrice();
        }

        return beforeDiscountTotalPrice + benefitPrice;
    }
}
