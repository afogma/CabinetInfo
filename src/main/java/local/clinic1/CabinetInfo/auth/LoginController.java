package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.auth.Login;
import local.clinic1.CabinetInfo.auth.UserSession;
import local.clinic1.CabinetInfo.auth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/login")
    public UserSession loginUser(@RequestBody Login login) {
        return loginService.login(login);
    }

}
