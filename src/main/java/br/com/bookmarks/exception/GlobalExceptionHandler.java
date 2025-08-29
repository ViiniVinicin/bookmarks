package br.com.bookmarks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Recurso não Encontrado");
        body.put("message", ex.getMessage()); // <-- AQUI ESTÁ SUA MENSAGEM!
        body.put("path", "/bookmarks/" + ex.getMessage().replaceAll("[^0-9]", "")); // Bônus: extraindo o ID da msg

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}