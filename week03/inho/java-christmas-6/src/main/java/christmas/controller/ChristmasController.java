package christmas.controller;

import christmas.model.manager.EventManager;
import christmas.model.manager.OrderManager;
import christmas.model.Menu;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.List;

public class ChristmasController {

    private List<Menu> orderMenus;
    private int date;
    private int beforeDiscountTotalPrice;
    private int benefitPrice;
    private final InputView inputView;
    private final OutputView outputView;
    private final EventManager eventManager;
    private final OrderManager orderManager;
    private static final int EVENT_MIN_PRICE = 10000;

    public ChristmasController() {
        inputView = new InputView();
        outputView = new OutputView();
        eventManager = new EventManager();
        orderManager = new OrderManager();
    }

    public void order() {
        date = inputView.readExpectedVisitDate();
        orderMenus = inputView.readMenusAndNumbers();

        beforeDiscountTotalPrice = orderManager.getBeforeDiscountTotalPrice(orderMenus);

        if(beforeDiscountTotalPrice >= EVENT_MIN_PRICE) {
            eventManager.settingEvent(date, orderMenus, beforeDiscountTotalPrice);
        }

        benefitPrice = orderManager.getBenefitTotalPrice(eventManager.getEvents());
        eventManager.setBadgeEvent(benefitPrice);

        printResult();
    }

    private void printResult() {
        outputView.printNotice(date, beforeDiscountTotalPrice);
        outputView.printTotalMenu(orderMenus);
        outputView.printBeforeDiscountTotalPrice(beforeDiscountTotalPrice);
        outputView.printGiftMenu(eventManager.containGift());
        outputView.printBenefit(eventManager.getEvents());
        outputView.printTotalBenefitPrice(benefitPrice);
        outputView.printAfterDiscountTotalPrice(orderManager.getAfterDiscountTotalPrice
                (eventManager.isGiftEvent(),beforeDiscountTotalPrice,benefitPrice));
        outputView.printGiftBadge(eventManager.getBadgeName());
    }
}
