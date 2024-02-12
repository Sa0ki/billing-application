package com.kinan.customerservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    /*
    @Bean
    public CommandLineRunner commandLineRunner(ICustomerRepository customerRepository){
        return args -> {
            customerRepository.saveAll(List.of(
                    Customer.builder().firstName("Kinan").lastName("Saad").dateOfBirth(LocalDate.of(2000, 8, 2)).build(),
                    Customer.builder().firstName("Fadi").lastName("Kawtar").dateOfBirth(LocalDate.of(1997, 12, 14)).build(),
                    Customer.builder().firstName("Aydi").lastName("Fatine").dateOfBirth(LocalDate.of(2003, 4, 30)).build()
            ));
        };
    }
     */
}
