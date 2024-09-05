package lotto.controller;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        PurchaseAmount purchaseAmount = inputView.receivePurchaseAmount();

        List<Lotto> generatedLottos = LottoGenerator.generate(purchaseAmount);
        outputView.printGeneratedLottoNumbers(generatedLottos);

        List<Integer> winningNumberList = inputView.receiveLottoNumbers();
        int bonusNumber = inputView.receiveBonusNumber(winningNumberList);

        WinningNumbers winningNumbers = new WinningNumbers(winningNumberList, bonusNumber);

        LottoResult result = new LottoResult(generatedLottos, winningNumbers);
        outputView.printResults(result.getRankCounts(), result.getProfitRate(purchaseAmount.getPurchaseAmount()));
    }
}
