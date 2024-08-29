This will contain my draft notes as I made this

# DRAFT 1 - v0.0

workflow
1. take in identities from the spt_identity db
2. perform a query for id and display_name
3. perform refresh task on that group of identities

For the SQL query, we run:
SELECT id, display_name
FROM spt_identity
LIMIT (quantity)
OFFSET (position)
;

We pass in a variable called position that is kept by the program to track our spot and a variable called quantity which is from the config - default 10

At the end of each query, we add quantity to position and then pick up from there

upon beginning the cycle of this plugin, we pull the total number of identities from spt_identity:

SELECT COUNT(id)
FROM spt_identity;

We will store this value in an additional static variable called totalRecords to let us know once we've gone through everything

Once position + quantity exceeds totalRecords, we reset position to 0, call the method that sets totalRecords again, and the process starts over

TIME TO FIGURE OUT
How do we call the refresh identity cube method? (check doc that jon sent me and also research)

# DRAFT 2 - v0.3

For my "Crawler" method:
USE identityiq;
UPDATE spt_identity
JOIN (
    SELECT id
    FROM spt_identity
    LIMIT (quantity)
    OFFSET (position)
) AS subquery
ON spt_identity.id = subquery.id
SET spt_identity.needs_refresh = 1
;

Then after this is executed, we run the "Refresh marked" task

I think I'll also want an API end point so external sources can call the "Refresh marked" task as well

The task will be defined in an XML file imported

API endpoint will take a POST request and a String containing the identity's ID. It will make a call to the database:
USE identityiq;
UPDATE spt_identity
SET needs_refresh = 1
WHERE id = ?;
And then it will call the Refresh marked task

After this successfully executes, 200 response (What to put in body?)

# DEPLOYMENT 1 - v1.0

Omitted API endpoint as it was not needed by the rest of the team

50 per 15 seconds by default