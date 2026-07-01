package br.lucas.petspring.handler;

import br.lucas.petspring.exception.BadRequestException;
import br.lucas.petspring.exception.ErrorResponse;
import br.lucas.petspring.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import java.time.Instant;

@RestControllerAdvice
public class GlobalHandlerException {

    private static final Logger log = LoggerFactory.getLogger(GlobalHandlerException.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("Erro não tratado ao processar {} {}", request.getMethod(), request.getRequestURI(), e);

        ErrorResponse response = ErrorResponse.builder()
                .message("Ocorreu um erro interno no servidor.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        List<String> erros = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .toList();

        ErrorResponse response = ErrorResponse.builder()
                .message("Erro de validação: " + String.join(" | ", erros))
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}