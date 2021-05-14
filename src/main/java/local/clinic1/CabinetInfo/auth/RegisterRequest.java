package local.clinic1.CabinetInfo.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
public class RegisterRequest {

    private String user;
    private String password;
    private SystemUser.UserRole userRole;

}
