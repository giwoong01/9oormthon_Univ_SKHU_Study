package christmas.view;

import christmas.dto.event.EventsDto;
import christmas.dto.order.OrdersDto;
import christmas.message.OutputMessage;
import java.text.NumberFormat;
import java.util.Locale;

public class OutputView {
    private static final OutputView instance = new OutputView();
    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

    private OutputView() {
    }

    public static OutputView getInstance() {
        return instance;
    }

    public void printIntroduction() {
        System.out.println(OutputMessage.WELCOME_MESSAGE.getMessage());
    }

    public void printPreviewEventBenefit(int date) {
        System.out.printf(OutputMessage.PREVIEW_EVENT_MESSAGE.getMessage(date));
    }

    public void printOrderMenu(OrdersDto ordersDto) {
        System.out.println(OutputMessage.ORDER_MENU_TITLE.getMessage());
        ordersDto.orders().forEach((menu, count)
                -> System.out.printf(OutputMessage.ORDER_ITEM_FORMAT.getMessage(menu, count)));
        System.out.println();
    }

    public void printTotalAmountBeforeDiscount(int totalAmount) {
        System.out.println(OutputMessage.TOTAL_AMOUNT_BEFORE_DISCOUNT_TITLE.getMessage());
        System.out.println(formatPrice(totalAmount) + OutputMessage.WON.getMessage());
        System.out.println();
    }

    public void printFreeGift(String freeGift, int count) {
        System.out.println(OutputMessage.FREE_GIFT_TITLE.getMessage());
        if (count == 0) {
            System.out.println(freeGift);
            System.out.println();
            return;
        }
        System.out.printf(OutputMessage.ORDER_ITEM_FORMAT.getMessage(freeGift, count));
        System.out.println();
    }

    public void printBenefitDetails(EventsDto eventsDto) {
        System.out.println(OutputMessage.BENEFIT_DETAILS_TITLE.getMessage());

        if (eventsDto.events().isEmpty()) {
            System.out.println(OutputMessage.NOTHING.getMessage());
            return;
        }
        eventsDto.events().forEach((eventName, benefit)
                -> System.out.printf(OutputMessage.BENEFIT_DETAILS.getMessage(eventName, benefit)));
        System.out.println();
    }

    public void printTotalBenefitsAmount(int amount) {
        System.out.println(OutputMessage.TOTAL_BENEFITS_AMOUNT_TITLE.getMessage());
        if (amount == 0) {
            System.out.println(formatPrice(amount) + OutputMessage.WON.getMessage());
            System.out.println();
            return;
        }
        System.out.println(formatPriceWithHyphen(amount) + OutputMessage.WON.getMessage());
        System.out.println();
    }

    public void printPaymentAmountAfterDiscount(int amount) {
        System.out.println(OutputMessage.PAYMENT_AMOUNT_AFTER_DISCOUNT_TITLE.getMessage());
        System.out.println(formatPrice(amount) + OutputMessage.WON.getMessage());
        System.out.println();
    }

    public void printBadge(String name) {
        System.out.println(OutputMessage.BADGE_TITLE.getMessage());
        System.out.print(name);
    }

    private String formatPriceWithHyphen(int price) {
        return OutputMessage.HYPHEN.getMessage() + numberFormat.format(price);
    }

    private String formatPrice(int price) {
        return numberFormat.format(price);
    }
}
