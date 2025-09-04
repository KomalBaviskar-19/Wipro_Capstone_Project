package com.wipro.komal.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wipro.komal.billing.clients")

public class BillingPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingPaymentServiceApplication.class, args);
	}

}
