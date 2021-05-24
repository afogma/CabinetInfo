package local.clinic1.CabinetInfo.cabinets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import local.clinic1.CabinetInfo.exceptions.CabinetAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.CabinetNotFoundException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import local.clinic1.CabinetInfo.exceptions.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CabinetService {

    Logger logger = LoggerFactory.getLogger(CabinetService.class);

    private final CabinetRepo cabinetRepo;

    public List<Cabinet> findAll() {
        return cabinetRepo.findAll().stream()
                .sorted(Comparator.comparing(Cabinet::getNumber))
                .collect(toList());
    }

    public List<Cabinet> findAllByFloorOrDepartment(String department, Integer floor) {
        if (department != null) return cabinetRepo.findAllByDepartment(department);
        if (floor != null) return cabinetRepo.findAllByFloor(floor);
        return emptyList();
    }

    public Cabinet findByNumber(int number) {
        var cab = cabinetRepo.findByNumber(number);
        if (cab == null) throw new CabinetNotFoundException();
        return cab;
    }

    public Cabinet addNewCabinet(Cabinet cabinet) {
        if (cabinetRepo.findByNumber(cabinet.getNumber()) != null) throw new CabinetAlreadyExistException();
        if (cabinet.getNumber() == 0) throw new WrongInputException();
        var cab = cabinetRepo.save(cabinet);
        logger.info("{} added", cab);
        return cab;
    }

    public Cabinet updateByNumber(int number, Cabinet cabinet) {
        if (cabinetRepo.findByNumber(number) == null) throw new CabinetNotFoundException();
        if (number != cabinet.getNumber()) throw new WrongInputException();
        var cab = cabinet;
        cab.setFloor(cabinet.getFloor());
        cab.setDepartment(cabinet.getDepartment());
        cabinetRepo.save(cab);
        logger.info("{} updated", cabinet);
        return cab;
    }

    public void deleteByNumber(int number) {
        var cab = cabinetRepo.findByNumber(number);
        if (cab == null) throw new CabinetNotFoundException();
        cabinetRepo.delete(cab);
        logger.info("{} deleted", cabinetRepo.findByNumber(number));
    }

    public void loadFromJson() {
        var url = this.getClass().getClassLoader().getResource("cabinet_data.json");
        if (url != null) {
            var jsonFile = new File(url.getFile());
            var objectMapper = new ObjectMapper();
            try {
                List<Cabinet> cabinets = objectMapper.readValue(jsonFile, new TypeReference<>() {
                });
                cabinetRepo.saveAll(cabinets);
                logger.info("saved all records");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new URLNotValidException();
        }
    }
}
