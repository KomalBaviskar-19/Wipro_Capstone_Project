package com.wipro.komal.billing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wipro.komal.billing.entity.enums.BillingStatus;

@Entity
@Table(name="billing_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private Long appointmentId;

    @NotNull
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BillingStatus status; // PENDING/PAID/FAILED

    private LocalDateTime issuedOn;
}
