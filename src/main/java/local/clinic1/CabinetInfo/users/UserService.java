package local.clinic1.CabinetInfo.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import local.clinic1.CabinetInfo.exceptions.UserAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.UserNotFoundException;
import local.clinic1.CabinetInfo.exceptions.UsersNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;

    public List<User> findAll() {
        return userRepo.findAll().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(toList());
    }

    public User addNewUser(User user) {
        if (userRepo.findByName(user.getName()) != null) throw new UserAlreadyExistException();
        var usr = userRepo.save(user);
        logger.info("{} added", usr);
        return usr;
    }

    public User findByName(String name) {
        var user = userRepo.findByName(name);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    public User findById(Long id) {
        var user = userRepo.findUserById(id);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    public List<User> findAllByCabinet(int cabinet) {
        List<User> users = userRepo.findByCabinet(cabinet);
        if (users.isEmpty()) throw new UsersNotFoundException();
        return users;
    }

    public List<User> findAllByPCName(String pcName) {
        List<User> users = userRepo.findByPcName(pcName);
        if (users.isEmpty()) throw new UsersNotFoundException();
        return users;
    }

    public List<User> findAllByPosition(String position) {
        List<User> users = userRepo.findByPosition(position);
        if (users.isEmpty()) throw new UsersNotFoundException();
        return users;
    }

    public List<User> findAllByDepartment(String department) {
        List<User> users = userRepo.findByDepartment(department);
        if (users.isEmpty()) throw new UsersNotFoundException();
        return users;
    }

    public User updateById(Long id, User user) {
        var newUser = userRepo.findUserById(id);
        newUser.setName(user.getName());
        newUser.setPosition(user.getPosition());
        newUser.setCabinet(user.getCabinet());
        newUser.setDepartment(user.getDepartment());
        newUser.setPcName(user.getPcName());
        userRepo.save(newUser);
        logger.info("{} updated", user);
        return newUser;
    }

    public void deleteUser(Long id) {
        var user = userRepo.findUserById(id);
        if (user == null) throw new UserNotFoundException();
        userRepo.delete(user);
        logger.info("{} deleted", userRepo.findUserById(id));
    }

    public void loadFromJson() {
        var url = this.getClass().getClassLoader().getResource("user_data.json");
        if (url != null) {
            File jsonFile = new File(url.getFile());
            var objectMapper = new ObjectMapper();
            try {
                List<User> users = objectMapper.readValue(jsonFile, new TypeReference<>() {
                });
                userRepo.saveAll(users);
                logger.info("saved all records");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new URLNotValidException();
        }
    }
}
