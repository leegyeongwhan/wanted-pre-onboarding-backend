package onboarding.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.dto.request.SignUpRequest;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
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
