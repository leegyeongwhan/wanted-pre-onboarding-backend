package onboarding.service;

import lombok.extern.slf4j.Slf4j;
import onboarding.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import onboarding.domain.Member;
import onboarding.domain.Password;
import onboarding.exception.DuplicateEmailException;
import onboarding.repository.MemberRepository;
import onboarding.repository.PasswordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;

    @Transactional
    public void signup(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateEmailException("이메일 중복입니다.");
        }

        Password password = new Password(signUpRequest.getPassword());
        Member member = new Member(signUpRequest.getEmail(), passwordRepository.save(password));

        memberRepository.save(member);
    }
}
