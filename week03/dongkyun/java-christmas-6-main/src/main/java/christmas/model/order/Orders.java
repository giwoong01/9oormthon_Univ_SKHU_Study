package christmas.model.order;

import java.util.List;

public record Orders(List<Order> orders) {
    public int getTotalPrice() {
        return orders.stream()
                .mapToInt(order -> order.menu().getPrice() * order.count())
                .sum();
    }
}
