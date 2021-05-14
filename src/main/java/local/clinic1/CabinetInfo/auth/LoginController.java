package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
import local.clinic1.CabinetInfo.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/login")
    public UserSession loginUser(@RequestBody Login login) {
        return loginService.login(login);
    }

    @PostMapping("/registration")
    public ResponseEntity.BodyBuilder registerUser(@RequestBody RegisterRequest request, SystemUser user) {
        if (!user.isAdmin()) {
            throw new ForbiddenException();
        }
        loginService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity.BodyBuilder UserLogout(SystemUser user) {
        loginService.logout(user);
        return ResponseEntity.status(HttpStatus.GONE);
    }
}
