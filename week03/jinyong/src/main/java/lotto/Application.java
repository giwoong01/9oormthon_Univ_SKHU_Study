package lotto;

import lotto.controller.LottoController;
import lotto.model.LottoResultManager;

public class Application {
    public static void main(String[] args) {
        LottoController controller = new LottoController();
        controller.run();
    }
}
