package org.lawify.psp.mediator.shared.exceptions;

import org.lawify.psp.mediator.transactions.erros.InvalidTransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiBadRequest.class})
    public ResponseEntity<Object> handleApiBadRequestException(ApiBadRequest e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException payload = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(payload, badRequest);
    }
    @ExceptionHandler(value = {ApiNotFound.class})
    public ResponseEntity<Object> handleApiNotFoundRequestException(ApiNotFound e){
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ApiException payload = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(payload, badRequest);
    }
    @ExceptionHandler(value = {ApiConflict.class})
    public ResponseEntity<Object> handleApiConflictRequestException(ApiConflict e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiException payload = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(payload, badRequest);
    }
    @ExceptionHandler(value = {ApiUnauthorized.class})
    public ResponseEntity<Object> handleApiUnauthorizedRequestException(ApiUnauthorized e){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException payload = new ApiException(
                e.getMessage(),
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(payload, unauthorized);
    }
    @ExceptionHandler(value = {InvalidTransactionStatus.class})
    public ResponseEntity<Object> handleInvalidTransactionStatus(InvalidTransactionStatus e){
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException payload = new ApiException(
                e.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(payload, conflict);
    }
}
