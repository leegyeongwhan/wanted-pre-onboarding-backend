package onboarding.service;

import onboarding.domain.Member;
import onboarding.domain.MemberToken;
import onboarding.dto.LoginResponse;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import onboarding.exception.DuplicateEmailException;
import onboarding.exception.EmailNotFoundException;
import onboarding.exception.NotAcceptPassword;
import onboarding.repository.MemberRepository;
import onboarding.repository.MemberTokenRepository;
import onboarding.security.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberTokenRepository memberTokenRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private MemberService memberService;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void testSignup() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("example123@naver.com", "password123");
        Member savedMember = new Member(signUpRequest.getEmail(),signUpRequest.getPassword());
        when(memberRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // when
        Long memberId = memberService.signup(signUpRequest);

        // then
        assertNotNull(memberId);
        verify(memberRepository, times(1)).existsByEmail(signUpRequest.getEmail());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("회원가입_이메일중복")
    void testLogin() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@example.com", "password");
        when(memberRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        // then
        assertThrows(DuplicateEmailException.class, () -> memberService.signup(signUpRequest));
        verify(memberRepository, times(1)).existsByEmail(signUpRequest.getEmail());
        verify(memberRepository, times(0)).save(any(Member.class));
    }

    @Test
    @DisplayName("로그인 성공테스트")
    void loginSuccess() {

        //given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        Member member = new Member("test@example.com", "password");
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        when(encoder.matches(loginRequest.getPassword(), member.getPassword())).thenReturn(true);
        when(jwtProvider.createToken(member.getId())).thenReturn("testToken");
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(new MemberToken("testToken"));

        // when
        LoginResponse loginResponse = memberService.login(new LoginRequest("test@example.com", "password"));

        // then
        assertNotNull(loginResponse);
        assertEquals("testToken", loginResponse.getJwtToken());
        verify(memberRepository, times(1)).findByEmail("test@example.com");
        verify(memberTokenRepository, times(1)).save(any(MemberToken.class));
    }


    @Test
    @DisplayName("로그인 이메일 실패테스트")
    void testLoginEmailNotFound() {
        String email = "test@example.com";
        String password = "password";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());


        assertThrows(EmailNotFoundException.class, () -> memberService.login(new LoginRequest(email, password)));
        verify(memberRepository, times(1)).findByEmail(email);
        //실패시 토큰을 만들면안된다.
        verify(memberTokenRepository, times(0)).save(any(MemberToken.class));
    }

    @Test
    @DisplayName("로그인 틀린 비밀번호")
    void testNotAcceptPassword() {
        String email = "test@example.com";
        String password = encoder.encode("password");
        Member member = new Member(email, "wrongPassword");
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(encoder.matches(password, member.getPassword())).thenReturn(false);

        assertThrows(NotAcceptPassword.class, () -> memberService.login(new LoginRequest(email, password)));
        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberTokenRepository, times(0)).save(any(MemberToken.class));
    }
}
