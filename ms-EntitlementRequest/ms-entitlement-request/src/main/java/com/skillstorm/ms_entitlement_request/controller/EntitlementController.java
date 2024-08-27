package com.skillstorm.ms_entitlement_request.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skillstorm.ms_entitlement_request.model.Entitlement;
import com.skillstorm.ms_entitlement_request.model.EntitlementRequest;
import com.skillstorm.ms_entitlement_request.service.EntitlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entitlements")
public class EntitlementController
{
    @Autowired
    private EntitlementService entitlementService;

    @GetMapping("/getByApp/{appName}")
    public ResponseEntity<List<Entitlement>> getEntitlementByApp(@PathVariable String appName) throws JsonProcessingException {
        List<Entitlement> ent = entitlementService.getEntitlementsByApp(appName);
        return ResponseEntity.ok(ent);
    }

    @PostMapping("/create/{ownerId}/{requesterId}/{entitlementId}/{description}")
    public ResponseEntity<EntitlementRequest> createRequest(
            @PathVariable String ownerId,
            @PathVariable String requesterId,
            @PathVariable String entitlementId,
            @PathVariable String description)
    {
        return entitlementService.createRequest(ownerId, requesterId, entitlementId, description);
    }

    @GetMapping("/getByOwner/{ownerId}")
    public ResponseEntity<EntitlementRequest[]> getRequestsByOwnerId(@PathVariable String ownerId) throws JsonProcessingException
    {
        return entitlementService.getRequestsByOwnerId(ownerId);
    }

    @GetMapping("/getByManagerAndStatus/{ownerId}/{status}")
    public ResponseEntity<EntitlementRequest[]> getRequestByManagerAndStatus(@PathVariable String ownerId, @PathVariable boolean status) throws JsonProcessingException
    {
        return entitlementService.getRequestByManagerAndStatus(ownerId, status);
    }

    @DeleteMapping("/deleteRequest/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable int requestId) throws JsonProcessingException
    {
        return entitlementService.deleteRequestById(requestId);
    }

    @PutMapping("/processRequest/{accountId}/{entName}/{approved}/{requestId}")
    public ResponseEntity<String> processRequest(@PathVariable String accountId, @PathVariable String entName, @PathVariable Boolean approved, @PathVariable String requestId) throws JsonProcessingException
    {
        return entitlementService.processRequest(accountId, entName, approved,requestId);
    }

    @DeleteMapping("/deleteRequest{requestId}")
    public ResponseEntity<String> processRequest(@PathVariable int requestId) throws JsonProcessingException
    {
        return entitlementService.deleteRequestById(requestId);
    }



}
