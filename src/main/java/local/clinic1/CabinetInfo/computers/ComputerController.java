package local.clinic1.CabinetInfo.computers;

import local.clinic1.CabinetInfo.auth.AuthorizationData;
import local.clinic1.CabinetInfo.auth.Authorized;
import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.ComputerAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.ComputerNotFoundException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
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
    public List<Computer> showAllComputers() {
        return computerService.findAll();
    }

    @GetMapping("/pc")
    public Computer showComputerByName(@RequestParam String name, AuthorizationData data) throws ComputerNotFoundException {
        if (data.isLoggedIn()) computerService.findPCByNameNoAuth(name);
        return computerService.findPCByName(name);
    }

    @GetMapping("/cabinet")
    public List<Computer> showAllByCabinet(@RequestParam int cabinet) {
        return computerService.findAllByCabinet(cabinet);
    }

    @GetMapping("/filter")
    public List<Computer> showAllCabinetsByFloorOrDepartment(@RequestParam(required = false) String ram, @RequestParam(required = false) String processor) throws ComputerNotFoundException {
        return computerService.findComputersByRamOrProcessor(ram, processor);
    }

    @PostMapping
    @Authorized
    public ResponseEntity registration(@RequestBody Computer pc, SystemUser user) throws ComputerAlreadyExistException {
        computerService.addNewPC(pc);
        return ResponseEntity.ok("Computer was added");
    }

    @PutMapping("/{name}")
    @Authorized
    public ResponseEntity updateComputer(@PathVariable String name, @RequestBody Computer pc, SystemUser user) {
        computerService.updatePCByName(name, pc);
        return ResponseEntity.ok("Computer info updated");
    }

    @DeleteMapping("/del")
    @Authorized
    public ResponseEntity deleteComputer(@RequestParam String name, SystemUser user) throws ComputerNotFoundException {
        computerService.deletePCByName(name);
        return ResponseEntity.ok("Computer removed");
    }

    @GetMapping("/json")
    @Authorized
    public ResponseEntity addDataFromJson(SystemUser user) throws URLNotValidException {
        computerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
