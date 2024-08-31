package com.skillstorm.ms_entitlement_request.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.ms_entitlement_request.model.Entitlement;
import com.skillstorm.ms_entitlement_request.model.EntitlementRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntitlementService {

    @Value("${url_iiq}")
    private String urlIiq;

    @Value("${url_acc}")
    private String urlAcc;

    @Value("${username_iiq}")
    private String usernameIiq;

    @Value("${password_iiq}")
    private String passwordIiq;

    /**
     * Retrieves a list of entitlements filtered by the application name.
     *
     * @param app The name of the application to filter entitlements.
     * @return A list of simplified Entitlement objects.
     * @throws JsonProcessingException if there is an error processing the JSON response.
     */
    public List<Entitlement> getEntitlementsByApp(String app) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://135.237.83.37:8080/identityiq/scim/v2/Entitlements?filter=application.displayName eq \"" + app + "\"",
                HttpMethod.GET,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String resp = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(resp);
            JsonNode res = node.path("Resources");
            List<Entitlement> simplifiedEntitlements = new ArrayList<>();

            for (JsonNode entitlement : res) {
                Entitlement simplified = new Entitlement();
                simplified.setId(entitlement.path("id").asText());
                simplified.setDisplayableName(entitlement.path("displayableName").asText());
                simplified.setType(entitlement.path("type").asText());
                simplified.setApplicationDisplayName(entitlement.path("application").path("displayName").asText());

                simplifiedEntitlements.add(simplified);
            }

            return simplifiedEntitlements;
        }

        return null;
    }

    /**
     * Processes an entitlement request by updating the account with the specified entitlement.
     *
     * @param accountId The ID of the account to update.
     * @param entName   The name of the entitlement to grant.
     * @param approved  The approval status of the request.
     * @param requestId The ID of the request being processed.
     * @return A response indicating the success or failure of the update.
     * @throws JsonProcessingException if there is an error processing the JSON response.
     */
    public ResponseEntity<String> processRequest(String accountId, String entName, Boolean approved, String requestId) throws JsonProcessingException {
        // Set up HTTP headers and JSON body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = new ObjectMapper().writeValueAsString(entName);
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    urlAcc + accountId,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                processRequestHelper(approved, requestId);
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("Account update could not be processed.");
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                processRequestHelper(approved, requestId);
                return ResponseEntity.ok("Request processed.");
            } else {
                return ResponseEntity.status(ex.getStatusCode())
                        .body("Account update could not be processed.");
            }
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Service unavailable or other network error.");
        }
    }

    /**
     * Creates a new entitlement request.
     *
     * @param ownerId       The ID of the owner (typically a manager) responsible for approving the request.
     * @param requesterId   The ID of the requester (user who needs the entitlement).
     * @param entitlementId The ID of the entitlement being requested.
     * @param description   A description of the request.
     * @return The created EntitlementRequest object.
     */
    public ResponseEntity<EntitlementRequest> createRequest(String ownerId, String requesterId, String entitlementId, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<EntitlementRequest> response = restTemplate.exchange(
                urlIiq + "Request/createRequest/" + ownerId + "/" + requesterId + "/" + entitlementId + "/" + description,
                HttpMethod.POST,
                entity,
                EntitlementRequest.class);

        System.out.println(response.getBody());
        return response;
    }

    /**
     * Retrieves a list of pending entitlement requests by the owner's ID.
     *
     * @param ownerId The ID of the owner (typically a manager).
     * @return A list of EntitlementRequest objects.
     * @throws JsonProcessingException if there is an error processing the JSON response.
     */
    public ResponseEntity<EntitlementRequest[]> getRequestsByOwnerId(String ownerId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EntitlementRequest[]> response = restTemplate.exchange(
                urlIiq + "Request/getPending/" + ownerId + "/false",
                HttpMethod.GET,
                entity,
                EntitlementRequest[].class);

        return response;
    }

    /**
     * Retrieves entitlement requests by the owner's ID and approval status.
     *
     * @param ownerId The ID of the owner (typically a manager).
     * @param status  The approval status (true for approved, false for pending).
     * @return A list of EntitlementRequest objects matching the criteria.
     */
    public ResponseEntity<EntitlementRequest[]> getRequestByManagerAndStatus(String ownerId, boolean status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EntitlementRequest[]> response = restTemplate.exchange(
                urlIiq + "Request/getPending/" + ownerId + "/" + status,
                HttpMethod.GET,
                entity,
                EntitlementRequest[].class);

        return response;
    }

    /**
     * Deletes an entitlement request by its ID.
     *
     * @param requestId The ID of the request to be deleted.
     * @return A response indicating the success or failure of the deletion.
     * @throws JsonProcessingException if there is an error processing the JSON response.
     */
    public ResponseEntity<String> deleteRequestById(int requestId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                urlIiq + "Request/deleteRequest/" + requestId,
                HttpMethod.DELETE,
                entity,
                String.class);

        return response;
    }

    /**
     * Helper method to process an entitlement request in IdentityIQ.
     *
     * @param approved  The approval status of the request.
     * @param requestId The ID of the request being processed.
     */
    public void processRequestHelper(Boolean approved, String requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);


        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(urlIiq + "Request/processRequest/" + requestId + "/" + approved,
                HttpMethod.PUT,
                entity,
                String.class);
    }
}
