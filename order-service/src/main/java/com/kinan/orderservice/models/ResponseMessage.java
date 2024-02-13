package com.kinan.orderservice.models;

import lombok.*;

/**
 * @author Eren
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage {
    private String message;
}
