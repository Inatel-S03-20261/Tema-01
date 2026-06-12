package com.inatel.auth_service.controller.common;

import com.inatel.auth_service.dto.ErrorResponseDTO;
import com.inatel.auth_service.exception.PasswordsDoNotMatchException;
import com.inatel.auth_service.exception.UserAlreadyExistsException;
import com.inatel.auth_service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return ErrorResponseDTO.conflict(e.getMessage());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handlePasswordsDoNotMatchException(PasswordsDoNotMatchException e){
        return ErrorResponseDTO.badRequest(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleUserNotFoundException(UserNotFoundException e){
        return ErrorResponseDTO.notFound(e.getMessage());
    }
}
