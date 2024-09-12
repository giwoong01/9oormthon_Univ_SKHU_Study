package christmas.message;

public enum ErrorMessage {
    INVALID_DAY_ERROR("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER_ERROR("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."),
    DUPLICATE_MENU_ERROR("[ERROR] 중복된 메뉴가 주문되었습니다."),
    INVALID_COUNT_ERROR("[ERROR] 잘못된 개수입니다."),
    NON_DRINK_MENU_REQUIRED_ERROR("[ERROR] 음료만 주문할 수 없습니다. 다른 메뉴를 포함해 주세요."),
    NON_EXIST_FREE_GIFT("[ERROR] 사은품이 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
