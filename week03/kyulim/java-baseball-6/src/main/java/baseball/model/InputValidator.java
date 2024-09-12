package baseball.model;

import java.util.Arrays;
import java.util.HashSet;

public class InputValidator {
    public void validateInput(String input) {
        validateLength(input);
        validateNumberRange(input);
        validateDuplicates(input);
    }

    private void validateLength(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("입력은 3자리여야 합니다.");
        }
    }

    private void validateNumberRange(String input) {
        for (char ch : input.toCharArray()) {
            if (ch < '1' || ch > '9') {
                throw new IllegalArgumentException("입력은 1에서 9 사이의 숫자여야 합니다.");
            }
        }
    }

    private void validateDuplicates(String input) {
        if (hasDuplicateCharacters(input)) {
            throw new IllegalArgumentException("중복된 숫자가 있습니다.");
        }
    }

    public boolean hasDuplicateCharacters(String input) {
        return input.length() != new HashSet<>(Arrays.asList(input.split(""))).size();
    }
}
