package onboarding.service;

import lombok.extern.slf4j.Slf4j;
import onboarding.dto.LoginResponse;
import onboarding.domain.MemberToken;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import onboarding.domain.Member;
import onboarding.exception.DuplicateEmailException;
import onboarding.exception.EmailNotFoundException;
import onboarding.exception.NotAcceptPassword;
import onboarding.repository.MemberRepository;
import onboarding.repository.MemberTokenRepository;
import onboarding.security.JwtProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtProvider jwtProvider;

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
        if (!encoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new NotAcceptPassword("비밀번호가 틀립니다");
        }
        String token = jwtProvider.createToken(member.getId());
        MemberToken memberToken = memberTokenRepository.save(new MemberToken(token));
        member.updateToken(memberToken);
        return LoginResponse.of(memberToken.getJwtToken());
    }

    public Member findMemberById(long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NotFoundMember::new);
    }
}
