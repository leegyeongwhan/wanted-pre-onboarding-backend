package onboarding.exception;

public class NotFoundBoard extends RuntimeException {

    public NotFoundBoard() {
        super("게시글을 찾을수없습니다");
    }
}
