/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deim.urv.cat.homework2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.model.CustomerDTO;
import deim.urv.cat.homework2.model.ViewCredentials;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miriam
 */
public class CustomerServiceImpl implements CustomerService {
    private final WebTarget webTarget;
    private final jakarta.ws.rs.client.Client client;
    private static final String BASE_URI = "http://localhost:8080/Homework1/rest/api/v1";
    
    @Inject Logger log;
    
    public CustomerServiceImpl() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("customer");
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        try {
            // Solicita el "GET /customer"
            Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                // Convertir la respuesta JSON en una lista de CustomerDTO
                CustomerDTO[] customers = response.readEntity(CustomerDTO[].class);
                return Arrays.asList(customers);
            } else {
                throw new RuntimeException("Error fetching customers: " + response.getStatusInfo().toString());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error retrieving customers", e);
        }
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        try {
            // Solicita el "GET /customer/{id}"
             Response response = webTarget.path(String.valueOf(id))
                     .request(MediaType.APPLICATION_JSON)
                     .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                // Convertir la respuesta JSON en un objeto CustomerDTO
                return response.readEntity(CustomerDTO.class);
            } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new RuntimeException("Customer not found for ID: " + id);
            } else {
                throw new RuntimeException("Error fetching customer: " + response.getStatusInfo().toString());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error retrieving customer by ID", e);
        }
    }

    @Override
    public void updateCustomer(Long id, ViewCredentials credentials, Customer updatedData) {
        try {
            // Construir el encabezado Authorization para Basic Authentication
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            // Concatenar username:password
            String credencials = username + ":" + password;
            
            // Codificar las credenciales en Base64
            String encodedCredentials = Base64.getEncoder().encodeToString(credencials.getBytes(StandardCharsets.UTF_8));
            log.log(Level.WARNING, "Data encoded: {0}", encodedCredentials);
            
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(updatedData);
            log.log(Level.WARNING, "Sending Payload: {0}", jsonPayload);
            
            // Crear la solicitud con el encabezado Authorization
            Response response = webTarget.path(String.valueOf(id))
                                         .request(MediaType.APPLICATION_JSON)
                                         .header("Authorization", "Basic " + encodedCredentials) // Agregar el encabezado Authorization
                                         .put(Entity.entity(updatedData, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Customer updated successfully.");
            } else {
                String errorMessage = response.readEntity(String.class);

                // Log de error con el código de estado y el mensaje
                log.log(Level.SEVERE, "Error updating customer. Status code: {0}. Message: {1}", 
                        new Object[]{response.getStatus(), errorMessage});

                if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                    throw new RuntimeException("Unauthorized to update customer: " + errorMessage);
                } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
                    throw new RuntimeException("Bad request when updating customer: " + errorMessage);
                } else {
                    throw new RuntimeException(errorMessage);
                }
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    @Override
    public Customer authenticateCustomer(ViewCredentials credentials) {
        WebTarget targetWithAuth = client.target(BASE_URI).path("customer/auth");
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Concatenar username:password
        String credencials = username + ":" + password;

        // Codificar las credenciales en Base64
        String encodedCredentials = Base64.getEncoder().encodeToString(credencials.getBytes(StandardCharsets.UTF_8));
        // Construir la URL con los parámetros query de username y password
        Response response = targetWithAuth.request(MediaType.APPLICATION_JSON)
                                          .header("Authorization", "Basic " + encodedCredentials)
                                          .get();
        String jsonResponse = response.readEntity(String.class);
        log.log(Level.INFO, "Response JSON: {0}", jsonResponse);
        // Verificar si la respuesta es correcta
        if (response.getStatus() == 200) {
            // Si la respuesta es correcta, deserializar el objeto Credentials
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
            Customer customer = null;
            try {
                customer = objectMapper.readValue(jsonResponse, Customer.class);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return customer;
        }

        // Si no es correcto, devolver null
        return null; 
    }

    @Override
    public boolean registerCustomer(Customer customer) {
        try {
            log.log(Level.INFO, "Attempting to register customer: {0}", customer);

            Response response = webTarget
                                .request(MediaType.APPLICATION_JSON)
                                .post(Entity.entity(customer, MediaType.APPLICATION_JSON));

            // Comprovar que el resultado es correcto
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Customer registered successfully.");
                return true;
            } else {
                // Si no es correcto devolver el error
                String responseBody = response.readEntity(String.class);
                throw new RuntimeException(responseBody);
            }
        } catch (ProcessingException e) {
            // Manejar excepciones
            System.err.println("Network error while registering customer: " + e.getMessage());
            throw new RuntimeException("Network error occurred during customer registration.", e);
        }
    }
}
