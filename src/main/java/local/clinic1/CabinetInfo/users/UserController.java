package local.clinic1.CabinetInfo.users;

import local.clinic1.CabinetInfo.auth.Authorized;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Authorized
    public List<User> showAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Authorized
    public User showUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/department")
    @Authorized
    public List<User> showAllByDepartment(@RequestParam String department) {
        return userService.findAllByDepartment(department);
    }

    @PostMapping("/cabinet")
    @Authorized
    public List<User> showAllByCabinet(@RequestParam int cabinet) {
        return userService.findAllByCabinet(cabinet);
    }

    @PostMapping("/computer")
    @Authorized
    public List<User> showAllByPCName(@RequestParam String pcName) {
        return userService.findAllByPCName(pcName);
    }

    @PostMapping("/position")
    @Authorized
    public List<User> showAllByPosition(@RequestParam String position) {
        return userService.findAllByPosition(position);
    }

    @PostMapping
    @Authorized
    public ResponseEntity registration(@RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok("User was added");
    }

    @PutMapping("/{id}")
    @Authorized
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateById(id, user);
        return ResponseEntity.ok("User info updated");
    }

    @DeleteMapping("/{id}")
    @Authorized
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User removed");
    }

    @GetMapping("/json")
    @Authorized
    public ResponseEntity addDataFromJson() {
        userService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }

}
