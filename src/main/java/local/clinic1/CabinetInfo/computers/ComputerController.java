package local.clinic1.CabinetInfo.computers;

import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.ComputerAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.ComputerNotFoundException;
import local.clinic1.CabinetInfo.exceptions.PermissionDeniedException;
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
    public Computer showComputerByName(@RequestParam String name) throws ComputerNotFoundException {
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
    public ResponseEntity registration(@RequestBody Computer pc, SystemUser user) throws ComputerAlreadyExistException {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        computerService.addNewPC(pc);
        return ResponseEntity.ok("Computer was added");
    }

    @PutMapping("/{name}")
    public ResponseEntity updateComputer(@PathVariable String name, @RequestBody Computer pc, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        computerService.updatePCByName(name, pc);
        return ResponseEntity.ok("Computer info updated");
    }

    @DeleteMapping("/del")
    public ResponseEntity deleteComputer(@RequestParam String name, SystemUser user) throws ComputerNotFoundException {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        computerService.deletePCByName(name);
        return ResponseEntity.ok("Computer removed");
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson(SystemUser user) throws URLNotValidException {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        computerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
