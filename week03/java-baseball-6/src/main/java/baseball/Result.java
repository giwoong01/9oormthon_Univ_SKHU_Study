package baseball;

import java.util.List;

public class Result {

    // 스트라이크와 볼 계산
    public int[] calculateScore(List<Integer> computer, List<Integer> userNumbers) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 3; i++) {
            if (computer.get(i).equals(userNumbers.get(i))) {
                strikes++;
            } else if (computer.contains(userNumbers.get(i))) {
                balls++;
            }
        }

        return new int[]{balls, strikes};
    }

    // 결과 출력
    public void printResult(int[] result) {
        int balls = result[0];
        int strikes = result[1];

        if (balls == 0 && strikes == 0) {
            System.out.println("낫싱");
        } else {
            StringBuilder sb = new StringBuilder();
            if (balls > 0) {
                sb.append(balls).append("볼 ");
            }
            if (strikes > 0) {
                sb.append(strikes).append("스트라이크");
            }
            System.out.println(sb.toString().trim());
        }
    }
}
