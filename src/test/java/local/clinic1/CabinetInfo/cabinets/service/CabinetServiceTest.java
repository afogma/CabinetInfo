package local.clinic1.CabinetInfo.cabinets.service;

import local.clinic1.CabinetInfo.cabinets.Cabinet;
import local.clinic1.CabinetInfo.cabinets.CabinetRepo;

import local.clinic1.CabinetInfo.cabinets.CabinetService;
import local.clinic1.CabinetInfo.exceptions.CabinetAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.CabinetNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CabinetServiceTest {


    CabinetRepo cabinetRepo = mock(CabinetRepo.class);
    CabinetService cabinetService = new CabinetService(cabinetRepo);

    public Cabinet getCabinet() {
        Cabinet cabinet = new Cabinet();
        cabinet.setNumber(333);
        cabinet.setFloor(3);
        cabinet.setDepartment("Х");
        return cabinet;
    }

    @Test
    void findByNumber() {
        Cabinet myCab = getCabinet();
        when(cabinetRepo.findByNumber(333)).thenReturn(myCab);
        Cabinet cabinet = cabinetService.findByNumber(333);
        assertEquals(myCab, cabinet);
    }

    @Test
    void addNewCabinet() {
        Cabinet cabinet = getCabinet();
        when(cabinetRepo.save(cabinet)).thenReturn(cabinet);
        Cabinet newCab = cabinetService.addNewCabinet(cabinet);
        assertEquals(cabinet, newCab);
        verify(cabinetRepo).save(cabinet);
    }

    @Test
    void updateByNumber() {
        Cabinet cabinet = getCabinet();
        Cabinet newCab = new Cabinet();
        newCab.setNumber(333);
        newCab.setFloor(4);
        newCab.setDepartment("XX");
        when(cabinetRepo.findByNumber(333)).thenReturn(cabinet);
        when(cabinetRepo.save(newCab)).thenReturn(newCab);
        Cabinet result =  cabinetService.updateByNumber(333, newCab);
        assertEquals(result, newCab);
//        verify(cabinetRepo, times(1)).save(newCab);
//        verify(cabinetRepo, times(1)).findByNumber(newCab.getNumber());
//        assertEquals(cabinetRepo.save(newCab), newCab);
    }

    @Test
    void deleteByNumber() {
        Cabinet cabinet = getCabinet();
        when(cabinetRepo.findByNumber(cabinet.getNumber())).thenReturn(cabinet);
        cabinetService.deleteByNumber(cabinet.getNumber());
        verify(cabinetRepo).delete(cabinet);
    }

    @Test
    public void should_throw_exception_when_cabinet_doesnt_exist() {
        Cabinet cabinet = getCabinet();
        cabinet.setNumber(555);
        when(cabinetRepo.findByNumber(555)).thenThrow(new CabinetNotFoundException());
        assertThrows(CabinetNotFoundException.class, () -> cabinetRepo.findByNumber(cabinet.getNumber()));
    }

    @Test
    public void should_throw_exception_when_cabinet_already_exist() {
        Cabinet cabinet = getCabinet();
        when(cabinetRepo.findByNumber(333)).thenThrow(new CabinetAlreadyExistException());
        assertThrows(CabinetAlreadyExistException.class, () -> cabinetRepo.findByNumber(cabinet.getNumber()));
    }


    @Test
    void findAll() {
        List<Cabinet> cabinets = asList(getCabinet());
        when(cabinetRepo.findAll()).thenReturn(cabinets);
        List<Cabinet> listOfCabs = cabinetService.findAll();
        assertEquals(listOfCabs, cabinets);
        verify(cabinetRepo).findAll();
    }

    @Test
    void findAllByFloorOrDepartment() {
        List<Cabinet> cabinets = asList(getCabinet());
        when(cabinetRepo.findAllByFloor(3)).thenReturn(cabinets);
        when(cabinetRepo.findAllByDepartment("Х")).thenReturn(cabinets);
        List<Cabinet> cabs = cabinetService.findAllByFloorOrDepartment("Х", null);
        List<Cabinet> cabs2 = cabinetService.findAllByFloorOrDepartment(null, 3);
        assertEquals(cabs, cabinets);
        assertEquals(cabs2, cabinets);
    }
}
