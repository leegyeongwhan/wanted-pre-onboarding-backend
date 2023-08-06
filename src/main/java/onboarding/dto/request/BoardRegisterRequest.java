package onboarding.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.domain.Board;
import onboarding.domain.Member;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BoardRegisterRequest {

    @NotNull
    private String title;
    @NotNull
    private String content;

    public BoardRegisterRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity(Member member) {
        return new Board(title, content, member);
    }
}
