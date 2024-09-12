package lotto.domain;

import java.util.List;


public class Lotto {
    private final List<Integer> numbers;
    private final LottoValidator lottoValidator = new LottoValidator();

    public Lotto(List<Integer> numbers) {
        lottoValidator.validate(numbers);
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return List.copyOf(numbers);
    }

    public int countMatchNumber(List<Integer> winningNumbers) {
        return (int) numbers.stream().filter(winningNumbers::contains).count();
    }

    public boolean isBonusMatchNumber(int bonusNumber) {
        return numbers.contains(bonusNumber);
    }

}
