package onboarding.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginInterceptorTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthorizationExtractor authExtractor;

    @InjectMocks
    private LoginInterceptor loginInterceptor;

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
    @DisplayName("인터셉터 통과")
    void preHandle_Valid() throws Exception {

        when(handlerMethod.hasMethodAnnotation(LoginCheck.class)).thenReturn(true);
        when(authExtractor.extract(request, LoginInterceptor.BEARER)).thenReturn("valid-token");
        when(jwtProvider.validateToken("valid-token")).thenReturn(true);
        when(jwtProvider.getUserId("valid-token")).thenReturn(1L);

        boolean result = loginInterceptor.preHandle(request, response, handlerMethod);

        assertTrue(result);
        assertEquals(1L, request.getAttribute(LoginInterceptor.USER_ID));
    }

    @Test
    void preHandle_NoLoginCheckAnnotation_ReturnsTrue() throws Exception {

        when(handlerMethod.hasMethodAnnotation(LoginCheck.class)).thenReturn(false);

        boolean result = loginInterceptor.preHandle(request, response, handlerMethod);

        assertTrue(result);
        assertNull(request.getAttribute(LoginInterceptor.USER_ID));
    }
}
