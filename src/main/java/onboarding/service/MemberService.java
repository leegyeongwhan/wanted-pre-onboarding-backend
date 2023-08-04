package onboarding.service;

import lombok.extern.slf4j.Slf4j;
import onboarding.controller.LoginResponse;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import onboarding.domain.Member;
import onboarding.exception.DuplicateEmailException;
import onboarding.exception.EmailNotFoundException;
import onboarding.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long signup(SignUpRequest signUpRequest) {
        validateEmail(signUpRequest);
        Member member = new Member(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        return memberRepository.save(member).getId();
    }

    private void validateEmail(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateEmailException("이메일 중복입니다.");
        }
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(EmailNotFoundException::new);
        if (member.getPassword().equals(loginRequest.getPassword())) {

        }
        throw new NotAcceptPassword("비밀번호가 틀립니다");
    }

    public Member findMemberById(long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }
}
