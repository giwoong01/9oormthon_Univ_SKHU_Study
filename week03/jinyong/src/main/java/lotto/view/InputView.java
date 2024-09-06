package lotto.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.List;

public class InputView {

    private static final String INPUT_PRICE = "구입금액을 입력해 주세요.";
    private static final String INPUT_WINNING_NUMBERS ="\n당첨 번호를 입력해 주세요.";
    private static final String INPUT_BONUS_NUMBER = "\n보너스 번호를 입력해 주세요.";

    public static int inputPrice() {
        System.out.println(INPUT_PRICE);
        return convertToIntegerElement(Console.readLine());
    }

    public static List<Integer> inputWinningNumbers() {
        System.out.println(INPUT_WINNING_NUMBERS);
        return stringToNumbers(Console.readLine());
    }

    public static int inputBonusNumber() {
        System.out.println(INPUT_BONUS_NUMBER);
        return convertToIntegerElement(Console.readLine());
    }

    public static List<Integer> stringToNumbers(String input) {
        String[] numbers = input.split(",");
        return Arrays.stream(numbers).map(InputView::convertToIntegerElement).toList();
    }

    public static int convertToIntegerElement(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            Exceptions.msgOnlyInputNumber();
            throw new IllegalArgumentException();
        }
    }
}
