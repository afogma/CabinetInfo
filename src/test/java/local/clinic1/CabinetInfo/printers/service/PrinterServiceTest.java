package local.clinic1.CabinetInfo.printers.service;

import local.clinic1.CabinetInfo.exceptions.PrinterAlreadyExistException;
import local.clinic1.CabinetInfo.exceptions.PrinterNotFoundException;
import local.clinic1.CabinetInfo.printers.Printer;
import local.clinic1.CabinetInfo.printers.PrinterRepo;
import local.clinic1.CabinetInfo.printers.PrinterService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PrinterServiceTest {

    PrinterRepo printerRepo = mock(PrinterRepo.class);
    PrinterService printerService = new PrinterService(printerRepo);

    public Printer getPrinter() {
        Printer printer = new Printer();
        printer.setId(1L);
        printer.setName("HP LaserJet 1606dn");
        printer.setCartridge("78a / 728");
        printer.setCabinet(333);
        printer.setConnectionType(Printer.ConnectionType.USB);
        printer.setIpAddress("");
        return printer;
    }

    @Test
    void findAll() {
        List<Printer> printers = asList(getPrinter());
        when(printerRepo.findAll()).thenReturn(printers);
        List<Printer> listOfPrinters = printerService.findAll();
        assertEquals(listOfPrinters, printers);
        verify(printerRepo).findAll();
    }

    @Test
    void addNewPrinter() {
        Printer printer = getPrinter();
        when(printerRepo.save(printer)).thenReturn(printer);
        Printer newPrinter = printerService.addNewPrinter(printer);
        assertEquals(printer, newPrinter);
        verify(printerRepo).save(printer);
    }

    @Test
    void findAllByName() {
        List<Printer> printers = asList(getPrinter());
        Printer printer = getPrinter();
        when(printerRepo.findByName("HP LaserJet 1606dn")).thenReturn(printers);
        List<Printer> listOfPrinters = printerService.findAllByName("HP LaserJet 1606dn");
        assertEquals(listOfPrinters, printers);
        verify(printerRepo).findByName(printer.getName());
    }

    @Test
    void findAllByCabinet() {
        List<Printer> printers = asList(getPrinter());
        Printer printer = getPrinter();
        when(printerRepo.findByCabinet(333)).thenReturn(printers);
        List<Printer> listOfPrinters = printerService.findAllByCabinet(333);
        assertEquals(listOfPrinters, printers);
        verify(printerRepo).findByCabinet(printer.getCabinet());
    }

    @Test
    void updateById() {
        Printer printer = getPrinter();
        Printer newPrinter = printer;
        newPrinter.setId(2L);
        newPrinter.setName("HP LaserJet 1606dn copy");
        newPrinter.setCartridge("78a/728");
        newPrinter.setCabinet(335);
        printer.setConnectionType(Printer.ConnectionType.USB);
        printer.setIpAddress("");
        when(printerRepo.findPrinterById(1L)).thenReturn(printer);
        when(printerRepo.save(newPrinter)).thenReturn(newPrinter);
        Printer result =  printerService.updateById(1L, newPrinter);
        assertEquals(result, newPrinter);
    }

    @Test
    void deletePrinter() {
        Printer printer = getPrinter();
        when(printerRepo.findPrinterById(printer.getId())).thenReturn(printer);
        printerService.deletePrinter(printer.getId());
        verify(printerRepo).delete(printer);
    }

    @Test
    public void should_throw_exception_when_printer_doesnt_exist() {
        Printer printer = getPrinter();
        printer.setId(2L);
        when(printerRepo.findPrinterById(2L)).thenThrow(new PrinterNotFoundException());
        assertThrows(PrinterNotFoundException.class, () -> printerRepo.findPrinterById(printer.getId()));
    }

    @Test
    public void should_throw_exception_when_cabinet_already_exist() {
        Printer printer = getPrinter();
        when(printerRepo.findPrinterById(1L)).thenThrow(new PrinterAlreadyExistException());
        assertThrows(PrinterAlreadyExistException.class, () -> printerRepo.findPrinterById(printer.getId()));
    }
}