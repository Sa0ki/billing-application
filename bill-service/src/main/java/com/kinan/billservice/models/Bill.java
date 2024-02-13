package com.kinan.billservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eren
 **/
@Document(collection = "bills")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bill {
    @Id
    private String id;
    private LocalDate date = LocalDate.now();
    private String customerId;
    private String orderId;
    private byte[] pdf;
}
