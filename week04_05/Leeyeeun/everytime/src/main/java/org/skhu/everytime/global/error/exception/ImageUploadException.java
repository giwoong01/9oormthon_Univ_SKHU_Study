package org.skhu.everytime.global.error.exception;

// 이미지 업로드 예외 처리
public class ImageUploadException extends RuntimeException {
    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageUploadException(String message) {
        super(message);
    }
}
