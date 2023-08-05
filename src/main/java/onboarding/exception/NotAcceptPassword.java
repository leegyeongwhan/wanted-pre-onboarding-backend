package onboarding.exception;

public class NotAcceptPassword extends RuntimeException {
    public NotAcceptPassword(String massge) {
        super(massge);
    }
}
