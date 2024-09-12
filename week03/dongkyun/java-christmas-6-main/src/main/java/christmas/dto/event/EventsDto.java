package christmas.dto.event;

import christmas.model.customer.Customer;
import christmas.model.event.Event;
import java.util.Map;
import java.util.stream.Collectors;

public record EventsDto(
        Map<String, Integer> events
) {
    public static EventsDto from(Customer customer) {
        Map<String, Integer> eventMap = customer.getEvents().stream()
                .filter(event -> event.getBenefit() > 0)
                .collect(Collectors.toMap(
                        event -> event.getEvent().getName(),
                        Event::getBenefit
                ));

        return new EventsDto(eventMap);
    }
}
