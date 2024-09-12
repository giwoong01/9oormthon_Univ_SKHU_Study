package baseball;

public class Validator {

    // 사용자 입력을 검증
    public void validateInput(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("1~9까지의 숫자 3개를 입력해야 합니다.");
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '1' || c > '9') {
                throw new IllegalArgumentException("1~9까지의 숫자 3개를 입력해야 합니다.");
            }
        }

        if (input.charAt(0) == input.charAt(1) || input.charAt(1) == input.charAt(2) || input.charAt(0) == input.charAt(2)) {
            throw new IllegalArgumentException("숫자는 서로 달라야 합니다.");
        }
    }
}
