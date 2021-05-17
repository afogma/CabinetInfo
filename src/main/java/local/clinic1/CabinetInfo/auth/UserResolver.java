package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
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
        return SystemUser.class.equals(methodParameter.getParameterType());
    }

    @Override
    public SystemUser resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        var request = webRequest.getNativeRequest(HttpServletRequest.class);
        var session = request.getHeader("session");
        var token = request.getHeader("token");
        var ipAddress = request.getHeader("ipAddress");
        var user = loginService.getUser(session, token);
        if (user == null) throw new AuthenticationFailedException();
        return user;
    }
}
