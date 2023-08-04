package onboarding.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super("일치하는 이메일이 없습니다.");
    }
}
