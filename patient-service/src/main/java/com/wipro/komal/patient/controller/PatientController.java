package com.wipro.komal.patient.controller;

import com.wipro.komal.patient.entity.Patient;
import com.wipro.komal.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService service;

  @PostMapping("/add")
  public ResponseEntity<Patient> add(@Valid @RequestBody Patient p){
    return ResponseEntity.ok(service.addPatient(p));
  }

  @GetMapping("/list")
  public Page<Patient> list(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size){
    return service.list(page, size);
  }

  @GetMapping("/{id}")
  public Patient get(@PathVariable Long id){ return service.get(id); }

  @PutMapping("/update/{id}")
  public Patient update(@PathVariable Long id, @Valid @RequestBody Patient p){ return service.update(id, p); }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
    service.delete(id);
    return ResponseEntity.ok(Map.of("message", "Patient " + id + " deleted"));
  }
}
