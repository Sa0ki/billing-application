package com.kinan.keycloakservice.services;

import com.kinan.keycloakservice.models.Customer;
import com.kinan.keycloakservice.models.ResponseMessage;
import com.kinan.keycloakservice.repositories.ICustomerRepository;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.K;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Eren
 **/
@org.springframework.stereotype.Service
public class Service {
    private final ICustomerRepository customerRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String urlLogin = "http://localhost:8080/realms/customer-realm/protocol/openid-connect/token";
    private final String realm = "customer-realm";
    private final String adminUsername = "admin";
    private final String adminPassword = "admin";
    public Service(ICustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public ResponseEntity<Object> getOrders(String customerId){
        return this.customerRepository.getOrders(customerId);
    }
    public ResponseEntity<Object> getOrder(String orderId){
        return this.customerRepository.getOrder(orderId);
    }
    public ResponseEntity<Object> placeOrder(String customerId, String productId, Integer quantity){
        return this.customerRepository.placeOrder(customerId, productId, quantity);
    }
    public ResponseEntity<Object> confirmOrder(String orderId){
        return this.customerRepository.confirmOrder(orderId);
    }
    public ResponseEntity<Object> getBill(String orderId, Customer customer){
        return this.getBill(orderId, customer);
    }
    public ResponseEntity<Object> downloadBill(String billId){
        return this.downloadBill(billId);
    }
    public ResponseEntity<Object> login(String username, String password){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", "customer-client");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        try {

            return this.restTemplate
                    .exchange(
                            this.urlLogin,
                            HttpMethod.POST,
                            requestEntity,
                            Object.class
                    );
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    public ResponseEntity<Object> register(Customer customer){
        ResponseEntity<Object> response = this.customerRepository.addCustomer(customer);
        if(response.getBody() == null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage("This email is already used."));
        try{
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080")
                    .realm(this.realm)
                    .clientId("customer-client")
                    .username(this.adminUsername)
                    .password(this.adminPassword)
                    .build();

            UserRepresentation user = new UserRepresentation();
            user.setUsername(customer.getLastName());
            user.setLastName(customer.getLastName());
            user.setFirstName(customer.getFirstName());
            user.setEmail(customer.getEmail());
            user.setEmailVerified(true);
            user.setEnabled(true);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(customer.getPassword());
            credential.setTemporary(false);

            user.setCredentials(List.of(credential));
            keycloak.realm(this.realm).users().create(user);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}
