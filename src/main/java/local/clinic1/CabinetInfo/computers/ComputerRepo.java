package local.clinic1.CabinetInfo.computers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputerRepo extends JpaRepository<Computer, String> {

    List<Computer> findAllByRam(String ram);

    List<Computer> findAllByProcessor(String processor);

    Computer findByName(String name);

    List<Computer> findAllByCabinet(int cabinet);
}
