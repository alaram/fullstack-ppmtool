package io.mycompany.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex,
                                                                 WebRequest webRequest) {
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex,
                                                                       WebRequest webRequest) {
        ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNameAlreadyExistsException(UsernameAlreadyExistsException ex,
                                                                             WebRequest webRequest) {
        UsernameAlreadyExistsExceptionResponse exceptionResponse = new UsernameAlreadyExistsExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}