package com.papeleria.pedidos.exception;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException ex){ return build(HttpStatus.NOT_FOUND,ex.getMessage()); }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String,Object>> badState(IllegalStateException ex){ return build(HttpStatus.BAD_REQUEST,ex.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){
        Map<String,String> errs=new LinkedHashMap<>();
        for(FieldError fe:ex.getBindingResult().getFieldErrors()) errs.put(fe.getField(),fe.getDefaultMessage());
        return ResponseEntity.badRequest().body(Map.of("timestamp",LocalDateTime.now(),"status",400,"errores",errs));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> general(Exception ex){ return build(HttpStatus.INTERNAL_SERVER_ERROR,"Error interno"); }
    private ResponseEntity<Map<String,Object>> build(HttpStatus s,String m){
        return ResponseEntity.status(s).body(Map.of("timestamp",LocalDateTime.now(),"status",s.value(),"mensaje",m));
    }
}
