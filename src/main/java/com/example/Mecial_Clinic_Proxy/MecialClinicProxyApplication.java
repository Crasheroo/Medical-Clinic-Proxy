package com.example.Mecial_Clinic_Proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MecialClinicProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MecialClinicProxyApplication.class, args);
	}

}
