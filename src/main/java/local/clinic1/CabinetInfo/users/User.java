package local.clinic1.CabinetInfo.users;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }
}
