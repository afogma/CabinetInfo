package local.clinic1.CabinetInfo.users.controller;

import local.clinic1.CabinetInfo.exceptions.*;
import local.clinic1.CabinetInfo.printers.entity.Printer;
import local.clinic1.CabinetInfo.users.entity.User;
import local.clinic1.CabinetInfo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> showAllPrinters() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity registration(@RequestBody User user) throws UserAlreadyExistException {
        userService.addNewUser(user);
        return ResponseEntity.ok("User was added");
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateById(id, user);
        return ResponseEntity.ok("User info updated");
    }

    @DeleteMapping("/del")
    public ResponseEntity deleteUser(@RequestParam Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.ok("User removed");
    }

    @PostMapping("/dep")
    public List<User> showAllByDepartment(String department) throws UsersNotFoundException {
        return userService.findAllByDepartment(department);
    }

    @PostMapping("/cab")
    public List<User> showAllByCabinet(int cabinet) throws UsersNotFoundException {
        return userService.findAllByCabinet(cabinet);
    }

    @PostMapping("/pc")
    public List<User> showAllByPCName(String pcName) throws UsersNotFoundException {
        return userService.findAllByPCName(pcName);
    }

    @PostMapping("/pos")
    public List<User> showAllByPosition(String position) throws UsersNotFoundException {
        return userService.findAllByPosition(position);
    }

    @GetMapping("/json")
    public ResponseEntity addDataFromJson() throws URLNotValidException {
        userService.loadFromJson();
        return ResponseEntity.ok("JSON data loaded");
    }

}
