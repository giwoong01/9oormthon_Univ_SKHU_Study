package christmas.dto;

public class EventInfoDto {

    private final String eventName;
    private final String discountPrice;

    public EventInfoDto(String eventName, String discountPrice) {
        this.eventName = eventName;
        this.discountPrice = discountPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    @Override
    public String toString() {
        return eventName + ": " + discountPrice + "원";
    }
}
