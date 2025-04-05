package br.com.guilherme.ecommerce_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EcommerceManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceManagerApiApplication.class, args);
	}

}
