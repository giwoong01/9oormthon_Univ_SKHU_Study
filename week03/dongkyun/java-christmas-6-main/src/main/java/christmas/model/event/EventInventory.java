package christmas.model.event;

public enum EventInventory {
    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT("평일 할인"),
    WEEKEND_DISCOUNT("주말 할인"),
    STAR_DISCOUNT("특별 할인"),
    GIVEAWAY_DISCOUNT("증정 이벤트");

    private final String name;

    EventInventory(String description) {
        this.name = description;
    }

    public String getName() {
        return name;
    }
}
