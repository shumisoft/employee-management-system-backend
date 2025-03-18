package com.shumisoft.employee_management_system.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.shumisoft.employee_management_system.dto.common.ErrorMessageDTO;
import com.shumisoft.employee_management_system.exception.InvalidTokenException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageDTO> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorMessageDTO.builder().message(e.getMostSpecificCause().getMessage()).build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessageDTO> httpRequestMethodNotSupportedExceptionHandler(
            HttpRequestMethodNotSupportedException e) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDTO> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessageDTO> methodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageDTO> illegalArgumentExceptionHandler(IllegalArgumentException e) {

        return ResponseEntity.badRequest().body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> entityNotFoundExceptionHandler(EntityNotFoundException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessageDTO> noResourceFoundExceptionHandler(NoResourceFoundException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e) {

        return ResponseEntity.badRequest()
                .body(ErrorMessageDTO.builder().message(e.getAllErrors().getFirst().getDefaultMessage()).build());

    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessageDTO> invalidTokenExceptionHandler(InvalidTokenException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());

    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorMessageDTO> authorizationDeniedExceptionHandler(AuthorizationDeniedException e) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorMessageDTO.builder().message("Acess Denied").build());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> defaultHandler(Exception e) {
        log.warn(e.getMessage() + e.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());
    }
}