package local.clinic1.CabinetInfo.printers;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class PrinterDTO {

    private Long id;
    private String name;
    private String cartridge;
    private int cabinet;
    private Printer.ConnectionType connectionType;
    private String ipAddress;

    public PrinterDTO(String name, String cartridge, int cabinet, Printer.ConnectionType connectionType, String ipAddress) {
        this.id = 0L;
        this.name = name;
        this.cartridge = cartridge;
        this.cabinet = cabinet;
        this.connectionType = connectionType;
        this.ipAddress = ipAddress;
    }
}

