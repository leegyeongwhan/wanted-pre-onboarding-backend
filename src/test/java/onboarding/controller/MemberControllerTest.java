package onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import onboarding.dto.request.SignUpRequest;
import onboarding.security.AuthorizationExtractor;
import onboarding.security.JwtProvider;
import onboarding.security.LoginCheck;
import onboarding.security.LoginInterceptor;
import onboarding.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @Mock
    private LoginInterceptor loginInterceptor; // Mock 인터셉터 추가

    @InjectMocks
    private MemberController memberController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthorizationExtractor authExtractor;


    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HandlerMethod handlerMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        handlerMethod = mock(HandlerMethod.class);
    }


    @Test
    @DisplayName("인터셉터테스트")
    void 회원가입_인터셉터_테스트() throws Exception {
        // Arrange
        when(handlerMethod.hasMethodAnnotation(LoginCheck.class)).thenReturn(true);
        when(authExtractor.extract(request, LoginInterceptor.BEARER)).thenReturn("valid-token");
        when(jwtProvider.validateToken("valid-token")).thenReturn(true);
        when(jwtProvider.getUserId("valid-token")).thenReturn(1L);

        // Act
        boolean result = loginInterceptor.preHandle(request, response, handlerMethod);

        // Assert
        assertTrue(result);
        assertEquals(1L, request.getAttribute(LoginInterceptor.USER_ID));
    }

    @Test
     void testSignup_Success() throws Exception {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("test@example.com", "password");
        when(memberService.signup(any(SignUpRequest.class))).thenReturn(1L);

        // Act & Assert
        mockMvc.perform(post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1L));

        verify(memberService, times(1)).signup(any(SignUpRequest.class));
    }

}
