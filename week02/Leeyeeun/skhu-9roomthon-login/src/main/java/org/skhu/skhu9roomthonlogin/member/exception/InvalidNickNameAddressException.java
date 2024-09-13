package org.skhu.skhu9roomthonlogin.member.exception;

public class InvalidNickNameAddressException extends RuntimeException{
    public InvalidNickNameAddressException(String message) {
        super(message);
    }

    public InvalidNickNameAddressException() {
        this("닉네임 형식이 올바르지 않습니다.");
    }
}
