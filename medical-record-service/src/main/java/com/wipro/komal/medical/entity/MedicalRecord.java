package com.wipro.komal.medical.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name="medical_records") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalRecord {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotNull private Long patientId;
  @NotNull private Long doctorId;
  @NotNull private Long appointmentId;
  private String diagnosis;
  private String prescription;
//  private LocalDateTime recordDate;
  private LocalDate recordDate;

}
