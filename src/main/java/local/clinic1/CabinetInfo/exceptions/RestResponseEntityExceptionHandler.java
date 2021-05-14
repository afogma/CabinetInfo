package local.clinic1.CabinetInfo.exceptions;

import local.clinic1.CabinetInfo.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        if (ex instanceof CabinetNotFoundException)
            return new ResponseEntity<>("Cabinet not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof ComputerNotFoundException)
            return new ResponseEntity<>("Computer not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof CabinetAlreadyExistException)
            return new ResponseEntity<>("Cabinet already exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (ex instanceof ComputerAlreadyExistException)
            return new ResponseEntity<>("Computer already exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (ex instanceof PrinterNotFoundException)
            return new ResponseEntity<>("Printer not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof PrinterAlreadyExistException)
            return new ResponseEntity<>("Printer already exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (ex instanceof UserNotFoundException)
            return new ResponseEntity<>("User not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof UsersNotFoundException)
            return new ResponseEntity<>("Users not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof UserAlreadyExistException)
            return new ResponseEntity<>("User already exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (ex instanceof URLNotValidException)
            return new ResponseEntity<>("url not valid", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof WrongInputException)
            return new ResponseEntity<>("Wrong input", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
        if (ex instanceof AuthenticationFailedException)
            return new ResponseEntity<>("Authentication failed", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        if (ex instanceof PermissionDeniedException)
            return new ResponseEntity<>("Permission denied", new HttpHeaders(), HttpStatus.PAYMENT_REQUIRED);

        return new ResponseEntity<String>("Other error", HttpStatus.I_AM_A_TEAPOT);
    }
}