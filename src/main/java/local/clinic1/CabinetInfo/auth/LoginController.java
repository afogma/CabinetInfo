package local.clinic1.CabinetInfo.auth;

import lombok.RequiredArgsConstructor;
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
