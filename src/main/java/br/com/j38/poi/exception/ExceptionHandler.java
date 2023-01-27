package br.com.j38.poi.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice 
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";
    private static final String CONSTANT_VALIDATION_PATTERN = "Pattern";
    private static final String CONSTANT_VALIDATION_MIN = "Min";
    private static final String CONSTANT_VALIDATION_MAX = "Max";

    @org.springframework.web.bind.annotation.ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
            WebRequest request) {
        String userMessage = "Resource not found.";
        String devMessage = ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            WebRequest request) {
        String userMessage = "Integrity violation exception.";
        String devMessage = ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessRulesException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRulesException ex,
            WebRequest request) {
        String userMessage = ex.getMessage();
        String devMessage = ex.getMessage();
        List<CustomError> errors = Arrays.asList(new CustomError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<CustomError> doErrorList(BindingResult bindingResult) {
        List<CustomError> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String userMessage = handleErrorMessageForUser(fieldError);
            String devMessage = fieldError.toString();
            errors.add(new CustomError(userMessage, devMessage));
        });
        return errors;
    }

    private String handleErrorMessageForUser(FieldError fieldError) {
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
            return fieldError.getDefaultMessage().concat(" is required.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_NULL)) {
            return fieldError.getDefaultMessage().concat(" is required.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
            return fieldError.getDefaultMessage().concat(String.format(" must be between %s and %s characters.",
                    fieldError.getArguments()[2], fieldError.getArguments()[1]));
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_PATTERN)) {
            return fieldError.getDefaultMessage().concat(" invalid format.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_MIN)) {
            return fieldError.getDefaultMessage().concat(String.format(" must be greater than or equal to %s.",
                    fieldError.getArguments()[1]));
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_MAX)) {
            return fieldError.getDefaultMessage().concat(String.format(" must be less than or equal to %s.",
                    fieldError.getArguments()[1]));
        }
        return fieldError.toString();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<CustomError> errors = doErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }
}
