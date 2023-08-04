package onboarding.service;

import lombok.extern.slf4j.Slf4j;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import onboarding.domain.Member;
import onboarding.domain.Password;
import onboarding.exception.DuplicateEmailException;
import onboarding.repository.MemberRepository;
import onboarding.repository.PasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long signup(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateEmailException("이메일 중복입니다.");
        }

        Password password = new Password(encoder.encode(signUpRequest.getPassword()));
        Member member = new Member(signUpRequest.getEmail(), passwordRepository.save(password));

        return memberRepository.save(member).getId();
    }

    @Transactional
    public void login(LoginRequest loginRequest) {

    }

    public Member findMemberById(long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }
}
