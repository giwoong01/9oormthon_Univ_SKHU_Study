package christmas.message;

import java.text.MessageFormat;

public enum OutputMessage {
    WELCOME_MESSAGE("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
    PREVIEW_EVENT_MESSAGE("12월 {0}일에 우테코 식당에서 받을 이벤트 혜택 미리 보기! %n %n"),
    ORDER_MENU_TITLE("<주문 메뉴>"),
    TOTAL_AMOUNT_BEFORE_DISCOUNT_TITLE("<할인 전 총주문 금액>"),
    FREE_GIFT_TITLE("<증정 메뉴>"),
    BENEFIT_DETAILS_TITLE("<혜택 내역>"),
    TOTAL_BENEFITS_AMOUNT_TITLE("<총혜택 금액>"),
    PAYMENT_AMOUNT_AFTER_DISCOUNT_TITLE("<할인 후 예상 결제 금액>"),
    BADGE_TITLE("<12월 이벤트 배지>"),
    WON("원"),
    BENEFIT_DETAILS("{0}: -{1}원%n"),
    ORDER_ITEM_FORMAT("{0} {1}개%n"),
    NOTHING("없음\n"),
    HYPHEN("-");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(this.message, args);
    }
}
