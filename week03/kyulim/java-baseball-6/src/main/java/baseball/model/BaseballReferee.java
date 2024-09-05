package baseball.model;

import java.util.List;

public class BaseballReferee {
    // 볼, 스트라이크 출력
    public String getResult(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int strikes = countStrikes(computerNumbers, userNumbers);
        int balls = countBalls(computerNumbers, userNumbers);

        String result = "";
        if (strikes == 0 && balls == 0) {
            result = "낫싱";
        }

        if (balls > 0)
            result += balls + "볼 ";
        if (strikes > 0)
            result += strikes + "스트라이크";

        return result.trim();
    }

    // 스트라이크 개수 정하기
    private int countStrikes(List<Integer> computerNumber, List<Integer> userNumber) {
        int strikes = 0;
        for (int i = 0; i < 3; i++) {
            if (computerNumber.get(i).equals(userNumber.get(i)))
                strikes++;
        }
        return strikes;
    }

    // 볼 개수 정하기
    private int countBalls(List<Integer> computerNumber, List<Integer> userNumber) {
        int balls = 0;
        for (int i = 0; i < 3; i++) {
            if (computerNumber.contains(userNumber.get(i)))
                balls++;

            if (computerNumber.get(i).equals(userNumber.get(i)))
                balls--;
        }
        return balls;
    }
}
