package local.clinic1.CabinetInfo.cabinets;

import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cabinets")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;

    @GetMapping
    public List<Cabinet> showCabinetList() {
//    public List<Cabinet> showCabinetList(SystemUser user) {
//        if (user.isAdmin()) {
//            throw new AuthenticationFailedException();
//        }
        return cabinetService.findAll();
    }

    @GetMapping("/{number}")
    public Cabinet showCabinetByNumber(@PathVariable int number) {
        return cabinetService.findByNumber(number);
    }

    @GetMapping("/filter")
    public List<Cabinet> showAllCabinetsByFloorOrDepartment(@RequestParam(required = false) String department, @RequestParam(required = false) Integer floor) {
        return cabinetService.findAllByFloorOrDepartment(department, floor);
    }

    @PostMapping
    public ResponseEntity registration(@RequestBody Cabinet cab) {
        cabinetService.addNewCabinet(cab);
        return ResponseEntity.ok("Cabinet successfully added");
    }

    @DeleteMapping("/del")
    public ResponseEntity deleteCabinet(@RequestParam int number) {
        cabinetService.deleteByNumber(number);
        return ResponseEntity.ok("Cabinet deleted");
    }

    @PutMapping("/{number}")
    public ResponseEntity<Cabinet> updateCabinet(@PathVariable int number, @RequestBody Cabinet cabinet) {
        return ResponseEntity.ok(cabinetService.updateByNumber(number, cabinet));
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson() {
        cabinetService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
