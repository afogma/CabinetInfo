package local.clinic1.CabinetInfo.auth;

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
    public ResponseEntity.BodyBuilder registerUser(@RequestBody Login login, String role) {
        loginService.register(login, role);
        return ResponseEntity.status(HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity.BodyBuilder UserLogout(@RequestBody Login login) {
        loginService.logout(login);
        return ResponseEntity.status(HttpStatus.GONE);
    }
}
