package lotto.controller;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;
import java.util.Map;

public class LottoController {

    private final LottoResultManager manager;
    private LottoAmount amount;
    private Lottos userLottos;

    public LottoController() {
        manager = new LottoResultManager();
    }

    public void run() {
        processLottoStart();
        processLottoResult();
    }

    private void processLottoStart() {
        amount = LottoAmount.setLottoAmount();
        userLottos = Lottos.generateLottos(amount.calculateLottoCount());
        OutputView.printLottos(userLottos);
    }

    private void processLottoResult() {
        try {
            Lotto winningNumbers = new Lotto(InputView.inputWinningNumbers());
            int bonusNumber = InputView.inputBonusNumber();
            Lotto winningLotto =  new Lotto(winningNumbers.getNumbers(), bonusNumber);
            displayLottoResult(winningLotto, bonusNumber, userLottos);
        } catch (IllegalArgumentException e) {
            processLottoResult();
        }
    }

    private void displayLottoResult(Lotto winningLotto, int bonusNumber, Lottos lottos) {
        Map<Rank,Integer> result = calculateWinningResult(winningLotto, lottos, bonusNumber);
        displayWinningStatistics(result);
        displayTotalEarningRate(result);
    }

    private Map<Rank,Integer> calculateWinningResult(Lotto winningLotto, Lottos lottos, int bonusNumber) {
        Map<Rank,Integer> result = manager.setResultMap();
        lottos.lottos().stream()
                .map(lotto -> manager.getLottoRank(winningLotto, lotto, bonusNumber))
                .forEach(rank -> result.put(rank, result.get(rank) + 1));
        return result;
    }

    private void displayWinningStatistics(Map<Rank,Integer> result){
        OutputView.printWinningStatisticsTwoLines();
        for (int i = Rank.values().length - 1; i >= 0; i--) {
            Rank.printResultMsg(Rank.values()[i],result.get(Rank.values()[i]));
        }
    }

    private void displayTotalEarningRate(Map<Rank, Integer> result) {
        int totalWinningAmount = result.keySet().stream()
                .mapToInt(rank -> rank.getWinPrice() * result.get(rank))
                .sum();
        OutputView.printEarningRate(manager.calculateEarningRate(amount.calculateLottoCount() * 1000, totalWinningAmount));
    }
}