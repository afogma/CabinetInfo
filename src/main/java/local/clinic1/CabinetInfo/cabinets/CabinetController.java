package local.clinic1.CabinetInfo.cabinets;

import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.PermissionDeniedException;
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
    public ResponseEntity registration(@RequestBody Cabinet cab, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        cabinetService.addNewCabinet(cab);
        return ResponseEntity.ok("Cabinet successfully added");
    }

    @PutMapping("/{number}")
    public ResponseEntity<Cabinet> updateCabinet(@PathVariable int number, @RequestBody Cabinet cabinet, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        return ResponseEntity.ok(cabinetService.updateByNumber(number, cabinet));
    }

    @DeleteMapping("/del")
    public ResponseEntity deleteCabinet(@RequestParam int number, SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        cabinetService.deleteByNumber(number);
        return ResponseEntity.ok("Cabinet deleted");
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson(SystemUser user) {
        if (!user.isAdmin()) throw new PermissionDeniedException();
        cabinetService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
