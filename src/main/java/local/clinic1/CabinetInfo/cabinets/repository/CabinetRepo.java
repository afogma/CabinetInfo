package local.clinic1.CabinetInfo.cabinets.repository;

import local.clinic1.CabinetInfo.cabinets.entity.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CabinetRepo extends JpaRepository<Cabinet, Integer> {

    List<Cabinet> findAllByFloor(int floor);

    List<Cabinet> findAllByDepartment(String department);

    Cabinet findByNumber(int number);

}
