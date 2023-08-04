package onboarding.security;

import lombok.extern.slf4j.Slf4j;
import onboarding.exception.MissingTokenException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String USER_ID = "userId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.debug("supportsParameter 실행");
        return parameter.hasParameterAnnotation(LoginValue.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        log.debug("USER_ID", httpServletRequest.getAttribute(USER_ID));
        if (httpServletRequest.getAttribute(USER_ID) == null) {
            throw new MissingTokenException("로그인 해주세요.");
        }
        return httpServletRequest.getAttribute(USER_ID);
    }
}
