package local.clinic1.CabinetInfo.computers.service;

import local.clinic1.CabinetInfo.computers.entity.Computer;
import local.clinic1.CabinetInfo.computers.repository.ComputerRepo;
import local.clinic1.CabinetInfo.exceptions.ComputerAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.ComputerNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComputerServiceTest {

    ComputerRepo computerRepo = mock(ComputerRepo.class);
    ComputerService computerService = new ComputerService(computerRepo);

    public Computer getComputer() {
        Computer computer = new Computer();
        computer.setName("kab333");
        computer.setRam("1gb");
        computer.setPassword("intel core2duo");
        computer.setIpAddress("10.70.62.1");
        computer.setCabinet(333);
        computer.setLogin("k333");
        computer.setPassword("gt56yh");
        return computer;
    }

    @Test
    void findPCByName() {
    }

    @Test
    void addNewPC() {
        Computer computer = getComputer();
        when(computerRepo.save(computer)).thenReturn(computer);
        Computer newPC = computerService.addNewPC(computer);
        assertEquals(computer, newPC);
        verify(computerRepo).save(computer);
    }

    @Test
    void updatePCByName() {
        Computer computer = getComputer();
        Computer newPC = computer;
        newPC.setRam("2gb");
        newPC.setCabinet(335);
        newPC.setIpAddress("10.70.64.2");
        when(computerRepo.findByName("kab333")).thenReturn(computer);
        when(computerRepo.save(newPC)).thenReturn(newPC);
        Computer result =  computerService.updatePCByName("kab333", newPC);
        assertEquals(result, newPC);
    }

    @Test
    void deletePCByName() {
        Computer computer = getComputer();
        when(computerRepo.findByName(computer.getName())).thenReturn(computer);
        computerService.deletePCByName(computer.getName());
        verify(computerRepo).delete(computer);
    }

    @Test
    void findAll() {
        List<Computer> computers = asList(getComputer());
        when(computerRepo.findAll()).thenReturn(computers);
        List<Computer> listOfPCs = computerService.findAll();
        assertEquals(listOfPCs, computers);
        verify(computerRepo).findAll();
    }

    @Test
    void findAllByCabinet() {
        List<Computer> computers = asList(getComputer());
        when(computerRepo.findAllByCabinet(333)).thenReturn(computers);
        List<Computer> cabs = computerService.findAllByCabinet(333);
        assertEquals(cabs, computers);
    }

    @Test
    void findComputersByRamOrProcessor() {
        List<Computer> computers = asList(getComputer());
        when(computerRepo.findAllByRam("1gb")).thenReturn(computers);
        when(computerRepo.findAllByProcessor("intel core2duo")).thenReturn(computers);
        List<Computer> pcs = computerService.findComputersByRamOrProcessor("1gb", null);
        List<Computer> pcs2 = computerService.findComputersByRamOrProcessor(null, "intel core2duo");
        assertEquals(pcs, computers);
        assertEquals(pcs2, computers);
    }

    @Test
    public void should_throw_exception_when_computer_doesnt_exist() {
        Computer computer = getComputer();
        computer.setName("kab555");
        when(computerRepo.findByName("kab555")).thenThrow(new ComputerNotFoundException());
        assertThrows(ComputerNotFoundException.class, () -> computerRepo.findByName(computer.getName()));
    }

    @Test
    public void should_throw_exception_when_computer_already_exist() {
        Computer computer = getComputer();
        when(computerRepo.findByName("kab333")).thenThrow(new ComputerAlreadyExistException());
        assertThrows(ComputerAlreadyExistException.class, () -> computerRepo.findByName(computer.getName()));
    }
}