package local.clinic1.CabinetInfo.printers.controller;

import local.clinic1.CabinetInfo.exceptions.PrinterAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.PrinterNotFoundException;
import local.clinic1.CabinetInfo.exceptions.PrintersNotFoundException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import local.clinic1.CabinetInfo.printers.entity.Printer;
import local.clinic1.CabinetInfo.printers.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/printers")
public class PrinterController {

    private final PrinterService printerService;

    @Autowired
    public PrinterController(PrinterService printerService) {
        this.printerService = printerService;
    }

    @GetMapping
    public List<Printer> showAllPrinters() {
        return printerService.findAll();
    }

    @PostMapping
    public ResponseEntity registration(@RequestBody Printer printer) throws PrinterAlreadyExistException {
        printerService.addNewPrinter(printer);
        return ResponseEntity.ok("Printer was added");
    }

    @PostMapping("/{name}")
    public List<Printer> showAllByName(@PathVariable String name) throws PrintersNotFoundException {
        return printerService.findAllByName(name);
    }

    @PostMapping("/filter")
    public List<Printer> showAllByCabinet(@RequestParam int cabinet) throws PrintersNotFoundException {
        return printerService.findAllByCabinet(cabinet);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePrinter(@PathVariable Long id, @RequestBody Printer printer) {
        printerService.updateById(id, printer);
        return ResponseEntity.ok("Printer info updated");
    }

    @DeleteMapping("/del")
    public ResponseEntity deletePrinter(@RequestParam Long id) throws PrinterNotFoundException {
        printerService.deletePrinter(id);
        return ResponseEntity.ok("Printer removed");
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson() throws URLNotValidException {
        printerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
