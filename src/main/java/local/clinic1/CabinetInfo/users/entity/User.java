package local.clinic1.CabinetInfo.users.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String position;
    private int cabinet;
    private String department;

    @Column(name = "pc_name")
    private String pcName;
}
