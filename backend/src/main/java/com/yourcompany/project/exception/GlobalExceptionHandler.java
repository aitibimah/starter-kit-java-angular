package com.yourcompany.project.exception;

import com.yourcompany.project.domain.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                "Validation failed: " + errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Validation failed: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Authentication failed: " + ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
                "Invalid username or password",
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(
                "Access denied: " + ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {
        ErrorResponse error = new ErrorResponse(
                "Method not allowed: " + ex.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(
                "Parameter type mismatch: " + ex.getName() + " should be of type "
                        + ex.getRequiredType().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        ErrorResponse error = new ErrorResponse(
                "Missing parameter: " + ex.getParameterName(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ErrorResponse error = new ErrorResponse(
                "File size exceeds maximum limit",
                HttpStatus.PAYLOAD_TOO_LARGE.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> handleMultipartException(MultipartException ex) {
        ErrorResponse error = new ErrorResponse(
                "File upload failed: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                "Invalid argument: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
