package local.clinic1.CabinetInfo.cabinets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import local.clinic1.CabinetInfo.exceptions.CabinetNotFoundException;
import local.clinic1.CabinetInfo.exceptions.CabinetAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import local.clinic1.CabinetInfo.exceptions.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CabinetService {

    Logger logger = LoggerFactory.getLogger(CabinetService.class);

    private final CabinetRepo cabinetRepo;

    public Cabinet findByNumber(int number) {
        Cabinet cab = cabinetRepo.findByNumber(number);
        if (cab == null) {
            throw new CabinetNotFoundException();
        }
        return cab;
    }

    public Cabinet addNewCabinet(Cabinet cabinet) {
        if (cabinetRepo.findByNumber(cabinet.getNumber()) != null) {
            throw new CabinetAlreadyExistException();
        }
        if (cabinet.getNumber() == 0 ) {
            throw new WrongInputException();
        }
        Cabinet cab = cabinetRepo.save(cabinet);
        logger.info("{} added", cab);
        return cab;
    }

    public Cabinet updateByNumber(int number, Cabinet cabinet) {
        if (cabinetRepo.findByNumber(number) == null) {
            throw new CabinetNotFoundException();
        }
        if (number != cabinet.getNumber()) {
            throw new WrongInputException();
        }
        Cabinet cab = cabinet;
        cab.setFloor(cabinet.getFloor());
        cab.setDepartment(cabinet.getDepartment());
        cabinetRepo.save(cab);
        logger.info("{} updated", cabinet);
        return cab;
    }

    public void deleteByNumber(int number) {
        Cabinet cab = cabinetRepo.findByNumber(number);
        if (cab == null) {
            throw new CabinetNotFoundException();
        }
        cabinetRepo.delete(cab);
        logger.info("{} deleted" , cabinetRepo.findByNumber(number));
    }

    public List<Cabinet> findAll() {
        List<Cabinet> listOfCabinets = (List<Cabinet>) cabinetRepo.findAll();
        listOfCabinets.sort(Comparator.comparingInt(Cabinet::getNumber));
        return listOfCabinets;
    }

    public List<Cabinet> findAllByFloorOrDepartment(String department, Integer floor) {
        List<Cabinet> list = new ArrayList<>();
        if (department != null) {
            list = cabinetRepo.findAllByDepartment(department);
        }
        if (floor != null) {
            list = cabinetRepo.findAllByFloor(floor);
        }
        return list;
    }

    public void loadFromJson() throws URLNotValidException {
        URL url = this.getClass().getClassLoader().getResource("cabinet_data.json");
        if (url != null) {
            File jsonFile = new File(url.getFile());
            ObjectMapper objectMapper = new ObjectMapper();
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
