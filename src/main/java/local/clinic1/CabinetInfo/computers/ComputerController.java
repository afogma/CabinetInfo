package local.clinic1.CabinetInfo.computers;

import local.clinic1.CabinetInfo.auth.AuthorizationData;
import local.clinic1.CabinetInfo.auth.Authorized;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/computers")
public class ComputerController {

    private final ComputerService computerService;

    @GetMapping
    public List<Computer> showAllComputers(AuthorizationData data) {
        return computerService.findAll(data.isLoggedIn());
    }

    @GetMapping("/{name}")
    public Computer showComputerByName(@PathVariable String name, AuthorizationData data) {
        return computerService.findPCByName(name, data.isLoggedIn());
    }

    @GetMapping("/cab")
    public List<Computer> showAllByCabinet(@RequestParam int cabinet, AuthorizationData data) {
        return computerService.findAllByCabinet(cabinet, data.isLoggedIn());
    }

    @GetMapping("/filter")
    public List<Computer> showAllCabinetsByFloorOrDepartment(
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String processor,
            AuthorizationData data) {
        return computerService.findComputersByRamOrProcessor(ram, processor, data.isLoggedIn());
    }

    @PostMapping
    @Authorized
    public ResponseEntity registration(@RequestBody Computer pc) {
        computerService.addNewPC(pc);
        return ResponseEntity.ok("Computer was added");
    }

    @PutMapping("/{name}")
    @Authorized
    public ResponseEntity updateComputer(@PathVariable String name, @RequestBody Computer pc) {
        computerService.updatePCByName(name, pc);
        return ResponseEntity.ok("Computer info updated");
    }

    @DeleteMapping("/del")
    @Authorized
    public ResponseEntity deleteComputer(@RequestParam String name) {
        computerService.deletePCByName(name);
        return ResponseEntity.ok("Computer removed");
    }

    @GetMapping("/json")
    @Authorized
    public ResponseEntity addDataFromJson() {
        computerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
