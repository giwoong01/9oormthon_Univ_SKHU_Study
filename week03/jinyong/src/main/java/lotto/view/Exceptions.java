package lotto.view;

public enum Exceptions {
    DUPLICATED_NUMBER("[ERROR] 로또 번호에 중복된 숫자가 있습니다."),
    DUPLICATED_BONUS_NUMBER("[ERROR] 보너스 번호는 당첨 번호와 중복된 숫자가 없어야 합니다."),
    NOT_SIX_NUMBERS("[ERROR] 로또 번호는 6개여야 합니다."),
    MUST_BE_OVER_1000("[ERROR] 로또 구매 금액은 1000원 이상이어야 합니다."),
    NOT_DIVISIBLE_BY_THOUSAND("[ERROR] 로또 구매 금액은 1000원 단위로만 가능합니다."),
    ONLY_INPUT_NUMBER("[ERROR] 숫자만 입력할 수 있습니다."),
    OUT_OF_RANGE("[ERROR] 로또 번호는 1부터 45까지의 숫자만 가능합니다."),
    BONUS_OUT_OF_RANGE("[ERROR] 보너스 번호는 1부터 45까지의 숫자만 가능합니다.")

    ;

    private final String message;

    Exceptions(String message) {
        this.message = message;
    }

    private String getMessage() {
        return message;
    }

    public static void msgDuplicateNumbers() {
        System.out.println(DUPLICATED_NUMBER.getMessage());
    }

    public static void msgDuplicateBonusNumber() {
        System.out.println(DUPLICATED_BONUS_NUMBER.getMessage());
    }

    public static void msgNotSixNumbers() {
        System.out.println(NOT_SIX_NUMBERS.getMessage());
    }

    public static void msgMustBeOverThousand() {
        System.out.println(MUST_BE_OVER_1000.getMessage());
    }

    public static void msgNotDivisibleByThousand() {
        System.out.println(NOT_DIVISIBLE_BY_THOUSAND.getMessage());
    }

    public static void msgOnlyInputNumber() {
        System.out.println(ONLY_INPUT_NUMBER.getMessage());
    }

    public static void msgOutOfRange() {
        System.out.println(OUT_OF_RANGE.getMessage());
    }

    public static void msgBonusOutOfRange() {
        System.out.println(BONUS_OUT_OF_RANGE.getMessage());
    }
}