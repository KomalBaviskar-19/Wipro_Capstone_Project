package com.wipro.komal.billing.clients;


import com.wipro.komal.billing.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="appointment-service", path="/api/v1/appointments")
public interface AppointmentClient {
    @GetMapping("/{id}")
    AppointmentDTO getAppointment(@PathVariable("id") Long id);
}
