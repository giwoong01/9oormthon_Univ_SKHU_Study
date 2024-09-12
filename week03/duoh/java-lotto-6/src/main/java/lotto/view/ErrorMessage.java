package lotto.view;

public enum ErrorMessage {
    INVALID_PURCHASE_AMOUNT("[ERROR] 로또 구입 금액은 1,000원 단위로 입력해야 합니다."),
    INVALID_LOTTO_NUMBER_COUNT("[ERROR] 로또 번호는 6개여야 합니다."),
    INVALID_LOTTO_NUMBER_RANGE("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다."),
    DUPLICATE_LOTTO_NUMBER("[ERROR] 로또 번호는 중복되지 않아야 합니다."),
    DUPLICATE_BONUS_NUMBER("[ERROR] 보너스 번호는 당첨 번호와 중복되지 않아야 합니다."),
    NOT_A_NUMBER("[ERROR] 입력값은 숫자여야 합니다."),
    NOT_A_SINGLE_NUMBER("[ERROR] 보너스 번호는 단일 숫자여야 합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
