package onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import onboarding.dto.request.SignUpRequest;
import onboarding.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(MemberController.class)
@WithMockUser // 가짜 사용자 인증 정보 설정
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

// ...

    @DisplayName("post 회원가입테스트")
    @Test
    void signTest() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("example123@naver.com", "password123");

        // When
        when(memberService.signup(any(SignUpRequest.class))).thenReturn(1L); // 수정 필요

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
