package local.clinic1.CabinetInfo.users;

import local.clinic1.CabinetInfo.auth.AuthorizationData;
import local.clinic1.CabinetInfo.auth.Authorized;
import local.clinic1.CabinetInfo.auth.SystemUser;
import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import local.clinic1.CabinetInfo.exceptions.UserNotFoundException;
import local.clinic1.CabinetInfo.exceptions.UsersNotFoundException;
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
    public List<User> showAllUsers(AuthorizationData data) {
        if (data == null) throw new AuthenticationFailedException();
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User showUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/department")
    public List<User> showAllByDepartment(@RequestParam String department) throws UsersNotFoundException {
        return userService.findAllByDepartment(department);
    }

    @PostMapping("/cabinet")
    public List<User> showAllByCabinet(@RequestParam int cabinet) throws UsersNotFoundException {
        return userService.findAllByCabinet(cabinet);
    }

    @PostMapping("/computer")
    public List<User> showAllByPCName(@RequestParam String pcName) throws UsersNotFoundException {
        return userService.findAllByPCName(pcName);
    }

    @PostMapping("/position")
    public List<User> showAllByPosition(@RequestParam String position) throws UsersNotFoundException {
        return userService.findAllByPosition(position);
    }

    @PostMapping
    @Authorized
    public ResponseEntity registration(@RequestBody User user, SystemUser systemUser) {
        userService.addNewUser(user);
        return ResponseEntity.ok("User was added");
    }

    @PutMapping("/{id}")
    @Authorized
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user, SystemUser systemUser) {
        userService.updateById(id, user);
        return ResponseEntity.ok("User info updated");
    }

    @DeleteMapping
    @Authorized
    public ResponseEntity deleteUser(@PathVariable Long id, SystemUser systemUser) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.ok("User removed");
    }

    @GetMapping("/json")
    @Authorized
    public ResponseEntity addDataFromJson(SystemUser systemUser) throws URLNotValidException {
        userService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }

}
