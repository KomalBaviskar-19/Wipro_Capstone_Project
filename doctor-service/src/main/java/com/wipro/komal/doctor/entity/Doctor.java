package com.wipro.komal.doctor.entity;

import com.wipro.komal.doctor.entity.enums.AvailabilityStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank(message="name required") private String name;
  @NotBlank(message="specialization required") private String specialization;
  private int experience;
  private String contactNumber;
  @Enumerated(EnumType.STRING) private AvailabilityStatus availabilityStatus;
}
