package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// @ControllerAdvice means this class intercepts exceptions thrown by ANY controller
// in your application — it's a global safety net
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler tells Spring: "when a MethodArgumentNotValidException is thrown
    // anywhere in the app, run this method instead of returning a generic 500 error"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> map = new HashMap<>();

        // getBindingResult() gives us all the validation failures
        // getFieldErrors() gives us a list of which specific fields failed and why
        // forEach iterates over each one and adds it to the map as fieldName -> errorMessage
        ex.getBindingResult().getFieldErrors().forEach(
                error -> map.put(error.getField(), error.getDefaultMessage())
        );

        // badRequest() sets the HTTP status to 400, and body(map) sends the
        // map as a JSON object like: { "email": "email should be valid" }
        return ResponseEntity.badRequest().body(map);
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex)
    {
        log.warn("Email address already exists!{}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message","Email adddress already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundExeption(PatientNotFoundException ex)
    {
        log.warn("Patient not found!{}", ex.getMessage());

        Map<String,String> errors = new HashMap<>();
        errors.put("message","Patient not found");
        return ResponseEntity.badRequest().body(errors);
    }

}
