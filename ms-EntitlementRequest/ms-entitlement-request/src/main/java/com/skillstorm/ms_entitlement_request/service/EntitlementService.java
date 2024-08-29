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
public class EntitlementService
{

    @Value("${url_iiq}")
    private String urlIiq;

    @Value("${username_iiq}")
    private String usernameIiq;

    @Value("${password_iiq}")
    private String passwordIiq;

    public List<Entitlement> getEntitlementsByApp(String app) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq,passwordIiq);

        HttpEntity<String>entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String>response = restTemplate.
                exchange("http://135.237.83.37:8080/identityiq/scim/v2/Entitlements?filter=application.displayName eq \"" + app + "\"",
                        HttpMethod.GET,
                        entity,
                        String.class);

        if (response.getStatusCode() == HttpStatus.OK)
        {
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

    public ResponseEntity<EntitlementRequest> createRequest(String ownerId, String requesterId, String entitlementId, String description)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<EntitlementRequest> response = restTemplate.exchange
                (urlIiq+"Request/createRequest/"+ownerId+"/"+requesterId+"/"+entitlementId+"/"+description,
                        HttpMethod.POST
                        ,entity,
                        EntitlementRequest.class);

        System.out.println(response.getBody());
        return response;
    }

    public ResponseEntity<EntitlementRequest[]> getRequestsByOwnerId(String ownerId) throws JsonProcessingException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EntitlementRequest[]> response = restTemplate.exchange(
                urlIiq+"Request/getPending/" + ownerId + "/false",
                HttpMethod.GET,
                entity,
                EntitlementRequest[].class
        );

        return response;
    }

    public ResponseEntity<EntitlementRequest[]> getRequestByManagerAndStatus(String ownerId, boolean status)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EntitlementRequest[]> response = restTemplate.exchange(
                urlIiq+"Request/getPending/" + ownerId + "/" + status,
                HttpMethod.GET,
                entity,
                        EntitlementRequest[].class
        );

        return response;
    }

    public ResponseEntity<String> deleteRequestById(int requestId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(urlIiq+"Request/deleteRequest/" + requestId,
                HttpMethod.DELETE,
                entity,
                String.class);

        return response;
    }


    public ResponseEntity<String> processRequest(String accountId, String entName, Boolean approved, String requestId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String JsonBody = new ObjectMapper().writeValueAsString(entName);
        HttpEntity<String> entity = new HttpEntity<>(JsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://4.156.40.62:9001/account/permission/" + accountId,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            // Check the response status and act accordingly
            if (response.getStatusCode() == HttpStatus.OK) {
                processRequestHelper(approved, requestId);
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("Account update could not be processed.");
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Log the exception or handle it if needed
            if (ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                // Proceed as if it was successful
                processRequestHelper(approved, requestId);
                return ResponseEntity.ok("Request processed.");
            } else {
                return ResponseEntity.status(ex.getStatusCode())
                        .body("Account update could not be processed.");
            }
        } catch (RestClientException ex) {
            // Handle other errors
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Service unavailable or other network error.");
        }
    }

    public void processRequestHelper(Boolean approved, String requestId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(usernameIiq, passwordIiq);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(urlIiq+"Request/processRequest/" + requestId + "/" + approved ,
                                HttpMethod.PUT,
                                entity,
                                String.class);
    }


}
