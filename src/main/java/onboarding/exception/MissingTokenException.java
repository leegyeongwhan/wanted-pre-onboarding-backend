package onboarding.exception;

public class MissingTokenException extends RuntimeException {
    public MissingTokenException(String message) {
        super(message);
    }
}
