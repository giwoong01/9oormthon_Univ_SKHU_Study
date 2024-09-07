package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.model.PurchaseAmount;
import lotto.model.WinningNumbers;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InputView {
    // TODO: MVC 패턴에 맞게 수정 필요
    public PurchaseAmount receivePurchaseAmount() {
        return handleInput(() -> {
            System.out.println("구입금액을 입력해 주세요.");
            int amount = Integer.parseInt(Console.readLine().trim());
            return new PurchaseAmount(amount);
        }, ErrorMessage.INVALID_PURCHASE_AMOUNT);
    }

    public List<Integer> receiveLottoNumbers() {
        return handleInput(() -> {
            System.out.println("\n당첨 번호를 입력해 주세요.");
            String numbers = Console.readLine();
            List<Integer> winningNumbers = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            WinningNumbers.validateWinningNumbers(winningNumbers);
            return winningNumbers;
        }, ErrorMessage.NOT_A_NUMBER);
    }

    public int receiveBonusNumber(List<Integer> winningNumbers) {
        return handleInput(() -> {
            System.out.println("\n보너스 번호를 입력해 주세요.");
            int bonusNumber = Integer.parseInt(Console.readLine().trim());
            WinningNumbers.validateBonusNumber(winningNumbers, bonusNumber);
            return bonusNumber;
        }, ErrorMessage.NOT_A_SINGLE_NUMBER);
    }

    private <T> T handleInput(Supplier<T> supplier, ErrorMessage errorMessage) {
        while (true) {
            try {
                return supplier.get();
            } catch (NumberFormatException e) {
                System.out.println(errorMessage.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}