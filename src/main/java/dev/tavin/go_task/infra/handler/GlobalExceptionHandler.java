package dev.tavin.go_task.infra.handler;

import dev.tavin.go_task.infra.dto.error.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> handleBadCredentials(BadCredentialsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Não Autorizado",
                "Email ou senha inválidos.",
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Requisição Inválida",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFound(UsernameNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Não Encontrado",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // 422

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Erro de Validação",
                String.join("; ", errors),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Erro Interno no Servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getRequestURI()
        );

        e.printStackTrace();

        return ResponseEntity.status(status).body(err);
    }
}