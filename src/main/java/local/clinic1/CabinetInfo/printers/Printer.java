package local.clinic1.CabinetInfo.printers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
