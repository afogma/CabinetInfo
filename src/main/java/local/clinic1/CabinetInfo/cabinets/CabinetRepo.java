package local.clinic1.CabinetInfo.cabinets;

import local.clinic1.CabinetInfo.cabinets.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabinetRepo extends JpaRepository<Cabinet, Integer> {

    List<Cabinet> findAllByFloor(int floor);

    List<Cabinet> findAllByDepartment(String department);

    Cabinet findByNumber(int number);

}
