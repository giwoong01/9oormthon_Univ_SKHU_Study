package baseball.model;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class BaseballReferee {

    public List<Integer> generateComputerNumbers() {
        Set<Integer> numberSet = new HashSet<>();
        while (numberSet.size() < 3) {
            numberSet.add(Randoms.pickNumberInRange(1, 9));
        }
        return new ArrayList<>(numberSet);
    }

    public String getResult(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int strikes = countStrikes(computerNumbers, userNumbers);
        int balls = countBalls(computerNumbers, userNumbers);

        return getNothingOrBallOrStrike(strikes, balls);
    }

    private String getNothingOrBallOrStrike(int strikes, int balls) {
        String result = "";
        if (strikes == 0 && balls == 0)
            result = "낫싱";
        if (balls > 0)
            result += balls + "볼 ";
        if (strikes > 0)
            result += strikes + "스트라이크";

        return result.trim();
    }

    private int countStrikes(List<Integer> computerNumber, List<Integer> userNumber) {
        int strikes = 0;
        for (int i = 0; i < 3; i++) {
            if (computerNumber.get(i).equals(userNumber.get(i)))
                strikes++;
        }
        return strikes;
    }

    private int countBalls(List<Integer> computerNumber, List<Integer> userNumber) {
        int balls = 0;
        for (int i = 0; i < 3; i++) {
            if (computerNumber.get(i).equals(userNumber.get(i)))
                continue;

            if (computerNumber.contains(userNumber.get(i)))
                balls++;
        }
        return balls;
    }

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
