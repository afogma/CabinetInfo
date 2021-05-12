package local.clinic1.CabinetInfo.printers.repository;

import local.clinic1.CabinetInfo.printers.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrinterRepo extends JpaRepository<Printer, Long> {

    List<Printer> findByCabinet(int cabinet);

    List<Printer> findByName(String name);

    Printer findPrinterById(Long id);

}
