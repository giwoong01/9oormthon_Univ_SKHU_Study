package lotto.model;

import lotto.view.ErrorMessage;

public class PurchaseAmount {
    public static final int LOTTO_PRICE = 1000;
    private final int purchaseAmount;

    public PurchaseAmount(int amount) {
        validatePurchaseAmount(amount);
        this.purchaseAmount = amount;
    }

    private void validatePurchaseAmount(int amount) {
        if (amount <= 0 || amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
        }
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }
}
