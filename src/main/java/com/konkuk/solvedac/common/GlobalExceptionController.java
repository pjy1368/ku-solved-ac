package com.konkuk.solvedac.common;

import com.konkuk.solvedac.exception.InvalidInputException;
import com.konkuk.solvedac.exception.NotFoundException;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    private static final Logger fileLogger = LoggerFactory.getLogger("file");

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> unConstraintViolationHandler(ConstraintViolationException exception) {
        fileLogger.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage().split(":")[1].strip()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> invalidInputExceptionHandler(InvalidInputException exception) {
        fileLogger.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException exception) {
        fileLogger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverExceptionHandler(Exception exception) {
        fileLogger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("앗! 예상치 못한 에러가 발생했습니다. 관리자에게 문의바랍니다."));
    }
}
