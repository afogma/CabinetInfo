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
            return new ResponseEntity<>("Cabinet already exist", new HttpHeaders(), HttpStatus.CONFLICT);
        if (ex instanceof ComputerAlreadyExistException)
            return new ResponseEntity<>("Computer already exist", new HttpHeaders(), HttpStatus.CONFLICT);
        if (ex instanceof PrinterNotFoundException)
            return new ResponseEntity<>("Printer not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof PrinterAlreadyExistException)
            return new ResponseEntity<>("Printer already exist", new HttpHeaders(), HttpStatus.CONFLICT);
        if (ex instanceof UserNotFoundException)
            return new ResponseEntity<>("User not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof UsersNotFoundException)
            return new ResponseEntity<>("Users not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof UserAlreadyExistException)
            return new ResponseEntity<>("User already exist", new HttpHeaders(), HttpStatus.CONFLICT);
        if (ex instanceof URLNotValidException)
            return new ResponseEntity<>("url not valid", new HttpHeaders(), HttpStatus.NOT_FOUND);
        if (ex instanceof WrongInputException)
            return new ResponseEntity<>("Wrong input", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
        if (ex instanceof AuthenticationFailedException)
            return new ResponseEntity<>("Authentication failed", new HttpHeaders(), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<String>("Other error", HttpStatus.I_AM_A_TEAPOT);
    }

//    @ExceptionHandler(CabinetAlreadyExistException.class)
//    public ResponseEntity<String> handleCabinetAlreadyExistException(CabinetAlreadyExistException ex) {
//        return new ResponseEntity<>("Cabinet already exist", new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(ComputerNotFoundException.class)
//    public ResponseEntity<String> handleComputerNotFoundException(ComputerNotFoundException ex) {
//        return new ResponseEntity<>("Computer not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(ComputerAlreadyExistException.class)
//    public ResponseEntity<String> handleComputerAlreadyExistException(ComputerAlreadyExistException ex) {
//        return new ResponseEntity<>("Computer already exist", new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(PrinterNotFoundException.class)
//    public ResponseEntity<String> handlePrinterNotFoundException(Exception ex, WebRequest request) {
//        if (ex instanceof PrinterNotFoundException)
//            return new ResponseEntity<>("Printer not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(PrinterAlreadyExistException.class)
//    public ResponseEntity<String> handlePrinterAlreadyExistException(Exception ex, WebRequest request) {
//        if (ex instanceof PrinterAlreadyExistException)
//            return new ResponseEntity<>("Printer already exist", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> handleUserNotFoundException(Exception ex, WebRequest request) {
//        if (ex instanceof UserNotFoundException)
//            return new ResponseEntity<>("User not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(UsersNotFoundException.class)
//    public ResponseEntity<String> handleUsersNotFoundException(Exception ex, WebRequest request) {
//        if (ex instanceof UsersNotFoundException)
//            return new ResponseEntity<>("Users not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(UserAlreadyExistException.class)
//    public ResponseEntity<String> handleUserAlreadyExistException(Exception ex, WebRequest request) {
//        if (ex instanceof UserAlreadyExistException)
//            return new ResponseEntity<>("User already exist", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(URLNotValidException.class)
//    public ResponseEntity<String> handleURLNotValidException(Exception ex, WebRequest request) {
//        if (ex instanceof URLNotValidException)
//            return new ResponseEntity<>("url not valid", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(WrongInputException.class)
//    public ResponseEntity<String> handleWrongInputException(Exception ex, WebRequest request) {
//        if (ex instanceof WrongInputException)
//            return new ResponseEntity<>("Wrong input", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<String> handleAuthenticationFailedException(Exception ex, WebRequest request) {
        if (ex instanceof AuthenticationFailedException)
            return new ResponseEntity<>("Authentication failed", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<String>("Other error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}