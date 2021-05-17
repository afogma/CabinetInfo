package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public UserSession loginUser(@RequestBody Login login, HttpServletRequest request) {
        return loginService.login(login, request.getRemoteAddr());
    }

    @PostMapping("/registration")
    public ResponseEntity registerUser(@RequestBody RegisterRequest request, SystemUser user) {
        if (!user.isAdmin()) {
            throw new ForbiddenException();
        }
        loginService.register(request);
        return ResponseEntity.ok("new user created");
    }

    @PostMapping("/logout")
    public ResponseEntity UserLogout(HttpServletRequest request) {
        var sessionId = request.getHeader("sessionId");
        var token = request.getHeader("token");
        loginService.logout(sessionId, token);
        return ResponseEntity.ok("logged out");
    }
}
