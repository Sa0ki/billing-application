package com.kinan.billservice.models;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Eren
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
