package christmas.controller;

import christmas.dto.event.EventsDto;
import christmas.dto.order.OrdersDto;
import christmas.model.calender.DateInfo;
import christmas.model.customer.Customer;
import christmas.model.order.Orders;
import christmas.util.RetryUtil;
import christmas.view.InputView;
import christmas.view.OutputView;

public class OrderController {
    private final InputView inputView;
    private final OutputView outputView;

    public OrderController() {
        this.inputView = InputView.getInstance();
        this.outputView = OutputView.getInstance();
        outputView.printIntroduction();
    }

    public void startOrder() {
        DateInfo dateInfo = getDate();
        Orders orders = getOrders();
        Customer customer = getCustomer(dateInfo, orders);

        printOrderResult(dateInfo, orders, customer);
    }

    private DateInfo getDate() {
        return new DateInfo(RetryUtil.retryReadDate(inputView::readDate));
    }

    private Orders getOrders() {
        OrdersDto ordersDto = RetryUtil.retryReadMenu(inputView::readMenu);

        return ordersDto.toEntity();
    }

    private Customer getCustomer(DateInfo dateInfo, Orders orders) {
        return new Customer(dateInfo, orders);
    }

    private void printOrderResult(DateInfo dateInfo, Orders orders, Customer customer) {
        outputView.printPreviewEventBenefit(dateInfo.getDay());
        outputView.printOrderMenu(OrdersDto.from(orders));
        outputView.printTotalAmountBeforeDiscount(orders.getTotalPrice());
        outputView.printFreeGift(customer.getFreeGiftEvent().getFreeGift(),
                customer.getFreeGiftEvent().getFreeGiftCount());
        outputView.printBenefitDetails(EventsDto.from(customer));
        outputView.printTotalBenefitsAmount(customer.getReceivedDiscounts());
        outputView.printPaymentAmountAfterDiscount(customer.getExpectPaymentAmount(orders));
        outputView.printBadge(customer.getBadge().getName());
    }
}
