package christmas.constant.event;

public enum EventName {
    EVENT_D_DAY("크리스마스 디데이 할인"),
    EVENT_WEEKDAY("평일 할인"),
    EVENT_WEEKEND("주말 할인"),
    EVENT_SPECIAL_DAY("특별 할인"),
    EVENT_GIFT("증정 이벤트");

    private final String name;

    EventName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
