package com.skillstorm.policies;

import java.util.LinkedList;
import java.util.List;
import java.util.Date;

import com.skillstorm.models.Document;
import com.skillstorm.services.DocumentService;

import sailpoint.api.SailPointContext;
import sailpoint.object.Identity;
import sailpoint.object.Policy;
import sailpoint.object.PolicyViolation;
import sailpoint.policy.BasePluginPolicyExecutor;
import sailpoint.tools.GeneralException;

public class DocumentPolicyExecutor extends BasePluginPolicyExecutor {

    @Override
    public String getPluginName() {
        return "ComplianceTrainingPlugin";
    }
    
    @Override
    public List<PolicyViolation> evaluate(SailPointContext context, Policy policy, Identity identity) throws GeneralException {
        
        List<PolicyViolation> violations = new LinkedList<>();
        
          int threshold = getSettingInt("overdueThreshold");
        		 
          DocumentService service = new DocumentService(this);
          List<Document> documents = service.getDocumentsByIdentity(identity.getId()); //fix
        
        for (Document document : documents) {
            
            Date assignedDate = document.getAssignedDateTime(); 
            boolean isCompleted = document.isCompleted();
            
            if (!isCompleted) {
                
                long elapsedTime = new Date().getTime() - assignedDate.getTime();
                long elapsedDays = elapsedTime / (1000 * 60 * 60 * 24); // equals 1 day
                
                if (elapsedDays > threshold) {
                    
                    violations.add(createViolation(context, policy, identity, document, elapsedDays));
                }
            }
        }
        
        return violations;
    }
    
    public PolicyViolation createViolation(SailPointContext context, Policy policy, Identity identity, Document document, long elapsedDays) throws GeneralException {
        PolicyViolation violation = new PolicyViolation();
        
        violation.setStatus(PolicyViolation.Status.Open);
        violation.setIdentity(identity);
        violation.setPolicy(policy);
        violation.setAlertable(true);
        violation.setOwner(context.getObjectByName(Identity.class, "spadmin"));
        violation.setConstraintName("Document overdue for more than " + elapsedDays + " days.");
        violation.setArgument("Document Name", document.getName());
        violation.setArgument("Assigned Date", document.getAssignedDateTime().toString());
        violation.setArgument("Elapsed Days", elapsedDays);
        
        return formatViolation(context, identity, policy, null, violation);
    }

}

