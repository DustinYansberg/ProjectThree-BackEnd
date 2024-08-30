package com.skillstorm.rest;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.models.Document;
import com.skillstorm.services.DocumentService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("ComplianceTrainingPlugin")
public class DocumentResource extends BasePluginResource {
	
    @Override
    public String getPluginName() {
        return "ComplianceTrainingPlugin";
    }
    
    private DocumentService service() {
        return new DocumentService(this);
    }
    
    @GET                                   
    @Path("getDocuments/{identityId}")                        
    @AllowAll                              
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Document> getDocumentsByIdentity(@PathParam("identityId") String identityId) throws GeneralException {
        return service().getDocumentsByIdentity(identityId);
    }
    
    @POST
    @Path("createDoc")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Document createDocument(Map<String, Object> body) throws GeneralException {
        String identityId = (String) body.get("identityId");
        String name = (String) body.get("name");
        String fileData = (String) body.get("fileData");
//        String fileDataString = (String) body.get("fileData"); 
//        byte[] fileData = Base64.getDecoder().decode(fileDataString); 
        
        return service().createDocument(identityId, name, fileData);
    }
    
    @PUT
    @Path("complete/{documentId}")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void completeDocument(@PathParam("documentId") int id) throws GeneralException {
        service().completeDocument(id);
    }

}

