package local.clinic1.CabinetInfo.users;

import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.*;
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
    public List<User> showAllUsers(SystemUser user) {
        if (!user.isAdmin()) {
            throw new PermissionDeniedException();
        }
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity registration(@RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok("User was added");
    }

    @GetMapping ("/{id}")
    public User showUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateById(id, user);
        return ResponseEntity.ok("User info updated");
    }

    @DeleteMapping("/del")
    public ResponseEntity deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.ok("User removed");
    }

    @PostMapping("/dep")
    public List<User> showAllByDepartment(@RequestParam String department) throws UsersNotFoundException {
        return userService.findAllByDepartment(department);
    }

    @PostMapping("/cab")
    public List<User> showAllByCabinet(@RequestParam int cabinet) throws UsersNotFoundException {
        return userService.findAllByCabinet(cabinet);
    }

    @PostMapping("/pc")
    public List<User> showAllByPCName(@RequestParam String pcName) throws UsersNotFoundException {
        return userService.findAllByPCName(pcName);
    }

    @PostMapping("/pos")
    public List<User> showAllByPosition(@RequestParam String position) throws UsersNotFoundException {
        return userService.findAllByPosition(position);
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson() throws URLNotValidException {
        userService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }

}
