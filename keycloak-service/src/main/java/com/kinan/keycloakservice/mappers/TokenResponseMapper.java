package com.kinan.keycloakservice.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Eren
 **/
@Component
public class TokenResponseMapper {
    private final ModelMapper modelMapper;
    public TokenResponseMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public TokenResponse convertToTokenResponse(Object object){
        return this.modelMapper.map(object, TokenResponse.class);
    }
}
