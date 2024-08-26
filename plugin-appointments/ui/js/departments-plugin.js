function changeText() {
    document.getElementById("test").innerHTML = "Text has been changed.";
}

function clearFields() {
	document.getElementById("dept_name").value = "";
	document.getElementById("dept_desc").value = "";
}

/**
 * getAllDepartments()
 */
async function getAllDepartments() {
    const url = PluginHelper.getPluginRestUrl("DepartmentsPlugin/getAll");
    const headers = new Headers();
	headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

    const options = {
        method: "GET",
        headers: headers,
        redirect: "follow"
    }

    const departments = await fetch(url, options);
    const departmentsJSON = await departments.json();

    document.getElementById("departmentsList").innerHTML = "<tr><th>ID</th><th>Name</th><th>Description</th></tr>";
    for(let d of departmentsJSON) {
        let row = document.createElement("tr");
        row.innerHTML = '<td>' + d.id + '</td><td>' + d.name + '</td><td>' + d.description + '</td>'
            + '<td><button type="button" onclick="toggleUpdate(true, ' + d.id + ')">Edit</button></td>'
            + '<td><button type="button" onclick="deleteDepartmentById(' + d.id + ')">Delete</button></td>';
		document.getElementById('departmentsList').appendChild(row);
        console.log(row.innerHTML);
    }
}

/**
 * createDepartment()
 */
async function createDepartment() {
    const url = PluginHelper.getPluginRestUrl("DepartmentsPlugin/create");
    const headers = new Headers();
    headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
	headers.append("Content-Type", "application/json");

    let name = document.getElementById("dept_name").value;
    let description = document.getElementById("dept_desc").value;
    const body = JSON.stringify({
        "name": name,
        "description": description
    });

    const options = {
        method: "POST",
        headers: headers,
        body: body,
        redirect: "follow"
    }

    await fetch(url, options).then(data => console.log(data)).catch(err => console.log(err));

    await getAllDepartments();
    clearFields();
}

//  TODO test what happens when you try to update opnly one field
function toggleUpdate(updating, updatingId) {
    document.getElementById("idToUpdate").innerHTML = updatingId;
    console.log(0.1 + document.getElementById("idToUpdate").innerHTML);
    if(updating) {
        document.getElementById("createButton").style.visibility = "hidden";
        document.getElementById("updateButton").style.visibility = "visible";
        document.getElementById("updateButtonCancel").style.visibility = "visible";
        document.getElementById("currentOperationLbl").innerHTML = "Update Department (ID: " + document.getElementById("idToUpdate").innerHTML + ")";
        console.log(0.2 + "A");
    } else {
        document.getElementById("createButton").style.visibility = "visible";
        document.getElementById("updateButton").style.visibility = "hidden";
        document.getElementById("updateButtonCancel").style.visibility = "hidden";
        document.getElementById("currentOperationLbl").innerHTML = "Create New Department";
        console.log(0.2 + "B");
    }
    console.log(0.3);
}

/**
 * updateDepartment()
 */
//  TODO Implement this somehow, probably with a different page?
async function updateDepartment() {
    let id = document.getElementById("idToUpdate").innerHTML;

    const url = PluginHelper.getPluginRestUrl(`DepartmentsPlugin/updateBy/id/${id}`);
    const headers = new Headers();
    headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
	headers.append("Content-Type", "application/json");

    let name = document.getElementById("dept_name").value;
    let description = document.getElementById("dept_desc").value;
    const body = JSON.stringify({
        "name": name,
        "description": description
    });

    const options = {
        method: "PUT",
        headers: headers,
        body: body,
        redirect: "follow"
    }

    await fetch(url, options).then(data => console.log(data)).catch(err => console.log(err));

    await getAllDepartments();
    toggleUpdate(false, "");
    clearFields();
}

/**
 * deleteDepartmentById()
 * @param {*} id 
 */
async function deleteDepartmentById(id) {
    const url = PluginHelper.getPluginRestUrl(`DepartmentsPlugin/deleteBy/id/${id}`)
    const headers = new Headers();
    headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

    const options = {
        method: "DELETE",
        headers: headers,
        redirect: "follow"
    }

    await fetch(url, options).then(data => console.log(data)).catch(err => console.log(err));
    
    await getAllDepartments();
}

getAllDepartments();
toggleUpdate(false, "");