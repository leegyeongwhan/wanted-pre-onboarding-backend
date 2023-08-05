package onboarding.dto.request;

import lombok.Getter;
import onboarding.domain.Board;
import onboarding.domain.Member;

import javax.validation.constraints.NotNull;

@Getter
public class BoardRegisterRequest {

    @NotNull
    private String title;
    @NotNull
    private String content;

    public Board toEntity(Member member) {
        return new Board(title, content, member);
    }
}
