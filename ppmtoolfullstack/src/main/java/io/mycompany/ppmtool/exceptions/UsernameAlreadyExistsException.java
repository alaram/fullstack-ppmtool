package io.mycompany.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {

    /**
     *
     * @param message
     */
    public UsernameAlreadyExistsException(String message) { super(message); }
}