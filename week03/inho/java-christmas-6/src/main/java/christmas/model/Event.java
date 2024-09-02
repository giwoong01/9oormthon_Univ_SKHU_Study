package christmas.model;

import christmas.constant.event.EventName;

import java.text.NumberFormat;
import java.util.Locale;

import static christmas.view.OutputView.formatPrice;

public class Event {

    protected int discountPrice;
    protected EventName eventName;
    private static final int DISCOUNT_MULTIPLIER = -1;

    public int getDiscountPrice() {
        return discountPrice * DISCOUNT_MULTIPLIER;
    }

    @Override
    public String toString() {
        return eventName.getName() + ": " + formatPrice(discountPrice * DISCOUNT_MULTIPLIER) + "원";
    }
}
