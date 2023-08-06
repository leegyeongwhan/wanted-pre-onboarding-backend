package onboarding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.domain.Board;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long id;
    private String title;
    private String content;

    public BoardDetailResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static BoardDetailResponse from(Board board) {
        return new BoardDetailResponse(board.getId(), board.getTitle(), board.getContent());
    }
}
