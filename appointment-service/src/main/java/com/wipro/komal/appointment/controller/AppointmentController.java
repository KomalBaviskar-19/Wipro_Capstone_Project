package com.wipro.komal.appointment.controller;
import com.wipro.komal.appointment.entity.Appointment;
import com.wipro.komal.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointments")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AppointmentController {
  private final AppointmentService service;

  @PostMapping("/add")
  public ResponseEntity<Appointment> add(@Valid @RequestBody Appointment appt)
  { 
	  return ResponseEntity.ok(service.create(appt)); 
  }
  @GetMapping("/list") 
  public Page<Appointment> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size)
  { 
	  return service.list(page,size); 
  }
  @GetMapping("/{id}")
  public Appointment get(@PathVariable Long id)
  {
	  return service.get(id); 
  }
  @PutMapping("/update/{id}") 
  public Appointment update(@PathVariable Long id,@Valid @RequestBody Appointment appt)
  {
	  return service.update(id, appt);
  }
  @DeleteMapping("/delete/{id}") 
  public Map<String,String> delete(@PathVariable Long id)
  { service.delete(id); 
  return Map.of("message","deleted"); 
  }

  @GetMapping("/{id}/details")
  public Map<String,Object> details(@PathVariable Long id)
  { 
	  return service.getDetails(id); 
  }
  @GetMapping("/doctor/{doctorId}/list") 
  public List<Appointment> byDoctor(@PathVariable Long doctorId)
  { 
	  return service.byDoctor(doctorId);
  }
  @GetMapping("/patient/{patientId}/list")
  public List<Appointment> byPatient(@PathVariable Long patientId)
  { 
	  return service.byPatient(patientId);
  }
}
