package local.clinic1.CabinetInfo.users.service;

import local.clinic1.CabinetInfo.cabinets.entity.Cabinet;
import local.clinic1.CabinetInfo.cabinets.repository.CabinetRepo;
import local.clinic1.CabinetInfo.cabinets.service.CabinetService;
import local.clinic1.CabinetInfo.exceptions.CabinetAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.CabinetNotFoundException;
import local.clinic1.CabinetInfo.exceptions.UserAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.UserNotFoundException;
import local.clinic1.CabinetInfo.users.entity.User;
import local.clinic1.CabinetInfo.users.repository.UserRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    UserRepo userRepo = mock(UserRepo.class);
    UserService userService = new UserService(userRepo);

    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Писечкина");
        user.setPosition("doctor");
        user.setCabinet(333);
        user.setDepartment("Therapy №3");
        user.setPcName("kab333");
        return user;
    }


    @Test
    void findAll() {
        List<User> users = asList(getUser());
        when(userRepo.findAll()).thenReturn(users);
        List<User> listOfUsers = userService.findAll();
        assertEquals(listOfUsers, users);
        verify(userRepo).findAll();
    }

    @Test
    void addNewUser() {
        User user = getUser();
        when(userRepo.save(user)).thenReturn(user);
        User newUser = userService.addNewUser(user);
        assertEquals(user, newUser);
        verify(userRepo).save(user);
    }

    @Test
    void findByName() {
        User user = getUser();
        when(userRepo.findByName("Писечкина")).thenReturn(user);
        User newUser = userService.findByName("Писечкина");
        assertEquals(user, newUser);
    }

    @Test
    void findAllByCabinet() {
        List<User> users = asList(getUser());
        when(userRepo.findByCabinet(333)).thenReturn(users);
        List<User> usrs = userService.findAllByCabinet(333);
        assertEquals(users, usrs);
    }

    @Test
    void findAllByPCName() {
        List<User> users = asList(getUser());
        when(userRepo.findBypcName("kab333")).thenReturn(users);
        List<User> usrs = userService.findAllByPCName("kab333");
        assertEquals(users, usrs);
    }

    @Test
    void findAllByPosition() {
        List<User> users = asList(getUser());
        when(userRepo.findByPosition("doctor")).thenReturn(users);
        List<User> usrs = userService.findAllByPosition("doctor");
        assertEquals(users, usrs);
    }

    @Test
    void findAllByDepartment() {
        List<User> users = asList(getUser());
        when(userRepo.findByDepartment("Therapy №3")).thenReturn(users);
        List<User> usrs = userService.findAllByDepartment("Therapy №3");
        assertEquals(users, usrs);
    }

    @Test
    void updateById() {
        User user = getUser();
        User newUser = user;
        newUser.setName("Жопкина");
        newUser.setPosition("doctor");
        newUser.setCabinet(335);
        newUser.setDepartment("Therapy №4");
        newUser.setPcName("kab335");
        when(userRepo.findUserById(1L)).thenReturn(user);
        when(userRepo.save(newUser)).thenReturn(newUser);
        User result = userService.updateById(1L, newUser);
        assertEquals(newUser, result);
    }

    @Test
    void deleteUser() {
        User user = getUser();
        when(userRepo.findUserById(user.getId())).thenReturn(user);
        userService.deleteUser(user.getId());
        verify(userRepo).delete(user);
    }

    @Test
    public void should_throw_exception_when_user_doesnt_exist() {
        User user = getUser();
        user.setId(2L);
        when(userRepo.findUserById(2L)).thenThrow(new UserNotFoundException());
        assertThrows(UserNotFoundException.class, () -> userRepo.findUserById(user.getId()));
    }

    @Test
    public void should_throw_exception_when_user_already_exist() {
        User user = getUser();
        when(userRepo.findUserById(1L)).thenThrow(new UserAlreadyExistException());
        assertThrows(UserAlreadyExistException.class, () -> userRepo.findUserById(user.getId()));
    }
}