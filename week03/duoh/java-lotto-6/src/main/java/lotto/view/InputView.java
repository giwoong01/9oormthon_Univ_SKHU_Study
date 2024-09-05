package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.model.PurchaseAmount;
import lotto.model.WinningNumbers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputView {

    public PurchaseAmount receivePurchaseAmount() {
        while (true) {
            try {
                System.out.println("구입금액을 입력해 주세요.");
                int amount = Integer.parseInt(Console.readLine().trim());
                return new PurchaseAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Integer> receiveLottoNumbers() {
        while (true) {
            try {
                System.out.println("\n당첨 번호를 입력해 주세요.");
                String numbers = Console.readLine();
                List<Integer> winningNumbers = Arrays.stream(numbers.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                WinningNumbers.validateWinningNumbers(winningNumbers);
                return winningNumbers;
            } catch (NumberFormatException e) {
                System.out.println(ErrorMessage.NOT_A_NUMBER.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int receiveBonusNumber(List<Integer> winningNumbers) {
        while (true) {
            try {
                System.out.println("\n보너스 번호를 입력해 주세요.");
                int bonusNumber = Integer.parseInt(Console.readLine().trim());
                WinningNumbers.validateBonusNumber(winningNumbers, bonusNumber);
                return bonusNumber;
            } catch (NumberFormatException e) {
                System.out.println(ErrorMessage.NOT_A_SINGLE_NUMBER.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
