package onboarding.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;
    private String email;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_token_id")
    private MemberToken memberToken;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void updateToken(MemberToken memberToken) {
        this.memberToken = memberToken;
    }
}
