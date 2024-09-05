package lotto.model;

import lotto.view.ErrorMessage;

import java.util.*;

public class Lotto {
    public static final int MIN_LOTTO_NUMBER = 1;
    public static final int MAX_LOTTO_NUMBER = 45;
    public static final int LOTTO_NUMBER_COUNT = 6;
    private List<Integer> lottoNumbers;

    public Lotto(List<Integer> lottoNumbers) {
        validate(lottoNumbers);
        List<Integer> sortedLottoNumbers = new ArrayList<>(lottoNumbers);
        Collections.sort(sortedLottoNumbers);
        this.lottoNumbers = sortedLottoNumbers;
    }

    private void validate(List<Integer> lottoNumbers) {
        validateLottoSize(lottoNumbers);
        validateNumberRange(lottoNumbers);
        validateDuplicates(lottoNumbers);
    }

    private void validateLottoSize(List<Integer> lottoNumbers) {
        if (lottoNumbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER_COUNT.getMessage());
        }
    }

    private void validateNumberRange(List<Integer> lottoNumbers) {
        for (int lottoNumber : lottoNumbers) {
            if (lottoNumber < MIN_LOTTO_NUMBER || lottoNumber > MAX_LOTTO_NUMBER) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER_RANGE.getMessage());
            }
        }
    }

    private void validateDuplicates(List<Integer> lottoNumbers) {
        Set<Integer> distinctNumbers = new HashSet<>(lottoNumbers);

        if (distinctNumbers.size() != lottoNumbers.size()) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_LOTTO_NUMBER.getMessage());
        }
    }

    public List<Integer> getLottoNumbers() {
        return lottoNumbers;
    }
}
