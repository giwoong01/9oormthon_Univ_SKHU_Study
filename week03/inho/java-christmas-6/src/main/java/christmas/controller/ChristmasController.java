package christmas.controller;

import christmas.dto.EventInfoDto;
import christmas.dto.MenuInfoDto;
import christmas.model.Menu;
import christmas.model.manager.EventManager;
import christmas.model.manager.OrderManager;
import christmas.validator.DateValidator;
import christmas.validator.MenuValidator;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

public class ChristmasController {

    private List<Menu> orderMenus;
    private int date;
    private int beforeDiscountTotalPrice;
    private int benefitPrice;
    private final InputView inputView;
    private final OutputView outputView;
    private final EventManager eventManager;
    private final OrderManager orderManager;
    private final MenuValidator menuValidator;
    private final DateValidator dateValidator;
    private static final int EVENT_MIN_PRICE = 10000;

    public ChristmasController() {
        inputView = new InputView();
        outputView = new OutputView();
        eventManager = new EventManager();
        orderManager = new OrderManager();
        menuValidator = new MenuValidator();
        dateValidator = new DateValidator();
    }

    public void order() {
        date = getValidDate();
        orderMenus = getValidMenus();

        beforeDiscountTotalPrice = orderManager.getBeforeDiscountTotalPrice(orderMenus);

        settingEventIfTotalPriceIsOverEventMinPrice();

        benefitPrice = orderManager.getBenefitTotalPrice(eventManager.getEvents());
        eventManager.setBadgeEvent(benefitPrice);

        printResult();
    }

    private int getValidDate() {
        do {
            String dateInput = inputView.readExpectedVisitDate();
            if (dateValidator.validateDate(dateInput)) {
                return dateValidator.getIntegerDate();
            }
            System.out.println(dateValidator.getErrorMessage());
        } while (true);
    }

    private List<Menu> getValidMenus() {
        do {
            String menuInput = inputView.readMenusAndNumbers();
            if (menuValidator.validateMenu(menuInput)) {
                return menuValidator.getMenus();
            }
            System.out.println(menuValidator.getErrorMessage());
        } while (true);
    }

    private void settingEventIfTotalPriceIsOverEventMinPrice() {
        if (beforeDiscountTotalPrice >= EVENT_MIN_PRICE) {
            eventManager.settingEvent(date, orderMenus, beforeDiscountTotalPrice);
        }
    }

    private void printResult() {
        List<MenuInfoDto> menuInfoDtos = orderMenus.stream()
                .map(menu -> new MenuInfoDto(menu.getMenuConstant(), menu.getSize()))
                .toList();

        List<EventInfoDto> eventInfoDtos = eventManager.getEvents().stream()
                .map(event -> new EventInfoDto(event.getEventName(), OutputView.formatPrice(event.getDiscountPrice())))
                .toList();

        outputView.printNotice(date, beforeDiscountTotalPrice);
        outputView.printTotalMenu(menuInfoDtos);
        outputView.printBeforeDiscountTotalPrice(beforeDiscountTotalPrice);
        outputView.printGiftMenu(eventManager.containGift());
        outputView.printBenefit(eventInfoDtos);
        outputView.printTotalBenefitPrice(benefitPrice);
        outputView.printAfterDiscountTotalPrice(orderManager.getAfterDiscountTotalPrice
                (eventManager.isGiftEvent(), beforeDiscountTotalPrice, benefitPrice));
        outputView.printGiftBadge(eventManager.getBadgeName());
    }
}
