package local.clinic1.CabinetInfo.computers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import local.clinic1.CabinetInfo.exceptions.ComputerAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.ComputerNotFoundException;
import local.clinic1.CabinetInfo.exceptions.URLNotValidException;
import local.clinic1.CabinetInfo.exceptions.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerService {

    Logger logger = LoggerFactory.getLogger(ComputerService.class);

    private final ComputerRepo computerRepo;

    public Computer findPCByName(String name) throws ComputerNotFoundException {
        var pc = computerRepo.findByName(name);
        if (pc == null) {
            throw new ComputerNotFoundException();
        }
        return pc;
    }

    public Computer addNewPC(Computer pc) throws ComputerAlreadyExistException {
        if (computerRepo.findByName(pc.getName()) != null) {
            throw new ComputerAlreadyExistException();
        }
        var computer = computerRepo.save(pc);
        logger.info("{} added", computer);
        return computer;
    }

    public Computer updatePCByName(String name, Computer pc) {
        var computer = computerRepo.findByName(name);
        if (computer.equals(pc)) {
            throw new WrongInputException();
        }
        var comp = computerRepo.findByName(name);
        comp.setRam(pc.getRam());
        comp.setProcessor(pc.getProcessor());
        comp.setIpAddress(pc.getIpAddress());
        comp.setCabinet(pc.getCabinet());
        comp.setLogin(pc.getLogin());
        comp.setPassword(pc.getPassword());
        computerRepo.save(comp);
        logger.info("{} updated", pc);
        return comp;
    }

    public void deletePCByName(String name) throws ComputerNotFoundException {
        var pc = computerRepo.findByName(name);
        if (pc == null) {
            throw new ComputerNotFoundException();
        }
        computerRepo.delete(pc);
        logger.info("{} deleted", computerRepo.findByName(name));
    }

    public List<Computer> findAll() {
        List<Computer> listOfComputers = computerRepo.findAll();
        listOfComputers.sort(Comparator.comparing(Computer::getName));
        return listOfComputers;
    }

    public List<Computer> findAllByCabinet(int cabinet) {
        List<Computer> listOfPCsInCabinet = computerRepo.findAllByCabinet(cabinet);
        listOfPCsInCabinet.sort(Comparator.comparing(Computer::getCabinet));
        return listOfPCsInCabinet;
    }

    public List<Computer> findComputersByRamOrProcessor(String ram, String processor) {
        List<Computer> list = new ArrayList<>();
        if (ram != null) {
            list = computerRepo.findAllByRam(ram);
        }
        if (processor != null) {
            list = computerRepo.findAllByProcessor(processor);
        }
        return list;
    }

    public void loadFromJson() throws URLNotValidException {
        var url = this.getClass().getClassLoader().getResource("computer_data.json");
        if (url != null) {
            File jsonFile = new File(url.getFile());
            var objectMapper = new ObjectMapper();
            try {
                List<Computer> computers = objectMapper.readValue(jsonFile, new TypeReference<>() {
                });
                computerRepo.saveAll(computers);
                logger.info("saved all records");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new URLNotValidException();
        }
    }
}
