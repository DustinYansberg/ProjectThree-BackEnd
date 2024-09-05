package com.skillstorm.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skillstorm.models.Department;
import com.skillstorm.services.DepartmentService;

import sailpoint.api.SailPointContext;
import sailpoint.object.Attributes;
import sailpoint.object.TaskResult;
import sailpoint.object.TaskSchedule;
import sailpoint.task.BasePluginTaskExecutor;

public class RemoveDuplicatesTask extends BasePluginTaskExecutor{
	boolean success = true;
	
	private DepartmentService service() {
		return new DepartmentService(this);
	}

	/**
	 * execute()
	 * When this task is executed, it searches for and deletes departments that have the same name.
	 * TODO Also finds accounts that are connected with those duplicate departments
	 * 		and re-assign their departments to the originals
	 */
	@Override
	public void execute(SailPointContext arg0, TaskSchedule schedule, TaskResult result, Attributes<String, Object> inputs)
			throws Exception {
		System.out.println("STARTING TASK RemoveDuplicatesTask");
		
        Map<String, ArrayList<Integer>> deptNameOccurrences = new HashMap<>();
        List<Department> allDepts = service().getAllDepartments();
		
        System.out.println("All Departments: " + allDepts);
        
        //	Make a list of department names with all the IDs of the departments that share them
        for(Department dept : allDepts) {
        	if(!deptNameOccurrences.containsKey(dept.getName())) {
        		deptNameOccurrences.put(dept.getName(), new ArrayList<>());
        	}
    		deptNameOccurrences.get(dept.getName()).add(dept.getId());
        }
        
        System.out.println("Dept Occurrences: " + deptNameOccurrences);
        
        //	Update the first occurrences of duplicate departments
        //		by aggregating the descriptions of all the rest of the departments.
        //	Also delete the departments with the same name as it.
        ArrayList<Integer> removedIDs = new ArrayList<>();
        for(String currentName : deptNameOccurrences.keySet()) {
        	ArrayList<Integer> currentArr = deptNameOccurrences.get(currentName);
        	//	Get reference to original department
        	Department originalDept = service().getDepartmentByColumn("id", currentArr.get(0) + "");
        	
        	for(int i = 1; i < currentArr.size(); i++) {
        		int currentId = currentArr.get(i);
        		Department foundDept = service().getDepartmentByColumn("id", currentId + "");
        		System.out.println("FOUND:\t" + currentName + " | " + currentId + " | " + foundDept);
        		
        		//	Update original department with aggregated description
        		//	TODO Test this method of description aggregation
        		service().updateDepartmentByColumn("id", originalDept.getId() + "",
        				originalDept.getName(), originalDept.getDescription() + "\n" + service().getDepartmentByColumn("id", currentId + "").getDescription());
        		
        		System.out.println("UPDATED:\t" + currentName + " | " + currentId + " | " + foundDept);
        		
        		//	Delete extra department
        		service().deleteDepartmentByColumn("id", currentId + "");
        		removedIDs.add(currentId);
        		
        		System.out.println("DELETED:\t" + currentName + " | " + currentId);
        	}
        }
        
        success = removedIDs.size() == 0;
        result.put("duplicates", removedIDs.toString());
	}

	@Override
	public boolean terminate() {
		return success;
	}

	@Override
	public String getPluginName() {
		return "DepartmentsPlugin";
	}
    
}
