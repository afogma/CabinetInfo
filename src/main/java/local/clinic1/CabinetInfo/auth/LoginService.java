package local.clinic1.CabinetInfo.auth;

import local.clinic1.CabinetInfo.cabinets.CabinetService;
import local.clinic1.CabinetInfo.exceptions.AuthenticationFailedException;
import local.clinic1.CabinetInfo.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class LoginService {

    Logger logger = LoggerFactory.getLogger(CabinetService.class);

    private final SystemUserRepo systemUserRepo;

    private final Map<String, UserSession> sessions = new HashMap<>();
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserSession login(Login login, String ipAddress) {
        var user = systemUserRepo.findByName(login.getUser());
        if (user == null) throw new AuthenticationFailedException();

        if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new AuthenticationFailedException();

        var session = new UserSession(login.getUser(), ipAddress);
        sessions.put(session.getSessionId(), session);
        logger.info("{} logged in", login);
        return session;
    }

    public UserSession getSession(String session, String token) {
        var s = sessions.get(session);
        if (s == null) throw new AuthenticationFailedException();
        if (!s.getToken().equals(token)) throw new AuthenticationFailedException();
        return s;
    }

    public SystemUser getUser(String session, String token) {
        var s = getSession(session, token);
        if (s == null) throw new AuthenticationFailedException();

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
        logger.info("{} registered", user);
        return user;
    }

    public void logout(String session, String token) {
        var sessionToken = sessions.get(session).getToken();
        if (!sessionToken.equals(token)) throw new AuthenticationFailedException();
        sessions.remove(session);
        var name = sessions.get(session).getUserName();
        logger.info("{} logged out", name);
    }
}
