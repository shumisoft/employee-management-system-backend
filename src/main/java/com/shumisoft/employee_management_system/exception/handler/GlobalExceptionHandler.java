package com.shumisoft.employee_management_system.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.shumisoft.employee_management_system.dto.common.ErrorMessageDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageDTO> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorMessageDTO.builder().message(e.getMessage()).build());
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

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorMessageDTO> defaultHandler(Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(ErrorMessageDTO.builder().message(e.getMessage()).build());
    // }
}