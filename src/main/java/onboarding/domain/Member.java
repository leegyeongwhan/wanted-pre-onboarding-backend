package onboarding.domain;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    private Password password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_token_id")
    private MemberToken memberToken;
}
