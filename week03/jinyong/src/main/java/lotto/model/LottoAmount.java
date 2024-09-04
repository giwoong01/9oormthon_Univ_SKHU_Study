package lotto.model;

import lotto.view.Exceptions;

public class LottoAmount {
    private static final int LOTTO_PRICE = 1000;
    private final int price;

    public LottoAmount(int inputPrice) {
        validateValidPrice(inputPrice);
        validateIndivisiblePrice(inputPrice);
        this.price = inputPrice;
    }

    public int calculateLottoCount() {
        return price / LOTTO_PRICE;
    }

    private void validateValidPrice(int price) {
        if (price < LOTTO_PRICE) {
            Exceptions.msgMustBeOverThousand();
            throw new IllegalArgumentException();
        }
    }
    private void validateIndivisiblePrice(int price) {
        if (price % LOTTO_PRICE != 0) {
            Exceptions.msgNotDivisibleByThousand();
            throw new IllegalArgumentException();
        }
    }
}
