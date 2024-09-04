package lotto.controller;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LottoController {

    private final LottoResultManager manager;
    private final LottoAmount amount;
    private final List<Lotto> userLottos;

    public LottoController() {
        manager = new LottoResultManager();
        amount = setLottoAmount();
        userLottos = createLottos();
    }

    private LottoAmount setLottoAmount() {
            try {
                return new LottoAmount(InputView.inputPrice());
            } catch (IllegalArgumentException e) {
                return setLottoAmount();
            }
    }

    public void run() {
        printLottos();
        processLottoResult();
    }

    private List<Lotto> createLottos() {
        List<Lotto> returningLottos = new ArrayList<>();
        for (int lottoNumber = 0; lottoNumber < amount.calculateLottoCount(); lottoNumber++) {
            returningLottos.add(new Lotto(RandomNumbers.generateRandomNumbers()));
        }
        return returningLottos;
    }

    private void printLottos() {
        OutputView.printLottoCount(userLottos.size());
        userLottos.stream()
                .map(Lotto::getNumbers)
                .forEach(System.out::println);
    }

    private void processLottoResult() {
        try {
            List<Integer> winningNumbers = InputView.inputWinningNumbers();
            int bonusNumber = InputView.inputBonusNumber();
            Lotto winningLotto = createWinningLottoWithBonusNumber(winningNumbers, bonusNumber);
            printLottoResult(winningLotto, bonusNumber, userLottos);
        } catch (IllegalArgumentException e) {
            processLottoResult();
        }
    }

    private Lotto createWinningLottoWithBonusNumber(List<Integer> winningNumbers, int bonusNumber) {
        return new Lotto(winningNumbers, bonusNumber);
    }


    private void printLottoResult(Lotto winningLotto, int bonusNumber, List<Lotto> lottos) {
        Map<Rank,Integer> result = calculateWinningResult(winningLotto, lottos, bonusNumber);
        printWinningStatistics(result);
        printWinningResult(result);
    }

    private Map<Rank,Integer> calculateWinningResult(Lotto winningLotto,List<Lotto> lottos, int bonusNumber) {
        Map<Rank,Integer> result = setResultMap();
        for (Lotto lotto : lottos) {
            Rank rank = manager.getLottoRank(winningLotto, lotto, bonusNumber);
            result.put(rank, result.get(rank) + 1);
        }
        return result;
    }

    private Map<Rank,Integer> setResultMap(){
        Map<Rank,Integer> returningMap = new LinkedHashMap<>();
        for (Rank rank : Rank.values()) {
            returningMap.put(rank, 0);
        }
        return returningMap;
    }

    private void printWinningStatistics(Map<Rank,Integer> result){
        OutputView.printWinningStatisticsTwoLines();
        for (int i = Rank.values().length - 1; i >= 0; i--) {
            Rank.printResultMsg(Rank.values()[i],result.get(Rank.values()[i]));
        }
    }

    private void printWinningResult(Map<Rank,Integer> result){
        int totalWinningAmount = 0;
        for (Rank rank : result.keySet()) {
            totalWinningAmount += rank.getWinPrice() * result.get(rank);
        }
        OutputView.printEarningRate(manager.calculateEarningRate(amount.calculateLottoCount() * 1000, totalWinningAmount) * 100);
    }
}
