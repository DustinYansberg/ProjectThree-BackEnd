//! Identities

async function getAllIdentities() {
  const url = PluginHelper.getPluginRestUrl("ep/identity/getAll");

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  const options = {
    method: "GET",
    headers: newHeaders,
  };
  const response = await fetch(url, options);
  await response.json().then((data) => {
    for (const identity of data) {
      let identityDropdown = document.getElementById("identity-name-dropdown");
      let option = document.createElement("option");
      option.text = identity.displayName;
      option.id = identity.id;
      identityDropdown.add(option);
    }
  });
}

async function getIdentityById(id) {
  const url = PluginHelper.getPluginRestUrl("ep/identity/get/" + id);

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
  newHeaders.append("Content-Type", "application/json");

  const options = {
    method: "GET",
    headers: newHeaders,
  };

  const response = await fetch(url, options);
  const data = await response.json();
  return data;
}

//! Emails

async function selectEmailTemplate(element) {
  const id = element.id;
  let email;
  getEmailById(id).then((data) => {
    email = data;
    document.getElementById("email-subject").value = email.subject;
    document.getElementById("email-template-textarea").value = email.body;
  });
}

async function getEmailById(id) {
  const url = PluginHelper.getPluginRestUrl("ep/email/get/" + id);

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
  newHeaders.append("Content-Type", "application/json");

  const options = {
    method: "GET",
    headers: newHeaders,
  };

  const response = await fetch(url, options);
  const data = await response.json();
  return data;
}

async function saveTemplate() {
  event.preventDefault();
  const url = PluginHelper.getPluginRestUrl("ep/email/saveTemplate");

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
  newHeaders.append("Content-Type", "application/json");

  const name = document.getElementById("email-name").value;
  const subject = document.getElementById("email-subject").value;
  const emailBody = document.getElementById("email-template-textarea").value;

  const body = JSON.stringify({
    "name": name,
    "subject": subject,
    "body": emailBody,
  });

  const options = {
    method: "POST",
    headers: newHeaders,
    body: body,
    redirect: "follow",
  };

  await fetch(url, options)
    .then((data) => console.log(data))
    .catch((err) => console.log(err));

  document.getElementById("email-name").value = "";
  document.getElementById("email-subject").value = "";
  document.getElementById("email-template-textarea").value = "";
}

async function getAllEmailTemplates() {
  const url = PluginHelper.getPluginRestUrl("ep/email/getAll");

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  const options = {
    method: "GET",
    headers: newHeaders,
  };

  const response = await fetch(url, options);

  const responseJSON = await response.json();

  for (const emailTemplate of responseJSON) {
    let templateDropdown = document.getElementById("template-name-dropdown");
    let option = document.createElement("option");
    option.text = emailTemplate.name;
    option.id = emailTemplate.id;
    option.onclick = () => selectEmailTemplate(option);
    templateDropdown.add(option);
  }
}

async function sendEmail() {
  event.preventDefault();
  const url = PluginHelper.getPluginRestUrl("ep/email/send");

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());
  newHeaders.append("Content-Type", "application/json");

  const emailId = document.getElementById("template-name-dropdown").options[
    document.getElementById("template-name-dropdown").selectedIndex
  ].id;

  const receiverId = document
    .getElementById("identity-name-dropdown")
    .options[
      document.getElementById("identity-name-dropdown").selectedIndex
    ].id.toString();

  const body = JSON.stringify({
    "emailId": emailId,
    "receiverId": receiverId,
  });

  const options = {
    method: "POST",
    headers: newHeaders,
    body: body,
    redirect: "follow",
  };

  await fetch(url, options)
    .then((data) => console.log(data))
    .catch((err) => console.log(err));

  document.getElementById("email-template-textarea").value = "";
}

//! Logs

async function getAllLogs() {
  document.getElementById(
    "table"
  ).innerHTML += `<tbody id="table-body"></tbody>`;
  const url = PluginHelper.getPluginRestUrl("ep/logs/getAll");

  const newHeaders = new Headers();
  newHeaders.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  const options = {
    method: "GET",
    headers: newHeaders,
  };

  const response = await fetch(url, options);
  await response.json().then((data) => {
    let tableBody = document.getElementById("table-body");

    for (const logEntry of data) {
      console.log(logEntry);
      let receiver;
      let email;
      let date = new Date(logEntry.createdAtDate).toLocaleDateString();
      let time = logEntry.createdAtTime;
      getIdentityById(logEntry.receiverId).then((data) => {
        receiver = data;
        getEmailById(logEntry.emailId).then((data) => {
          email = data;
          tableBody.innerHTML += `
									<td>${receiver.displayName}</td>
									<td>${email.name}</td>
									<td>${date}, ${time}</td>
								`;
        });
      });
    }
  });
}

//! Tab Functions

function openTemplateTab() {
  let comp = document.getElementById("comp");
  comp.innerHTML = `
	<div id="email-composition">
	  <h4>Choose Template</h4>
	  <label for="template-name-dropdown">Template Name:</label>
	  <select id="template-name-dropdown" name="template-name-dropdown"></select>
  
	  <h4>Choose Recipient</h4>
	  <label for="identity-name-dropdown">Recipient Name:</label>
	  <select id="identity-name-dropdown"></select>
  
	  <h4>Compose Email</h4>
	  <form class="email-form">
		<div>
		  <label for="email-subject">Subject:</label>
		  <input type="text" id="email-subject" name="email-subject" />
		</div>
  
		<div>
		  <label for="email-template-textarea">Message:</label>
		  <textarea id="email-template-textarea" name="email-template-textarea" rows="10" cols="50"></textarea>
		</div>
  
		<button type="submit" onclick="sendEmail()">Send</button>
	  </form>
	</div>
  `;

  getAllEmailTemplates();
  getAllIdentities();
}

function openCreateTab() {
  let comp = document.getElementById("comp");
  comp.innerHTML = `
		   <div id="email-composition">
			<h4>Create a new Template</h4>
			<form class="email-form">
			  <div>
				<label for="email-name">Template Name:</label>
				<input
				  type="text"
				  id="email-name"
				  name="email-name"
				/>
			  </div>
  
			  <div>
				<label for="email-subject">Email Subject:</label>
				<input
				  type="text"
				  id="email-subject"
				  name="email-subject"
				/>
			  </div>
			  
			  <div>
				<label for="email-template-textarea">Message:</label>
				<textarea
				  id="email-template-textarea"
				  name="email-template-textarea"
				  rows="10"
				  cols="50"
				></textarea>
			  </div>
  
			  <button
				type="submit"
				id="email-submit"
				onclick="saveTemplate()"
			  >
				Submit
			  </button> 
	`;
}

function openLogsTab() {
  let comp = document.getElementById("comp");
  comp.innerHTML = `
	  <h4>Logs</h4>
		  <div class="logs">
		  <table id="table">
			  <thead>
				  <tr>
					  <th style="width: 200px">Receiver</th>
					  <th style="width: 200px">Email Sent</th>
					  <th style="width: 200px">Time Sent</th>
				  </tr>
			  </thead>
		  </table>
		  </div>
	`;
  getAllLogs();
}

openTemplateTab();
