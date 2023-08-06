package onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import onboarding.domain.Member;
import onboarding.domain.MemberToken;
import onboarding.dto.LoginResponse;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import onboarding.repository.MemberRepository;
import onboarding.repository.MemberTokenRepository;
import onboarding.security.AuthorizationExtractor;
import onboarding.security.JwtProvider;
import onboarding.security.LoginInterceptor;
import onboarding.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberTokenRepository memberTokenRepository;
    @MockBean
    private MemberRepository memberRepository;

    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private AuthorizationExtractor authExtractor;

    @MockBean
    private LoginInterceptor loginInterceptor;

    @Test
    @DisplayName("회원가입_api테스트")
    @WithMockUser
    void signupTest() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test@example.com", "password");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)//json 형식으로 데이터를 보냄.
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void printJsonResponse() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("example123@naver.com", "password123");
        LoginResponse loginResponse = LoginResponse.of("dummyJwtToken"); // 예상되는 반환값
        when(memberService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        LoginResponse loginResponse2 = memberService.login(new LoginRequest("example123@naver.com", "password123"));
        System.out.println("loginResponse = " + loginResponse);
        System.out.println("password123 = " + loginResponse2);
        Assertions.assertEquals(loginResponse,loginResponse2);

        LoginResponse loginResponse3 = new LoginResponse("dummyJwtToken");
        System.out.println("loginResponse3 = " + objectMapper.writeValueAsString(loginResponse3));
        // When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/members/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        // Then
        int responseStatus = result.getResponse().getStatus();
        String jsonResponse = result.getResponse().getContentAsString();

        System.out.println("Response JSON: " + jsonResponse);
        System.out.println("Status: " + responseStatus);

        if (responseStatus != HttpStatus.OK.value()) {
            System.out.println("Error Content: " + result.getResponse().getErrorMessage());
        } else {
            System.out.println("Content: " + jsonResponse);
        }
    }
}
