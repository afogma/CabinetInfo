package local.clinic1.CabinetInfo.users;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String position;
    private int cabinet;
    private String department;

    @Column(name = "pc_name")
    private String pcName;
}
