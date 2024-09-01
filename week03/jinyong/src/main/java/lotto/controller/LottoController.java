package lotto.controller;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LottoController {

    private static LottoAmount amount;
    private static List<Lotto> userLottos;
    private static final LottoResultManager manager = new LottoResultManager();

    public void run() {
        processLottoCreation();
        processLottoResult();
    }

    private void processLottoCreation() {
        amount = inputLottoAmount();
        userLottos = createLottos(amount);
        printLottosCreationArea(userLottos);
    }

    private void processLottoResult() {
        Lotto winningLotto = inputWinningLotto();
        int bonusNumber = InputView.inputBonusNumber();
        Lotto.validateDuplicatedBonusNumber(winningLotto.getNumbers(), bonusNumber);
        printLottoResultArea(winningLotto, userLottos, bonusNumber, manager, amount);
    }

    private static LottoAmount inputLottoAmount() {
        return new LottoAmount(InputView.inputPrice());
    }


    public void printLottosCreationArea(List<Lotto> lottos) {
        OutputView.printLottoCount(lottos.size());
        lottos.stream()
                .map(Lotto::getNumbers)
                .forEach(System.out::println);
    }

    private static Lotto inputWinningLotto() {
        return new Lotto(InputView.inputWinningNumbers());
    }


    public void printLottoResultArea(Lotto winningLotto, List<Lotto> lottos, int bonusNumber, LottoResultManager manager, LottoAmount amount) {
        Map<Rank,Integer> result = calculateWinningResult(winningLotto, lottos, bonusNumber, manager);
        printWinningStatistics(result);
        printWinningResult(result, manager, amount);
    }

    public Map<Rank,Integer> calculateWinningResult(Lotto winningLotto, List<Lotto> lottos, int bonusNumber, LottoResultManager manager) {
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

    public List<Lotto> createLottos(LottoAmount amount) {
        List<Lotto> returningLottos = new ArrayList<>();
        for (int lottoNumber = 0; lottoNumber < amount.calculateLottoCount(); lottoNumber++) {
            returningLottos.add(new Lotto(RandomNumbers.generateRandomNumbers()));
        }
        return returningLottos;
    }

    public void printWinningStatistics(Map<Rank,Integer> result){
        OutputView.printWinningStatisticsTwoLines();
        for (int i = Rank.values().length - 1; i >= 0; i--) {
            Rank.printResultMsg(Rank.values()[i],result.get(Rank.values()[i]));
        }
    }

    public void printWinningResult(Map<Rank,Integer> result, LottoResultManager manager, LottoAmount amount){
        int totalWinningAmount = 0;
        for (Rank rank : result.keySet()) {
            totalWinningAmount += rank.getWinPrice() * result.get(rank);
        }
        OutputView.printEarningRate(manager.calculateEarningRate(amount.calculateLottoCount() * 1000, totalWinningAmount));
    }
}
