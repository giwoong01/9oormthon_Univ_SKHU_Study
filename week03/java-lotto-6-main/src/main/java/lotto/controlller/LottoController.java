package lotto.controlller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lotto.domain.Lotto;
import lotto.domain.LottoGenerator;
import lotto.domain.LottoValidator;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

    private static final int FIRST_PRIZE = 2000000000;
    private static final int SECOND_PRIZE = 30000000;
    private static final int THIRD_PRIZE = 1500000;
    private static final int FOURTH_PRIZE = 50000;
    private static final int FIFTH_PRIZE = 5000;

    public LottoController() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    private List<Lotto> generateLottoList(int amount) {
        List<Lotto> lottoList = new ArrayList<>();
        for (int i = 0; i < amount / 1000; i++) {
            lottoList.add(new Lotto(LottoGenerator.generateWinningNumber()));
        }
        return lottoList;
    }

    private int getLottoPrice() {
        int amount = inputView.printStartMessage();
        LottoValidator.validateLottoPrice(amount);
        return amount;
    }

    public void gameStart() {
        int amount = getLottoPrice();
        List<Lotto> lottoList = generateLottoList(amount);
        outputView.printLottoNumbers(lottoList);

        List<Integer> winningNumbers = getWinningNumbers();
        int bonusNumber = getBonusNumber(winningNumbers);

        Map<String, Integer> results = calculateWinning(lottoList, winningNumbers, bonusNumber);
        double earningsRate = calculateRate(results, amount);

        outputView.printResults(results, earningsRate);
    }

    private List<Integer> getWinningNumbers() {
        return inputView.inputPickNumber();
    }

    private int getBonusNumber(List<Integer> winningNumbers) {
        int bonusNumber = inputView.inputBonusNumber();
        LottoValidator.validateBonusNumber(bonusNumber, winningNumbers);  // 유효성 검사 호출
        return bonusNumber;
    }

    private void updateResult(Map<String, Integer> results, int matchCount, boolean bonusMatch) {
        if (matchCount == 6) {
            results.put("1등", results.get("1등") + 1);
            return;
        }
        if (matchCount == 5 && bonusMatch) {
            results.put("2등", results.get("2등") + 1);
            return;
        }
        if (matchCount == 5) {
            results.put("3등", results.get("3등") + 1);
            return;
        }
        if (matchCount == 4) {
            results.put("4등", results.get("4등") + 1);
            return;
        }
        if (matchCount == 3) {
            results.put("5등", results.get("5등") + 1);
        }
    }

    private Map<String, Integer> calculateWinning(List<Lotto> lottoList, List<Integer> winningNumbers, int bonusNumber) {
        Map<String, Integer> results = new HashMap<>();
        results.put("1등", 0);
        results.put("2등", 0);
        results.put("3등", 0);
        results.put("4등", 0);
        results.put("5등", 0);

        for (Lotto lotto : lottoList) {
            int matchCount = lotto.countMatchNumber(winningNumbers);
            boolean bonusMatch = lotto.isBonusMatchNumber(bonusNumber);
            updateResult(results, matchCount, bonusMatch);
        }
        return results;
    }

    private double calculateRate(Map<String, Integer> results, int totalAmount) {
        int totalEarnings = results.get("1등") * FIRST_PRIZE +
                results.get("2등") * SECOND_PRIZE +
                results.get("3등") * THIRD_PRIZE +
                results.get("4등") * FOURTH_PRIZE +
                results.get("5등") * FIFTH_PRIZE;
        return (double) totalEarnings / totalAmount * 100;
    }
}
