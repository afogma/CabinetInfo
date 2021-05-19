package local.clinic1.CabinetInfo.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginFilter implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            var method = (HandlerMethod) handler;
            if (method.hasMethodAnnotation(Authorized.class)) {
                var user = loginService.getUser(request.getHeader("sessionId"), request.getHeader("token"));
                MDC.put("user-id", user.getId().toString());
                return true;
            }
        }
        return true;
    }
}