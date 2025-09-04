package com.wipro.komal.billing.clients;
import com.wipro.komal.billing.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="patient-service", path="/api/v1/patients")
public interface PatientClient {
    @GetMapping("/{id}")
    PatientDTO getPatient(@PathVariable("id") Long id);
}
