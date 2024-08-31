package com.skillstorm.model;


public class EntitlementRequest  {

    private String requestId;
    private String ownerId;
    private String requesterId;
    private String entitlementId;
    private Boolean processed;
    private Boolean approved;
    private String description;

    public EntitlementRequest() {}

    public EntitlementRequest(String requestId, String ownerId, String requesterId,String entitlementId, Boolean processed, Boolean approved,String description)
    {
        this.requestId = requestId;
        this.ownerId = ownerId;
        this.requesterId = requesterId;
        this.entitlementId = entitlementId;
        this.processed = processed;
        this.approved = approved;
        this.description = description;
    }

    public String getEntitlementId() {
        return entitlementId;
    }

    public void setEntitlementId(String entitlementId) {
        this.entitlementId = entitlementId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EntitlementRequest{" +
                "requestId=" + requestId +
                ", ownerId='" + ownerId + '\'' +
                ", requesterId='" + requesterId + '\'' +
                ", processed=" + processed +
                ", approved=" + approved +
                ", description='" + description + '\'' +
                '}';
    }
}
