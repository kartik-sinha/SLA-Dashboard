package com.example.SLA_Dashboard.ControllerAdvice;

import com.example.SLA_Dashboard.Exceptions.NoDevicePresentException;
import com.example.SLA_Dashboard.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException userNotFoundException){
        return new ResponseEntity<String> ("The username could not be found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDevicePresentException.class)
    public ResponseEntity<String> handleUserNotFound(NoDevicePresentException noDevicePresentException){
        return new ResponseEntity<String> ("No device has been deployed", HttpStatus.BAD_REQUEST);
    }
}
