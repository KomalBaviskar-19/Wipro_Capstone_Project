package com.komal.kafka;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    private String userEmail;
    private double amount;
    private String status;  // SUCCESS or FAIL
    private String message;
}
