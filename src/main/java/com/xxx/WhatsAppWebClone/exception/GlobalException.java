package com.xxx.WhatsAppWebClone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException exception, WebRequest request){

        ErrorDetail errorDetail=ErrorDetail.builder()
                .message(exception.getMessage())
                .error(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> ChatExceptionHandler(ChatException exception, WebRequest request){

        ErrorDetail errorDetail=ErrorDetail.builder()
                .message(exception.getMessage())
                .error(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> MessageExceptionHandler(MessageException exception, WebRequest request){

        ErrorDetail errorDetail=ErrorDetail.builder()
                .message(exception.getMessage())
                .error(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> OtherExceptionHandler(Exception exception, WebRequest request){

        ErrorDetail errorDetail=ErrorDetail.builder()
                .message(exception.getMessage())
                .error(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }
}
