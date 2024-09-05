package lotto.domain;

import java.util.List;


public class Lotto {
    private final List<Integer> numbers;

    private static final int MIN_SIZE = 6;

    public Lotto(List<Integer> numbers) {
        LottoValidator.validate(numbers);
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }



    public int countMatchNumber(List<Integer> winningNumbers) {
        return (int) numbers.stream().filter(winningNumbers::contains).count();
    }

    public boolean isBonusMatchNumber(int bonusNumber) {
        return numbers.contains(bonusNumber);
    }

}
