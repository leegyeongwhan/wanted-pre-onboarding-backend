package onboarding.repository;

import onboarding.domain.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {
}
