package lotto.model;

import lotto.view.Exceptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {

    private static final int STANDARD_LOTTO_SIZE = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateMatchOfSize(numbers);
        validateDuplicatedNumbers(numbers);
        validateNumberRange(numbers);
        this.numbers = numbers;
    }

    public Lotto(List<Integer> numbers, int bonusNumber) {
        validateMatchOfSize(numbers);
        validateDuplicatedNumbers(numbers);
        validateBonusNumberRange(bonusNumber);
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

    private void validateNumberRange(List<Integer> numbers) throws IllegalArgumentException {
        if (numbers.stream().anyMatch(number -> number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER)) {
            Exceptions.msgOutOfRange();
            throw new IllegalArgumentException();
        }
    }

    private void validateBonusNumberRange(int bonusNumber) throws IllegalArgumentException {
        if (bonusNumber < MIN_LOTTO_NUMBER || bonusNumber > MAX_LOTTO_NUMBER) {
            Exceptions.msgBonusOutOfRange();
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
