package onboarding.domain;

import javax.persistence.*;

@Entity
public class MemberToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_token_id")
    private Long id;

    private String jwtToken;
}
