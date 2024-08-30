//TODO CREATE
async function createTask()
{
    const url = PluginHelper.getPluginRestUrl('AutomatedTasks/add');

    const newHeaders = new Headers();
    newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
    newHeaders.append("Content-Type", "application/json");

    let task = document.getElementById('task').value;

    const body = JSON.stringify({ "taskName" : task });

    const options =
    {
          method: "POST"
        , headers: newHeaders
        , body: body
        , redirect: "follow"
    };

    await fetch(url, options)
        .then(data => console.log(data))
        .catch(err => console.log(err));

    await getAllTasks();

    document.getElementById('task').value = '';
    
}

//TODO GET
async function getAllTasks()
{
    const url = PluginHelper.getPluginRestUrl('AutomatedTasks/all');

    const newHeaders = new Headers();
    newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

    const options = 
    {
          method: "GET"
        , headers: newHeaders
        , redirect: "follow"
    };

    const response = await fetch(url, options);

    const responseJSON = await response.json();

    document.getElementById('table-body').innerHTML = '';

    for(let task of responseJSON)
    {
        let row = document.createElement('tr');
        row.innerHTML =
          '<td>' + task.id + '</td><td>' + task.taskName
        + '</td><td>' + '<td><button type ="button" onclick="deleteTask('
        + task.id + ')">X</button></td>';
        document.getElementById('table-body').appendChild(row);
    }
}

//TODO DELETE
async function deleteTask(id)
{
    const url = PluginHelper.getPluginRestUrl('AutomatedTasks/delete/' + id);

    const newHeaders = new Headers();
    newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

    const options = 
    {
          method: "DELETE"
        , headers: newHeaders
        , redirect: "follow"
    };

    await fetch(url, options);

    await getAllTasks();
}


//this will run to display everything in our db
getAllTasks();