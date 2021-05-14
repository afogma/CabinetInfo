package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
import local.clinic1.CabinetInfo.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
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
        SystemUser user = systemUserRepo.findByName(login.getUser());
        if (user == null) throw new AuthenticationFailedException();;
        if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) throw new AuthenticationFailedException();;

        UserSession session = new UserSession(login.getUser());
        sessions.put(session.getSessionId(), session);
        return session;
    }

    public UserSession getSession(String session, String token) {
        UserSession s = sessions.get(session);
        if (s == null) {
            throw new AuthenticationFailedException();
        }
        if (!s.getToken().equals(token)) {
            throw new AuthenticationFailedException();
        }
        return s;
    }

    public SystemUser getUser(String session, String token) {
        UserSession s = getSession(session, token);
        if (s == null) {
            throw new AuthenticationFailedException();
        }
        String userName = s.getUserName();
        SystemUser user = systemUserRepo.findByName(userName);
        return user;
    }

    private String encryption(String password) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        return encryptedPassword;
    }

    public SystemUser register(Login login, String role) {
        if (login == null) {
            throw new UserNotFoundException();
        }
        SystemUser user = new SystemUser();
        user.setName(login.getUser());
        user.setPassword(encryption(login.getPassword()));
        user.setUserRole(SystemUser.UserRole.valueOf(role.toUpperCase(Locale.ROOT)));
        systemUserRepo.save(user);
        return user;
    }

    public void logout(Login login) {
        if (login == null) throw new UserNotFoundException();
        UserSession session = new UserSession(login.getUser());
        String sessionId = session.getSessionId();
        if (sessions.containsKey(sessionId)) {
            sessions.remove(session.getSessionId(), session);
        }
    }
}
