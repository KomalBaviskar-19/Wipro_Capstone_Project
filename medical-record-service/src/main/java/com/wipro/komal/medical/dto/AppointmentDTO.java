package com.wipro.komal.medical.dto;

import lombok.Data;

@Data
public class AppointmentDTO {
    private Long id;
    private String appointmentDate;
    private String appointmentStatus;
}
