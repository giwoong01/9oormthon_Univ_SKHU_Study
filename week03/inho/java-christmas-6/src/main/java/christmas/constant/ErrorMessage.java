package christmas.constant;

public enum ErrorMessage {
    ERROR_DATE("유효하지 않은 날짜입니다."),
    ERROR_ORDER("유효하지 않은 주문입니다."),
    ERROR_OVER_MAX_SIZE("메뉴는 한 번에 최대 " + 20 + "개까지만 주문할 수 있습니다."),
    ERROR_OVER_ONLY_DRINK("음료만 주문 시, 주문할 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[ERROR] " + message + " 다시 입력해 주세요.";
    }
}
