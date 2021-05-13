package local.clinic1.CabinetInfo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_users")
public class SystemUser {

    @Id
    private Long id;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    public boolean isAdmin() {
        return userRole.equals(UserRole.ADMIN);
    }

    public enum UserRole {
        ADMIN,
        USER
    }
}
