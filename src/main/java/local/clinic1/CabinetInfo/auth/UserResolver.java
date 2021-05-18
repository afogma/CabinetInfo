package local.clinic1.CabinetInfo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserResolver implements HandlerMethodArgumentResolver {

    private final LoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return AuthorizationData.class.equals(methodParameter.getParameterType());
    }

    @Override
    public AuthorizationData resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        var request = webRequest.getNativeRequest(HttpServletRequest.class);
        var session = request.getHeader("sessionId");
        var token = request.getHeader("token");
//        var user = loginService.getUser(session, token);
//        if (user == null) throw new AuthenticationFailedException();
//        return user;
        var user = loginService.getUser(session, token);
        return new AuthorizationData(user != null);
    }
}
