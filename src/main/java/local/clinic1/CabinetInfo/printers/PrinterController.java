package local.clinic1.CabinetInfo.printers;

import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/printers")
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

    @PostMapping
    public ResponseEntity registration(@RequestBody Printer printer, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        printerService.addNewPrinter(printer);
        return ResponseEntity.ok("Printer was added");
    }

    @PostMapping("/{name}")
    public List<Printer> showAllByName(@PathVariable String name) {
        return printerService.findAllByName(name);
    }

    @PostMapping("/filter")
    public List<Printer> showAllByCabinet(@RequestParam int cabinet){
        return printerService.findAllByCabinet(cabinet);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePrinter(@PathVariable Long id, @RequestBody Printer printer, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        printerService.updateById(id, printer);
        return ResponseEntity.ok("Printer info updated");
    }

    @DeleteMapping("/del")
    public ResponseEntity deletePrinter(@RequestParam Long id, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        printerService.deletePrinter(id);
        return ResponseEntity.ok("Printer removed");
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson(SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        printerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
