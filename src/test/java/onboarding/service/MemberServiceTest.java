package onboarding.service;

import onboarding.domain.Member;
import onboarding.domain.MemberToken;
import onboarding.dto.LoginResponse;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import onboarding.repository.MemberRepository;
import onboarding.repository.MemberTokenRepository;
import onboarding.security.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("회원가입 테스트")
    void test_signup() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("example123@naver.com", "password123");
        Member member = new Member(signUpRequest.getEmail(), signUpRequest.getPassword());

        Long fakeMemberId = 1L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);

        given(encoder.encode(signUpRequest.getPassword())).willReturn("encodedPassword");
        given(memberRepository.save(any())).willReturn(member);
        given(memberRepository.findById(fakeMemberId)).willReturn(Optional.of(member));
        //when
        Long newMemberId = memberService.signup(signUpRequest);

        //then
        Member findMember = memberRepository.findById(newMemberId).get();
        Assertions.assertEquals(member.getId(), findMember.getId());
        Assertions.assertEquals(member.getPassword(), findMember.getPassword());
        Assertions.assertEquals(member.getEmail(), findMember.getEmail());
    }

    @Test
    @DisplayName("로그인 테스트")
    void test_login() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        Member member = new Member("test@example.com", "encoded_password");

        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(encoder.matches(any(), any())).thenReturn(true);
        when(jwtProvider.createToken(anyLong())).thenReturn("mocked_token");
        when(memberTokenRepository.save(any())).thenReturn(new MemberToken("mocked_token"));

        // Act
        LoginResponse response = memberService.login(request);

        // Assert
        assertNotNull(response);
        Assertions.assertEquals("mocked_token", response.getJwtToken());

        verify(memberRepository, times(1)).findByEmail("test@example.com");
        verify(encoder, times(1)).matches("password", "encoded_password");
        verify(jwtProvider, times(1)).createToken(1L);
        verify(memberTokenRepository, times(1)).save(any());
        verify(member, times(1)).updateToken(any()); // Make sure your Member class has this m
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void testLoginEmailNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest("nonexistent@example.com", "password");
        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("비밀번호 인코딩 테스트")
    public void test_passwordIncord() {
    }
}
