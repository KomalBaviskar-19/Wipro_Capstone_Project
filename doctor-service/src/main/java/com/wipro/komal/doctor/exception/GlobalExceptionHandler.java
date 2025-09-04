package com.wipro.komal.doctor.exception;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,String>> val(MethodArgumentNotValidException ex){
    var map=new HashMap<String,String>(); ex.getBindingResult().getFieldErrors().forEach(f->map.put(f.getField(), f.getDefaultMessage())); return ResponseEntity.badRequest().body(map);
  }
  @ExceptionHandler(DoctorNotFoundException.class)
  public ResponseEntity<Map<String,String>> nf(DoctorNotFoundException ex){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage())); }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,String>> other(Exception ex){ return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage())); }
}
