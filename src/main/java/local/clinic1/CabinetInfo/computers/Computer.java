package local.clinic1.CabinetInfo.computers;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
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


    public Computer withoutPassword() {
        Computer computer = new Computer();
        computer.setName(this.getName());
        computer.setRam(this.getRam());
        computer.setProcessor(this.getProcessor());
        computer.setIpAddress(this.getIpAddress());
        computer.setCabinet(this.getCabinet());
        computer.setLogin(this.getLogin());
        computer.setPassword("");
        return computer;
    }

}
