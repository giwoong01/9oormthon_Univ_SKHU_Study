package lotto.controlller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lotto.domain.Lotto;
import lotto.domain.LottoGenerator;
import lotto.domain.LottoValidator;
import lotto.domain.Prize;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

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

        Map<Prize, Integer> results = calculateWinning(lottoList, winningNumbers, bonusNumber);
        double earningsRate = calculateRate(results, amount);

        outputView.printResults(results, earningsRate);
    }

    private List<Integer> getWinningNumbers() {
        return inputView.inputPickNumber();
    }

    private int getBonusNumber(List<Integer> winningNumbers) {
        int bonusNumber = inputView.inputBonusNumber();
        LottoValidator.validateBonusNumber(bonusNumber, winningNumbers);
        return bonusNumber;
    }

    private Map<Prize, Integer> calculateWinning(List<Lotto> lottoList, List<Integer> winningNumbers, int bonusNumber) {
        Map<Prize, Integer> results = new HashMap<>();
        for (Prize prize : Prize.values()) {
            results.put(prize, 0);
        }

        for (Lotto lotto : lottoList) {
            int matchCount = lotto.countMatchNumber(winningNumbers);
            boolean bonusMatch = lotto.isBonusMatchNumber(bonusNumber);
            Prize prize = Prize.valueOf(matchCount, bonusMatch);
            results.put(prize, results.get(prize) + 1);
        }
        return results;
    }

    private double calculateRate(Map<Prize, Integer> results, int totalAmount) {
        int totalEarnings = results.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
        return (double) totalEarnings / totalAmount * 100;
    }
}
