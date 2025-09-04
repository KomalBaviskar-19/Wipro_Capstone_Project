package com.wipro.komal.appointment.clients;
import com.wipro.komal.appointment.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="doctor-service", path="/api/v1/doctors")
public interface DoctorClient {
  @GetMapping("/{id}")
  DoctorDTO getDoctor(@PathVariable("id") Long id);
}
