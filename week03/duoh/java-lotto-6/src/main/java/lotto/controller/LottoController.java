package lotto.controller;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    // TODO: 일급 컬렉션
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
