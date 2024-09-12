package christmas.controller;

import static christmas.message.MessageConstants.CHAMPAGNE_ONE;
import static christmas.message.MessageConstants.DISCOUNT_FORMAT;
import static christmas.message.MessageConstants.EMPTY_STRING;
import static christmas.message.MessageConstants.MINUS_WON_FORMAT;
import static christmas.message.MessageConstants.NOT_THING;
import static christmas.message.MessageConstants.TEN_THOUSAND;
import static christmas.message.MessageConstants.WEEKDAY;
import static christmas.message.MessageConstants.WEEKEND;
import static christmas.message.MessageConstants.WON_FORMAT;
import static christmas.message.MessageConstants.ZERO;

import christmas.domain.Buyer;
import christmas.domain.Restaurant;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.text.NumberFormat;

public class GameMachine {
    private final Restaurant restaurant;
    private final InputView inputView;
    private final OutputView outputView;

    private final NumberFormat numberFormat;
    private int calculateTotalDiscount;

    public GameMachine() {
        this.restaurant = new Restaurant();
        this.inputView = new InputView();
        this.outputView = new OutputView();
        numberFormat = NumberFormat.getInstance();
        calculateTotalDiscount = ZERO;
    }

    public void start() {
        int inputDate = inputView.inputVisitDate();
        Buyer buyer = inputBuyerInfo();

        outputView.previewEventBenefitMessage(inputDate);
        showOrderMenu(buyer);

        boolean isPresentation = restaurant.isPresentation(buyer.getTotalPrice());
        showPresentedMenu(isPresentation);
        applyBenefits(buyer, inputDate, isPresentation);
        showTotalBenefitAmount();
        calculateFinalPrice(buyer, isPresentation);
        outputView.eventBadgeMessage(restaurant, calculateTotalDiscount);
    }

    private Buyer inputBuyerInfo() {
        return inputView.inputMenuAndCounts();
    }

    private void showOrderMenu(Buyer buyer) {
        outputView.orderMenuMessage(buyer);
        outputView.totalPriceBeforeDiscountMessage(buyer);
    }

    private void showPresentedMenu(boolean isPresentation) {
        String presentationsMenu = NOT_THING;

        if (isPresentation) {
            presentationsMenu = CHAMPAGNE_ONE;
        }
        outputView.presentedMenuMessage(presentationsMenu);
    }

    private void applyBenefits(Buyer buyer, int inputDate, boolean isPresentation) {
        benefitsDetails(buyer, inputDate, isPresentation);
    }

    private void showTotalBenefitAmount() {
        String totalBenefitAmount = String.format(WON_FORMAT, calculateTotalDiscount);

        if (calculateTotalDiscount > ZERO) {
            totalBenefitAmount = String.format(MINUS_WON_FORMAT, numberFormat.format(calculateTotalDiscount));
        }
        outputView.totalBenefitAmount(totalBenefitAmount);
    }

    private void calculateFinalPrice(Buyer buyer, boolean isPresentation) {
        discountAfterTotalPrice(buyer, calculateTotalDiscount, isPresentation);
    }

    private void benefitsDetails(Buyer buyer, int inputDate, boolean isPresentation) {
        outputView.benefitsDetailsMessage();
        if (buyer.getTotalPrice() >= TEN_THOUSAND) {
            applyChristmasDayDiscount(inputDate);
            applyWeekdayOrWeekendDiscount(inputDate, buyer);
            applyStarDiscount(inputDate);
            applyPresentationDiscount(isPresentation);
            return;
        }

        outputView.printNone();
    }

    private void applyChristmasDayDiscount(int inputDate) {
        if (restaurant.isChristmasDiscount(inputDate)) {
            calculateTotalDiscount += restaurant.christmasDiscount(inputDate);
            outputView.christmasDayDiscountMessage(inputDate, restaurant);
        }
    }

    private void applyWeekdayOrWeekendDiscount(int inputDate, Buyer buyer) {
        if (restaurant.isWeekday(inputDate)) {
            weekdayDiscount(buyer);
        }

        if (restaurant.isWeekend(inputDate)) {
            weekendDiscount(buyer);
        }
    }

    private void weekdayDiscount(Buyer buyer) {
        String weekdayDiscount = calculateDiscount(restaurant.weekdayDiscount(buyer), WEEKDAY);
        outputView.weekdayOrWeekendDiscountMessage(weekdayDiscount);
    }

    private void weekendDiscount(Buyer buyer) {
        String weekendDiscount = calculateDiscount(restaurant.weekendDiscount(buyer), WEEKEND);
        outputView.weekdayOrWeekendDiscountMessage(weekendDiscount);
    }

    private String calculateDiscount(int discountAmount, String discountType) {
        if (discountAmount == ZERO) {
            return EMPTY_STRING;
        }
        calculateTotalDiscount += discountAmount;
        return String.format(DISCOUNT_FORMAT, discountType, numberFormat.format(discountAmount));
    }

    private void applyStarDiscount(int inputDate) {
        if (restaurant.isStar(inputDate)) {
            calculateTotalDiscount += restaurant.starDiscount();
            outputView.starDiscountMessage(restaurant);
        }
    }

    private void applyPresentationDiscount(boolean isPresentation) {
        if (isPresentation) {
            calculateTotalDiscount += restaurant.presentationDiscount();
            outputView.presentationDiscountMessage(restaurant);
        }
    }

    private void discountAfterTotalPrice(Buyer buyer, int calculateTotalDiscount, boolean isPresentation) {
        String discountAfterTotalPrice = String.format(WON_FORMAT,
                numberFormat.format(
                        buyer.getTotalPrice() - calculateTotalDiscount));
        if (isPresentation) {
            discountAfterTotalPrice = String.format(WON_FORMAT,
                    numberFormat.format(
                            buyer.getTotalPrice() - calculateTotalDiscount + restaurant.presentationDiscount()));
        }
        outputView.discountAfterTotalPriceMessage(discountAfterTotalPrice);
    }

}
