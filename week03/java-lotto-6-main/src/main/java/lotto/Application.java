package lotto;

import lotto.controlller.LottoController;

public class Application {
    public static void main(String[] args) {
        LottoController lottoController = new LottoController();
        lottoController.gameStart();
    }
}
