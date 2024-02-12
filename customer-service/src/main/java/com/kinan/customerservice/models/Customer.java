package com.kinan.customerservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @author Eren
 **/
@Document(collection = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
