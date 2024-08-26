package com.skillstorm.tasks;

import com.skillstorm.services.LogEntryService;

import sailpoint.api.SailPointContext;
import sailpoint.object.Attributes;
import sailpoint.object.TaskResult;
import sailpoint.object.TaskSchedule;
import sailpoint.task.BasePluginTaskExecutor;
import sailpoint.tools.GeneralException;

public class DeleteOldLogEntriesExecutor extends BasePluginTaskExecutor {

    boolean success = true;

    private LogEntryService service() {
	return new LogEntryService(this);
    }

    @Override
    public void execute(SailPointContext context, TaskSchedule schedule, TaskResult result,
	    Attributes<String, Object> inputs) throws Exception {
//	int numToKeep = getSettingInt("numOfLogs");
	/*
	 * See how many log entries exists. if greater than a given amount, delete the
	 * oldest until the amount is less than the described limit
	 */

	int logCount = service().countLogs();

	if (logCount <= 3) {
	    success = false;
	    throw new GeneralException("3 or fewer logs in the database");
	} else {
	    int numberOfLogsDeleted = service().deleteOldLogs();
	    result.put("numberOfLogsDeleted", numberOfLogsDeleted);

	    success = true;
	}

    }

    @Override
    public boolean terminate() {

	return success;
    }

    @Override
    public String getPluginName() {

	return "EmailPlugin";
    }

}
