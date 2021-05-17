package local.clinic1.CabinetInfo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_users")
public class SystemUser {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;

}
