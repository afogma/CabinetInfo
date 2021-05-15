package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
import local.clinic1.CabinetInfo.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final SystemUserRepo systemUserRepo;

    private final Map<String, UserSession> sessions = new HashMap<>();
    //    private final Map<String, SystemUser> users = new HashMap<>();
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//    {
//        SystemUser user = new SystemUser("admin",encryption("4dm1n2"), ADMIN);
//        // pass hash: $2a$10$JvNX9sgVBnSJXyRuEpNtXuBLok1cIZ0fDtxNZxHftEcgStF/f7x6e
//        userpass: $2a$10$CQDdUmJXO.qc8zLw4zx9LeHMJcOsGhx3dY5R5DZ4neFDemTGopd6i
//        users.put("admin", user);
//    }

    public UserSession login(Login login) {
        var user = systemUserRepo.findByName(login.getUser());
        if (user == null) throw new AuthenticationFailedException();
        ;
        if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new AuthenticationFailedException();
        ;

        System.out.println("login password: " + login.getPassword());
        System.out.println("user password: " + user.getPassword());

        var session = new UserSession(login.getUser());
        sessions.put(session.getSessionId(), session);
        return session;
    }

    public UserSession getSession(String session, String token) {
        var s = sessions.get(session);
        if (s == null) {
            throw new AuthenticationFailedException();
        }
        if (!s.getToken().equals(token)) {
            throw new AuthenticationFailedException();
        }
        return s;
    }

    public SystemUser getUser(String session, String token) {
        var s = getSession(session, token);
        if (s == null) {
            throw new AuthenticationFailedException();
        }
        var userName = s.getUserName();
        var user = systemUserRepo.findByName(userName);
        return user;
    }

    private String encryption(String password) {
        var encryptedPassword = bCryptPasswordEncoder.encode(password);
        return encryptedPassword;
    }

    public SystemUser register(RegisterRequest request) {
        if (request == null) throw new UserNotFoundException();
        var user = new SystemUser();
        user.setName(request.getUser());
        user.setName(request.getUser());
        user.setPassword(encryption(request.getPassword()));
        user.setUserRole(request.getUserRole());
        systemUserRepo.save(user);
        return user;
    }

    public void logout(SystemUser user) {
        if (user == null) throw new UserNotFoundException();
        var session = new UserSession(user.getName());
        sessions.remove(session.getSessionId());
    }
}
