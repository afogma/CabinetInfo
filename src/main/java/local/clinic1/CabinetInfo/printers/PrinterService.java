package local.clinic1.CabinetInfo.printers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import local.clinic1.CabinetInfo.exceptions.PrinterNotFoundException;
import local.clinic1.CabinetInfo.exceptions.PrintersNotFoundException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrinterService {

    Logger logger = LoggerFactory.getLogger(PrinterService.class);
    private final PrinterRepo printerRepo;

    public List<Printer> findAll() {
        List<Printer> printers = printerRepo.findAll();
        printers.sort(Comparator.comparing(Printer::getName));
        return printers;
    }

    public Printer addNewPrinter(Printer printer) {
        var print = printerRepo.save(printer);
        logger.info("{} added", print);
        return print;
    }

    public Printer findPrinterById(Long id) {
        var printer = printerRepo.findPrinterById(id);
        if (printer == null) {
            throw new PrinterNotFoundException();
        }
        return printer;
    }

    public List<Printer> findAllByName(String name) {
        List<Printer> printers = printerRepo.findByName(name);
        if (printers.isEmpty()) {
            throw new PrintersNotFoundException();
        }
        return printers;
    }

    public List<Printer> findAllByCabinet(int cabinet) {
        List <Printer> printers = printerRepo.findByCabinet(cabinet);
        if (printers.isEmpty()) {
            throw new PrintersNotFoundException();
        }
        return printers;
    }

    public Printer updateById(Long id, Printer printer) {
        var newPrinter = printerRepo.findPrinterById(id);
        newPrinter.setName(printer.getName());
        newPrinter.setCartridge(printer.getCartridge());
        newPrinter.setCabinet(printer.getCabinet());
        newPrinter.setConnectionType(printer.getConnectionType());
        newPrinter.setIpAddress(printer.getIpAddress());
        printerRepo.save(newPrinter);
        logger.info("{} updated", printer);
        return newPrinter;
    }

    public void deletePrinter(Long id) {
        var printer = printerRepo.findPrinterById(id);
        if (printer == null) {
            throw new PrinterNotFoundException();
        }
        printerRepo.delete(printer);
        logger.info("{} deleted", printerRepo.findPrinterById(id));
    }

    public void loadFromJson() throws URLNotValidException {
        var url = this.getClass().getClassLoader().getResource("printer_data.json");
        if (url != null) {
            File jsonFile = new File(url.getFile());
            var objectMapper = new ObjectMapper();
            try {
                List<Printer> printers = objectMapper.readValue(jsonFile, new TypeReference<>() {
                });
                printerRepo.saveAll(printers);
                logger.info("saved all records");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new URLNotValidException();
        }
    }
}
