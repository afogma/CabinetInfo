package local.clinic1.CabinetInfo.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String user;
    private String password;
    private SystemUser.UserRole userRole;

}
