package lotto.model;

import lotto.view.Exceptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {

    private static final int STANDARD_LOTTO_SIZE = 6;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateMatchOfSize(numbers);
        validateDuplicatedNumbers(numbers);
        this.numbers = numbers;
    }

    public Lotto(List<Integer> numbers, int bonusNumber) {
        validateMatchOfSize(numbers);
        validateDuplicatedNumbers(numbers);
        this.numbers = numbers;
        validateDuplicatedBonusNumber(bonusNumber);
    }

    private void validateMatchOfSize(List<Integer> numbers) throws IllegalArgumentException {
        if (numbers.size() != STANDARD_LOTTO_SIZE) {
            Exceptions.msgNotSixNumbers();
            throw new IllegalArgumentException();
        }
    }

    private void validateDuplicatedNumbers(List<Integer> numbers) throws IllegalArgumentException {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            Exceptions.msgDuplicateNumbers();
            throw new IllegalArgumentException();
        }
    }

    private void validateDuplicatedBonusNumber(int bonusNumber) throws IllegalArgumentException {
        if (numbers.stream().anyMatch(number -> number == bonusNumber)) {
            Exceptions.msgDuplicateBonusNumber();
            throw new IllegalArgumentException();
        }
    }

    public boolean containsWinningNumber(int number) {
        return numbers.contains(number);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
