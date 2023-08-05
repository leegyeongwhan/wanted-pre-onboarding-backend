package onboarding.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardListResponse {

    List<BoardDetailResponse> list;

    private BoardListResponse(List<BoardDetailResponse> boards) {
        this.list = boards;
    }

    public static BoardListResponse from(List<BoardDetailResponse> boards) {
        return new BoardListResponse(boards);
    }
}
