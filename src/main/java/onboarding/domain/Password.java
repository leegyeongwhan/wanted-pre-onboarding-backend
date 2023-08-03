package onboarding.domain;

import javax.persistence.*;

@Entity
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paasword_id")
    private Long id;

    private String hashedPassword;
}
