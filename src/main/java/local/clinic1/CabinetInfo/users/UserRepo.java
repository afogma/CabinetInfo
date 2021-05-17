package local.clinic1.CabinetInfo.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findByName(String name);

    List<User> findByCabinet(int cabinet);

    List<User> findBypcName(String pcName);

    List<User> findByPosition(String position);

    List<User> findByDepartment(String department);
}
