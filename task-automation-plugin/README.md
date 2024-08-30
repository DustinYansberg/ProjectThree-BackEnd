# TASK AUTOMATION PLUGIN v1.0
The user will be able to put in a task name that the plugin will execute every 15 minutes (default)
- Tasks will be executed one at a time, cycling through the database
    * Ideally, the db will be cycled through every hour - So we want to include calculations to make sure this can be accomplished
- If possible, implement customization to allow task execution customization


The user will be able to press a button on any task to delete it from the database

If the task can't be executed by the plugin, exception handling will be in place, and the plugin will automatically delete that record from the database



The user will be able to press a button on any task to execute it right now


# TASK AUTOMATION PLUGIN v1.0 2

Include a column in the database that marks something as having just been refreshed
- When pulled from DB, change it to 0 to indicate it has been called
- Order the "Get All" call to order by this value in ASC order
- Add 1 to everything in that column to increment the counter after setting the one pulled to 0

# v1.1
If feasible, store a message in the database to display after a task is executed from the task notification itself