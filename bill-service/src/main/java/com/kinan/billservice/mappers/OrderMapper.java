package com.kinan.billservice.mappers;

import com.kinan.billservice.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Eren
 **/
@Component
public class OrderMapper {
    private final ModelMapper modelMapper;
    public OrderMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public Order convertToOrder(Object object){
        return this.modelMapper.map(object, Order.class);
    }
}
