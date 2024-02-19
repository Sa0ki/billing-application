package com.kinan.keycloakservice.configurations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author Eren
 **/
public class TokenInterceptor implements ClientHttpRequestInterceptor {
    private final String token;
    public TokenInterceptor(final String token){
        this.token = token;
    }
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + this.token);
        return execution.execute(request, body);
    }
}
