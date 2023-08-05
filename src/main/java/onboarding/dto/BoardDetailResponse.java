package onboarding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.domain.Board;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private String title;
    private String content;

    private BoardDetailResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static BoardDetailResponse from(Board board) {
        return new BoardDetailResponse(board.getTitle(), board.getContent());
    }
}
