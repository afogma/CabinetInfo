package local.clinic1.CabinetInfo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUser {

    private String name;
    private String password;
    private UserRole userRole;

    public enum UserRole {
        ADMIN,
        USER
    }
}
