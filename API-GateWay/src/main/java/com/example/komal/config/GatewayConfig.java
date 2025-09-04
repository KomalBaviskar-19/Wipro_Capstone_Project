package com.example.komal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.komal.filter.JwtAuthenticationFilter;

import java.util.List;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                // Auth service route - publicly accessible
                .route("userauth-service-route", r -> r.path("/api/auth/**")
                        .uri("lb://USER-AUTHENTICATION-SERVICE"))

                // Doctor service - accessible by ADMIN, DOCTOR
                .route("doctor-service-route", r -> r.path("/api/v1/doctors/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            config.setRequiredRoles(List.of("ADMIN", "DOCTOR"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://DOCTOR-SERVICE"))

                // Patient service - accessible by ADMIN, DOCTOR
                .route("patient-service-route", r -> r.path("/api/v1/patients/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            config.setRequiredRoles(List.of("ADMIN", "DOCTOR"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://PATIENT-SERVICE"))

                // Appointment service - accessible by ADMIN, DOCTOR, PATIENT
                .route("appointment-service-route", r -> r.path("/api/v1/appointments/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            config.setRequiredRoles(List.of("ADMIN", "DOCTOR", "PATIENT"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://APPOINTMENT-SERVICE"))

                // Medical Records service - accessible by ADMIN, DOCTOR, PATIENT
                .route("medical-records-service-route", r -> r.path("/api/v1/records/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            config.setRequiredRoles(List.of("ADMIN", "DOCTOR", "PATIENT"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://MEDICAL-RECORDS-SERVICE"))

                // Payment service - accessible by ADMIN, PATIENT
                .route("payment-service-route", r -> r.path("/payments/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            config.setRequiredRoles(List.of("ADMIN", "PATIENT"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://PAYMENT-SERVICE"))

                // Email service - accessible by ADMIN only
                .route("email-service-route", r -> r.path("/api/v1/email/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            config.setRequiredRoles(List.of("ADMIN"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://EMAIL-SERVICE"))

                .build();
    }
}
