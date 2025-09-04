package com.wipro.komal.doctor.controller;
import com.wipro.komal.doctor.entity.Doctor;
import com.wipro.komal.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DoctorController {
  private final DoctorService service;

  @PostMapping("/add") 
  public Doctor add(@Valid @RequestBody Doctor d)
  { 
	  return service.addDoctor(d); 
  }
  @GetMapping("/list") 
  public Page<Doctor> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size)
  {
	  return service.list(page,size); 
  }
  @GetMapping("/{id}")
  public Doctor get(@PathVariable Long id)
  {
	  return service.get(id);
  }
  @GetMapping("/availability/{status}")
  public List<Doctor> availability(@PathVariable String status)
  { 
	  return service.findByAvailability(status); 
  }
  @PutMapping("/update/{id}") 
  public Doctor update(@PathVariable Long id, @Valid @RequestBody Doctor d)
  { 
	  return service.update(id, d); 
  }
  @DeleteMapping("/delete/{id}") 
  public Map<String,String> delete(@PathVariable Long id)
  { 
	  service.delete(id); return Map.of("message","deleted"); 
  }
}
