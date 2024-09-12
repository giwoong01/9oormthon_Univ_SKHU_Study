package christmas.view;

import static christmas.message.MessageConstants.BENEFIT_DETAILS;
import static christmas.message.MessageConstants.CHRISTMAS_DISCOUNT_FORMAT;
import static christmas.message.MessageConstants.DECEMBER_EVENT_BADGE;
import static christmas.message.MessageConstants.EVENT_PREVIEW_FORMAT;
import static christmas.message.MessageConstants.EXPECTED_PAYMENT_AFTER_DISCOUNT;
import static christmas.message.MessageConstants.GIFT_EVENT_FORMAT;
import static christmas.message.MessageConstants.GIFT_MENU;
import static christmas.message.MessageConstants.NOT_THING;
import static christmas.message.MessageConstants.ORDER_MENU;
import static christmas.message.MessageConstants.STAR_DISCOUNT_FORMAT;
import static christmas.message.MessageConstants.TOTAL_BEFORE_DISCOUNT;
import static christmas.message.MessageConstants.TOTAL_BENEFIT_AMOUNT;
import static christmas.message.MessageConstants.WON;

import christmas.domain.Buyer;
import christmas.domain.Restaurant;
import java.text.NumberFormat;

public class OutputView {
    private final NumberFormat numberFormat;

    public OutputView() {
        numberFormat = NumberFormat.getInstance();
    }

    public void previewEventBenefitMessage(int inputDate) {
        System.out.printf(EVENT_PREVIEW_FORMAT, inputDate);
    }

    public void orderMenuMessage(Buyer buyer) {
        System.out.println(ORDER_MENU);
        System.out.println(buyer);
    }

    public void totalPriceBeforeDiscountMessage(Buyer buyer) {
        System.out.println(TOTAL_BEFORE_DISCOUNT);
        System.out.println(numberFormat.format(buyer.getTotalPrice()) + WON);
    }

    public void presentedMenuMessage(String presentationsMenu) {
        System.out.println(GIFT_MENU);
        System.out.println(presentationsMenu);
    }

    public void christmasDayDiscountMessage(int inputDate, Restaurant restaurant) {
        System.out.printf(CHRISTMAS_DISCOUNT_FORMAT, numberFormat.format(restaurant.christmasDiscount(inputDate)));
    }

    public void weekdayOrWeekendDiscountMessage(String weekdayOrWeekendDiscount) {
        System.out.print(weekdayOrWeekendDiscount);
    }

    public void starDiscountMessage(Restaurant restaurant) {
        System.out.printf(STAR_DISCOUNT_FORMAT, numberFormat.format(restaurant.starDiscount()));
    }

    public void presentationDiscountMessage(Restaurant restaurant) {
        System.out.printf(GIFT_EVENT_FORMAT,
                numberFormat.format(restaurant.presentationDiscount()));
    }

    public void benefitsDetailsMessage() {
        System.out.println(BENEFIT_DETAILS);
    }

    public void totalBenefitAmount(String totalBenefitAmount) {
        System.out.println(TOTAL_BENEFIT_AMOUNT);
        System.out.print(totalBenefitAmount);
    }

    public void discountAfterTotalPriceMessage(String discountAfterTotalPrice) {
        System.out.println(EXPECTED_PAYMENT_AFTER_DISCOUNT);
        System.out.print(discountAfterTotalPrice);
    }

    public void eventBadgeMessage(Restaurant restaurant, int calculateTotalDiscount) {
        System.out.println(DECEMBER_EVENT_BADGE);
        System.out.println(restaurant.eventBadge(calculateTotalDiscount));
    }

    public void printNone() {
        System.out.println(NOT_THING);
    }
}
