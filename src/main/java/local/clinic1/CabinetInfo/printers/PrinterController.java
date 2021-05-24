package local.clinic1.CabinetInfo.printers;

import local.clinic1.CabinetInfo.auth.Authorized;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/printers")
public class PrinterController {

    private final PrinterService printerService;

    @GetMapping
    public List<Printer> showAllPrinters() {
        return printerService.findAll();
    }

    @GetMapping("/{id}")
    public Printer showPrinterById(@PathVariable Long id) {
        return printerService.findPrinterById(id);
    }

    @GetMapping("/filter")
    public List<Printer> showAllByCabinetOrName(
            @RequestParam(required = false) Integer cabinet,
            @RequestParam(required = false) String name) {
        return printerService.findAllByCabinetOrName(cabinet, name);
    }

    @PostMapping
    @Authorized
    public ResponseEntity registration(@RequestBody Printer printer) {
        printerService.addNewPrinter(printer);
        return ResponseEntity.ok("Printer was added");
    }

    @PutMapping("/{id}")
    @Authorized
    public ResponseEntity updatePrinter(@PathVariable Long id, @RequestBody Printer printer) {
        printerService.updateById(id, printer);
        return ResponseEntity.ok("Printer info updated");
    }

    @DeleteMapping("/del")
    @Authorized
    public ResponseEntity deletePrinter(@RequestParam Long id) {
        printerService.deletePrinter(id);
        return ResponseEntity.ok("Printer removed");
    }

    @GetMapping("/json")
    @Authorized
    public ResponseEntity addDataFromJson() {
        printerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
