package lotto.controlller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
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
        for(int i=0;i<amount/1000;i++){
            lottoList.add(new Lotto(LottoGenerator.generateWinningNumber()));
        }
        return lottoList;
    }

    private int getLottoPrice() {
        return inputView.printStartMessage();
    }

    public void gameStart() {
        int amount = getLottoPrice();
        List<Lotto> lottoList = generateLottoList(amount);
        outputView.printLottoNumbers(lottoList);

        List<Integer> winningNumbers = getWinningNumbers();
        int bonusNumber = getBonusNumber(winningNumbers);

        Map<String, Integer> results = calculateWinnings(lottoList, winningNumbers, bonusNumber);
        double earningsRate = calculateRate(results, amount);

        outputView.printResults(results, earningsRate);
    }

    private List<Integer> getWinningNumbers() {
        return inputView.inputPickNumber();
    }

    private int getBonusNumber(List<Integer> winningNumbers) {
        int bonusNumber = inputView.inputBonusNumber();
        if (winningNumbers.contains(bonusNumber)) {
            throw new RuntimeException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
        return bonusNumber;
    }

    private Map<String, Integer> calculateWinnings(List<Lotto> lottoList, List<Integer> winningNumbers, int bonusNumber) {
        Map<String, Integer> results = new HashMap<>();
        results.put("1등", 0);
        results.put("2등", 0);
        results.put("3등", 0);
        results.put("4등", 0);
        results.put("5등", 0);

        for (Lotto lotto : lottoList) {
            int matchCount = lotto.countMatchNumber(winningNumbers);
            boolean bonusMatch = lotto.isBonusMatchNumber(bonusNumber);

            if (matchCount == 6) {
                results.put("1등", results.get("1등") + 1);
            } else if (matchCount == 5 && bonusMatch) {
                results.put("2등", results.get("2등") + 1);
            } else if (matchCount == 5) {
                results.put("3등", results.get("3등") + 1);
            } else if (matchCount == 4) {
                results.put("4등", results.get("4등") + 1);
            } else if (matchCount == 3) {
                results.put("5등", results.get("5등") + 1);
            }
        }

        return results;
    }

    private double calculateRate(Map<String, Integer> results, int totalAmount) {
        int totalEarnings = results.get("1등") * 2000000000 +
                results.get("2등") * 30000000 +
                results.get("3등") * 1500000 +
                results.get("4등") * 50000 +
                results.get("5등") * 5000;
        return (double) totalEarnings / totalAmount * 100;
    }

}
