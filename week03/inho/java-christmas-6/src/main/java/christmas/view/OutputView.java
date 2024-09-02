package christmas.view;


import christmas.constant.PrintMessage;
import christmas.model.Event;
import christmas.model.Menu;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OutputView {

    private static final int EVENT_MIN_PRICE = 10000;

    public void printNotice(int date, int beforeDiscountTotalPrice) {
        System.out.println("12월 " + date + "일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");

        if (beforeDiscountTotalPrice < EVENT_MIN_PRICE) {
            System.out.println(PrintMessage.ORDER_LESS_MIN_PRICE);
        }
        System.out.println();
    }

    public void printTotalMenu(List<Menu> menus) {
        System.out.println(PrintMessage.TOTAL_MENU.getMessage());
        for (Menu menu : menus) {
            System.out.println(menu.toString());
        }
        System.out.println();
    }

    public void printBeforeDiscountTotalPrice(int price) {
        System.out.println(PrintMessage.BEFORE_DISCOUNT_TOTAL_PRICE.getMessage());
        System.out.println(formatPrice(price) + "원");
        System.out.println();
    }

    public void printGiftMenu(String gift) {
        System.out.println(PrintMessage.GIFT_MENU.getMessage());
        System.out.println(gift);
        System.out.println();
    }

    public void printBenefit(List<Event> events) {
        System.out.println(PrintMessage.BENEFIT.getMessage());

        if (events.isEmpty()) {
            System.out.println("없음");
            System.out.println();

            return;
        }
        for (Event event : events) {
            System.out.println(event.toString());
        }
        System.out.println();
    }

    public void printTotalBenefitPrice(int benefit) {
        System.out.println(PrintMessage.TOTAL_BENEFIT_PRICE.getMessage());
        System.out.println(formatPrice(benefit) + "원");
        System.out.println();
    }

    public void printAfterDiscountTotalPrice(int price) {
        System.out.println(PrintMessage.AFTER_DISCOUNT_TOTAL_PRICE.getMessage());
        System.out.println(formatPrice(price) + "원");
        System.out.println();
    }

    public void printGiftBadge(String badgeString) {
        System.out.println(PrintMessage.EVENT_BADGE.getMessage());
        System.out.println(badgeString);
    }

    public static String formatPrice(int price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        
        return numberFormat.format(price);
    }
}

