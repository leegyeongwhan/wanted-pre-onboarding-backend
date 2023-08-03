package onboarding.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;
}
