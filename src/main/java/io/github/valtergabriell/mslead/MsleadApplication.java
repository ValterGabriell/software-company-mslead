package io.github.valtergabriell.mslead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsleadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsleadApplication.class, args);
	}

}
