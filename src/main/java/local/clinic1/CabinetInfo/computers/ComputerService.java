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

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ComputerService {

    Logger logger = LoggerFactory.getLogger(ComputerService.class);

    private final ComputerRepo computerRepo;

    public List<Computer> findAll(boolean isLoggedIn) {
        return computerRepo.findAll().stream()
                .sorted(Comparator.comparing(Computer::getName))
                .map(c -> isLoggedIn ? c : c.withoutPassword())
                .collect(toList());
    }

    public Computer findPCByName(String name, boolean isLoggedIn) {
        var pc = computerRepo.findByName(name);
        if (pc == null) throw new ComputerNotFoundException();
        if (!isLoggedIn) return pc.withoutPassword();
        return pc;
    }

    public List<Computer> findAllByCabinet(int cabinet, boolean isLoggedIn) {
        return computerRepo.findAllByCabinet(cabinet).stream()
                .sorted(Comparator.comparing(Computer::getCabinet))
                .map(c -> isLoggedIn ? c : c.withoutPassword())
                .collect(toList());
    }

    public List<Computer> findComputersByRamOrProcessor(String ram, String processor, boolean isLoggedIn) {
        List<Computer> computers = new ArrayList<>();
        if (ram != null) computers = computerRepo.findAllByRam(ram);
        if (processor != null) computers = computerRepo.findAllByProcessor(processor);
        if (!isLoggedIn) computers.forEach(p -> p.setPassword(""));
        return computers;
    }

    public Computer addNewPC(Computer pc) {
        if (computerRepo.findByName(pc.getName()) != null) throw new ComputerAlreadyExistException();
        if (pc.getName().isEmpty()) throw new WrongInputException();
        var computer = computerRepo.save(pc);
        logger.info("{} added", computer);
        return computer;
    }

    public Computer updatePCByName(String name, Computer pc) {
        var comp = computerRepo.findByName(name);
        if (comp.equals(pc)) throw new WrongInputException();
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

    public void deletePCByName(String name) {
        var pc = computerRepo.findByName(name);
        if (pc == null) throw new ComputerNotFoundException();
        computerRepo.delete(pc);
        logger.info("{} deleted", computerRepo.findByName(name));
    }

    public void loadFromJson() {
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
