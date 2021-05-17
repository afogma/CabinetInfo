package local.clinic1.CabinetInfo.auth;

import lombok.Data;

import java.util.Base64;

import static java.util.UUID.randomUUID;

@Data
public class UserSession {

    private final String userName;
    private final String sessionId = randomUUID().toString();
    private final String token = Base64.getEncoder().encodeToString(randomUUID().toString().getBytes());
    private final String ipAddress = "";

}
