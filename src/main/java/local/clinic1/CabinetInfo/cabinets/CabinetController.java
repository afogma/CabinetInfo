package local.clinic1.CabinetInfo.cabinets;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import local.clinic1.CabinetInfo.auth.Authorized;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/cabinets")
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

    @Operation(summary = "Filter cabinet by either department or floor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the cabinet",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cabinet.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cabinet not found",
                    content = @Content)})
    @GetMapping("/filter")
    public List<Cabinet> showAllCabinetsByFloorOrDepartment(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Integer floor) {
        return cabinetService.findAllByFloorOrDepartment(department, floor);
    }

    @PostMapping
    @Authorized
    public ResponseEntity registration(@RequestBody Cabinet cab) {
        cabinetService.addNewCabinet(cab);
        return ResponseEntity.ok("Cabinet successfully added");
    }

    @PutMapping("/{number}")
    @Authorized
    public ResponseEntity<Cabinet> updateCabinet(
            @PathVariable int number,
            @RequestBody Cabinet cabinet) {
        return ResponseEntity.ok(cabinetService.updateByNumber(number, cabinet));
    }

    @DeleteMapping("/del")
    @Authorized
    public ResponseEntity deleteCabinet(@RequestParam int number) {
        cabinetService.deleteByNumber(number);
        return ResponseEntity.ok("Cabinet deleted");
    }

    @GetMapping("/json")
    @Authorized
    public ResponseEntity addDataFromJson() {
        cabinetService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }
}
