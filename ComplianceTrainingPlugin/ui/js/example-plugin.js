function changeParagraph() {
	console.log(document.getElementById("myParagraph").innerText);
	document.getElementById("myParagraph").innerText = "Well, whaddaya know...it worked!";
	console.log(document.getElementById("myParagraph").innerText);
}

async function createOffice() {
	
	const url = PluginHelper.getPluginRestUrl('OfficePlugin/createOne');
	
	const newHeaders = new Headers();
	newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
	// we need this here, because our endpoint is expecting JSON in its @Consumes annotation
	newHeaders.append("Content-Type", "application/json");
	
	// set up body
	let department = document.getElementById('department').value;
	let address = document.getElementById('address').value;
	
	const body = JSON.stringify({ "department": department, "address": address });
	
	// set up options
	const options = {
		method: "POST",
		headers: newHeaders,
		body: body,
		redirect: "follow"
	}
	
	// handling responses to the request, just printing them out for now
	await fetch(url, options)
		.then(data => console.log(data))
		.catch(err => console.log(err));
		
	// refreshing our table
	await getAllOffices();
	
	document.getElementById('department').value = '';
	document.getElementById('address').value = '';
	
}

// this function must be async because we're going to be awaiting within it
// we're going to use the fetch API for these calls
// fetch calls take two parameters -- a URL and an options block
async function getAllOffices() {
	
	// setting up the URL
	// the plugin helper only needs the total suffix from your endpoint to find it
	const url = PluginHelper.getPluginRestUrl('OfficePlugin/getall');
	
	// setting up our headers
	// we must include the CSRF token to validate the request
	const newHeaders = new Headers();
	newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
	
	// setting up our options block
	const options = {
		method: "GET",
		headers: newHeaders,
		redirect: "follow"
	};
	
	// using fetch to actually make the call
	// this is async, so we must await before continuing
	const response = await fetch(url, options);
	
	// unpacking the JSON from the response
	// must await this, too, since it deals with a Promise
	const responseJSON = await response.json();
	
	// clearing the table body before getting all offices and appending them as children
	document.getElementById('table-body').innerHTML = '';
	
	for (let office of responseJSON) {
		let row = document.createElement('tr');
		row.innerHTML = '<td>' + office.id + '</td><td>' + office.department + '</td><td>' + office.address + '</td>'
					+ '<td><button type="button" onclick="deleteOffice(' + office.id + ')">X</button></td>';
		document.getElementById('table-body').appendChild(row);
	}
}

// a function for deleting an office when clicking on the button in the row
async function deleteOffice(id) {
	
	const url = PluginHelper.getPluginRestUrl('OfficePlugin/deleteById/' + id);
	
	const newHeaders = new Headers();
	newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
	
	const options = {
		method: "DELETE",
		headers: newHeaders,
		redirect: "follow"
	};
	
	await fetch(url, options);
	
	await getAllOffices();
}



// the code below will actually run when the page loads
// we call our getAllOffices function by default on load

getAllOffices();