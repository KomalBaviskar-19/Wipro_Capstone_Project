package com.wipro.komal.appointment.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

import com.wipro.komal.appointment.entity.enums.AppointmentStatus;

@Entity @Table(name="appointments") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotNull private Long doctorId;
  @NotNull private Long patientId;
  @NotNull private LocalDateTime scheduledAt;
  @Enumerated(EnumType.STRING) private AppointmentStatus status;
}
