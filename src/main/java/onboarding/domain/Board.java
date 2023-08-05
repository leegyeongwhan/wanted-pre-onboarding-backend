package onboarding.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.exception.BoardAccessException;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    @Column(name = "created_date", updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private LocalDateTime createdDate;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void validateAccess(long userId) {
        if (this.member.getId() != userId) {
            throw new BoardAccessException("접근 불가능한 게시글 입니다.");
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deleted(boolean deleted) {
        this.deleted = deleted;
    }
}
