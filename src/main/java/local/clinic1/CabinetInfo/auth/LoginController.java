package local.clinic1.CabinetInfo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public UserSession loginUser(@RequestBody Login login, HttpServletRequest request) {
        return loginService.login(login, request.getRemoteAddr());
    }

    @PostMapping("/registration")
    @Authorized
    public ResponseEntity registerUser(@RequestBody Login login) {
        loginService.register(login);
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
