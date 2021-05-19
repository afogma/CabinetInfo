package local.clinic1.CabinetInfo.computers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "computers")
public class Computer {

    @Id
    private String name;
    private String ram;
    private String processor;

    @Column(name = "ip_address")
    private String ipAddress;

    private int cabinet;
    private String login;
    private String password;


//    public Computer withoutPassword() {
//        Computer computer = new Computer();
//        computer.setName(this.getName());
//        computer.setRam(this.getRam());
//        computer.setProcessor(this.getProcessor());
//        computer.setIpAddress(this.getIpAddress());
//        computer.setCabinet(this.getCabinet());
//        computer.setLogin(this.getLogin());
//        computer.setPassword("");
//        return computer;
//    }

    public Computer withoutPassword() {
        return new Computer(
                this.getName(),
                this.getRam(),
                this.getProcessor(),
                this.getIpAddress(),
                this.getCabinet(),
                this.getLogin(),
                ""
        );
    }
}
