package com.wipro.komal.billing.dto;
import lombok.Data;

@Data
public class AppointmentDTO {
    private Long id;
    private String appointmentDate;
    private String appointmentStatus;
}
