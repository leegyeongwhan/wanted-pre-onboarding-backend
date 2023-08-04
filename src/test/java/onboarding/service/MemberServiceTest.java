package onboarding.service;

import onboarding.domain.Member;
import onboarding.dto.request.SignUpRequest;
import onboarding.repository.MemberRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private MemberService memberService;

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
}
