package local.clinic1.CabinetInfo.printers;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "printers")
public class Printer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String cartridge;

    private int cabinet;

    @Enumerated(EnumType.STRING)
    @Column(name = "connection_type")
    private ConnectionType connectionType;

    @Column(name = "ip_address")
    private String ipAddress;

    public enum ConnectionType {
        LAN,
        USB,
        WIFI;
    }
}
