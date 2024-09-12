package christmas.dto.order;

import christmas.model.order.Order;
import christmas.model.order.Orders;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record OrdersDto(
        Map<String, Integer> orders
) {
    public static OrdersDto from(String[] orders) {
        Map<String, Integer> orderMap = Stream.of(orders)
                .map(order -> order.split("-"))
                .collect(Collectors.toMap(
                        split -> split[0].trim(),
                        split -> Integer.parseInt(split[1].trim())
                ));

        return new OrdersDto(orderMap);
    }

    public static OrdersDto from(Orders orders) {
        Map<String, Integer> orderMap = orders.orders().stream()
                .collect(Collectors.toMap(
                        order -> order.menu().getName(),
                        Order::count,
                        Integer::sum
                ));

        return new OrdersDto(orderMap);
    }

    public Orders toEntity() {
        List<Order> orderList = orders.entrySet().stream()
                .map(entry -> Order.from(entry.getKey(), entry.getValue()))
                .toList();

        return new Orders(orderList);
    }
}
