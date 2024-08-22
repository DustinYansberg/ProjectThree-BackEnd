function changeText() {
    document.getElementById("test").innerHTML = "Text has been changed.";
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

    document.getElementById("departmentsList").innerHTML = "<tr><th>ID</th><th>Name</th></tr>";
    for(let d of departmentsJSON) {
        let row = document.createElement("tr");
        row.innerHTML = '<td>' + d.name + '</td><td>' + d.description + '</td>'
            // + '<td><button type="button" onclick="updateDepartmentById(' + d.id + ')">Edit</button></td>'
            + '<td><button type="button" onclick="deleteDepartmentById(' + d.id + ')">Delete</button></td>';
		document.getElementById('departmentsList').appendChild(row);
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
	document.getElementById("dept_name").value = "";
	document.getElementById("dept_desc").value = "";
}

//  TODO
async function updateDepartmentById(id) {

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