package onboarding.controller;

import onboarding.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler(BoardAccessException.class)
    public final ResponseEntity<Object> handleAllBoardAccessExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public final ResponseEntity<Object> handleAllDuplicateEmailExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public final ResponseEntity<Object> handleAllEmailNotFoundExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingTokenException.class)
    public final ResponseEntity<Object> handleAllMissingTokenExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAcceptPassword.class)
    public final ResponseEntity<Object> handleAllNotAcceptPasswordExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundBoard.class)
    public final ResponseEntity<Object> handleAllNotFoundBoardExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundMember.class)
    public final ResponseEntity<Object> handleAllNotFoundMemberExceptionExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }



}
