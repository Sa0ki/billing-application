package com.kinan.keycloakservice.configurations;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;

/**
 * @author Eren
 **/
public class TokenConverter {
    public static Map<String, String> decodeToken(String token){
        try{
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);

            String name = payloadMap.get("name").toString();
            String email = payloadMap.get("email").toString();

            return Map.of(
                    "email", email,
                    "name", name
            );

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
