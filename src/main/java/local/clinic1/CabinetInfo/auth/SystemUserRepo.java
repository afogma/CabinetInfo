package local.clinic1.CabinetInfo.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepo extends JpaRepository<SystemUser, Long> {

    SystemUser findByName(String name);
}
