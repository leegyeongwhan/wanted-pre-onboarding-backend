package onboarding.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onboarding.domain.MemberToken;
import onboarding.repository.MemberRepository;
import onboarding.repository.MemberTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    public static final String BEARER = "Bearer";
    public static final String USER_ID = "userId";
    private final JwtProvider jwtProvider;
    private final AuthorizationExtractor authExtractor;
    private final MemberTokenRepository memberTokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("인터셉터 실행");

        if (loginCheck(handler)) {
            String token = authExtractor.extract(request, BEARER);

            //헤더로 부터 토큰을 얻어온 후 유효한 토큰인지 검증한다. 요청에  디코딩한 값을 세팅
            if ((token != null && !token.equals("")) && (jwtProvider.validateToken(token))) {
                Long userId = jwtProvider.getUserId(token);
                log.debug("userId", userId);
                request.setAttribute(USER_ID, userId);
                log.debug("토큰으로 부터 받아온 userId = {}", userId);
                request.setAttribute(USER_ID, userId);

            }
        }

        return true;
    }

    private boolean loginCheck(Object handler) {
        return handler instanceof HandlerMethod
                && ((HandlerMethod) handler).hasMethodAnnotation(LoginCheck.class);
    }
}
