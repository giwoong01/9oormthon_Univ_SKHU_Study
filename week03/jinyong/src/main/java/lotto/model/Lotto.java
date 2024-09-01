package lotto.model;

import lotto.view.Exceptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        validateDuplicatedNumbers(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            Exceptions.msgNotSixNumbers();
            throw new IllegalArgumentException();
        }
    }

    private void validateDuplicatedNumbers(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            Exceptions.msgDuplicateNumbers();
            throw new IllegalArgumentException();
        }
    }

    public static void validateDuplicatedBonusNumber(List<Integer> numbers, int bonusNumber) {
        if (numbers.stream().anyMatch(number -> number == bonusNumber)) {
            Exceptions.msgDuplicateBonusNumber();
            throw new IllegalArgumentException();
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
