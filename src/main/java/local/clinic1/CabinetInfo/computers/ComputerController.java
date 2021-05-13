package local.clinic1.CabinetInfo.computers;

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

    @PostMapping
    public ResponseEntity registration(@RequestBody Computer pc) throws ComputerAlreadyExistException {
        computerService.addNewPC(pc);
        return ResponseEntity.ok("Computer was added");
    }

    @PutMapping("/{name}")
    public ResponseEntity updateComputer(@PathVariable String name, @RequestBody Computer pc) {
        computerService.updatePCByName(name, pc);
        return ResponseEntity.ok("Computer info updated");
    }

    @DeleteMapping("/del")
    public ResponseEntity deleteComputer(@RequestParam String name) throws ComputerNotFoundException {
        computerService.deletePCByName(name);
        return ResponseEntity.ok("Computer removed");
    }

    @GetMapping("/pc")
    public Computer showComputerByName(@RequestParam String name) throws ComputerNotFoundException {
        return computerService.findPCByName(name);
    }

    @GetMapping("/cab")
    public List<Computer> showAllByCabinet(@RequestParam int cabinet) {
        return computerService.findAllByCabinet(cabinet);
    }

//    @GetMapping("/ram")
//    public List<Computer> showAllByRam(@RequestParam String ram) {
//        return computerService.findAllByRam(ram);
//    }
//
//    @GetMapping("/proc")
//    public List<Computer> showAllByProcessor(@RequestParam String processor) {
//        return computerService.findAllByProcessor(processor);
//    }

    @GetMapping("/filter")
    public List<Computer> showAllCabinetsByFloorOrDepartment(@RequestParam(required = false) String ram, @RequestParam(required = false) String processor) throws ComputerNotFoundException {
        return computerService.findComputersByRamOrProcessor(ram, processor);
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson() throws URLNotValidException {
        computerService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
