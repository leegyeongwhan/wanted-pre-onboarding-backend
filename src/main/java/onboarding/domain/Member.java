package onboarding.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    private Password password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_token_id")
    private MemberToken memberToken;

    public Member(String email, Password password) {
        this.email = email;
        this.password = password;
    }
}
