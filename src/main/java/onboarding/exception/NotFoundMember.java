package onboarding.exception;

public class NotFoundMember extends RuntimeException{

    public NotFoundMember() {
        super("계정을 찾을수 없습니다.");
    }
}
