package onboarding.service;

public class NotAcceptPassword extends RuntimeException {
    public NotAcceptPassword(String massge) {
        super(massge);
    }
}
