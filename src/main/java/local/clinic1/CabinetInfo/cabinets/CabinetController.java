package local.clinic1.CabinetInfo.cabinets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cabinets")
public class CabinetController {

    private final CabinetService cabinetService;

    @Autowired
    public CabinetController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @GetMapping
//    public List<Cabinet> showCabinetList(SystemUser user) {
    public List<Cabinet> showCabinetList() {
        return cabinetService.findAll();
    }

    @GetMapping("/{number}")
    public Cabinet showCabinetByNumber(@PathVariable int number){
        return cabinetService.findByNumber(number);
    }

//    @GetMapping("/filter")
//    public List<Cabinet> findAllCabinetsByFloor(@RequestParam Integer floor) throws ComputerNotFoundException {
//        return cabinetService.showCabinetsByFloor(floor);
//    }
//
//    @GetMapping("/filter/")
//    public List<Cabinet> findAllCabinetsByDepartment(@RequestParam String department) {
//        return cabinetService.showCabinetsByDepartment(department);
//    }

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
