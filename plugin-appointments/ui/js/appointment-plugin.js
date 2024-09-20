
/**
* getAllDepartments()
*/
let appointmentsJSON = [];

async function getAllAppointments() {
  const url = PluginHelper.getPluginRestUrl("AppointmentsPlugin/getAll");
  console.log(url)
  const headers = new Headers();
  headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  const options = {
      method: "GET",
      headers: headers,
      redirect: "follow"
  }

  const appointments = await fetch(url, options);
  appointmentsJSON = await appointments.json();

  console.log(appointments)
  console.log(appointmentsJSON)

  recreateAppointmentList(appointmentsJSON);

  return appointments;
}

/**
/**
 * handleAppointmentFormSubmit()
 */
function handleAppointmentFormSubmit(event) {
  event.preventDefault();

  // Get the form data
  const form = event.target;
  const formData = new FormData(form);

  // Convert form data to JSON
  const appointmentData = {};
  for (let [key, value] of formData.entries()) {
    appointmentData[key] = value;
  }

  // Send the appointment data to the server
  createAppointment(appointmentData);
}

/**
 * createAppointment()
 * @param {*} appointmentData 
 */
async function createAppointment(appointmentData) {
  const url = PluginHelper.getPluginRestUrl("AppointmentsPlugin/create");
  const headers = new Headers();
  headers.append("Content-Type", "application/json");
  headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  console.log(appointmentData);

  const options = {
    method: "POST",
    headers: headers,
    body: JSON.stringify(appointmentData),
    redirect: "follow"
    };

    console.log(options)

  const response = await fetch(url, options);
  const createdAppointment = await response.json();

  console.log(createdAppointment);

  // Reset the form
  const form = document.getElementById("appointmentForm");
  form.reset();

  // Refresh the appointment list
  await getAllAppointments();
}



// Attach the event listener to the form submit event
const appointmentForm = document.getElementById("appointmentForm");
appointmentForm.addEventListener("submit", handleAppointmentFormSubmit);
function recreateAppointmentList(appointmentsJSON) {
  document.getElementById("appointmentList").innerHTML = "<tr><th>Appointment ID</th><th>Title</th><th>Description</th><th>Organizer</th><th>Attendee</th><th>Datetime</th><th>Checked-In</th></tr>";
  for(let a of appointmentsJSON) {
      let row = document.createElement("tr");
      row.innerHTML = '<td style="border: 1px solid #ddd; padding: 8px;">' + a.id + '</td><td style="border: 1px solid #ddd; padding: 8px;">' + a.title + '</td><td style="border: 1px solid #ddd; padding: 8px;">' + a.description + '</td><td style="border: 1px solid #ddd; padding: 8px;"><a target="_blank" href="http://135.237.83.37:8080/identityiq/define/identity/identity.jsf?id=' + a.organizerId + '">' + a.organizerId + '</a></td><td style="border: 1px solid #ddd; padding: 8px;"><a target="_blank" href="http://135.237.83.37:8080/identityiq/define/identity/identity.jsf?id=' + a.attendeeId +'">' + a.attendeeId + '</a></td><td style="border: 1px solid #ddd; padding: 8px;">' + a.datetime + '</td><td style="border: 1px solid #ddd; padding: 8px;">' + a.checkedIn + '</td>'
          + '<td style="border: 1px solid #ddd; padding: 8px;"><button type="button" onclick="deleteAppointmentById(' + a.id + ')">Delete</button></td>';
  document.getElementById('appointmentList').appendChild(row);
      console.log(row.innerHTML);
  }
}


/**
 * filterAppointments()
 */
function filterAppointments() {
  const categoryFilter = document.getElementById("categoryFilter");
  const selectedCategory = categoryFilter.value;

  // Perform sorting based on selected category
  if (selectedCategory === "category1") {
      // Sort by ID
      appointmentsJSON.sort((a, b) => a.id - b.id);
      recreateAppointmentList(appointmentsJSON);
  } else if (selectedCategory === "category2") {
      // Sort by Title
      appointmentsJSON.sort((a, b) => a.title.localeCompare(b.title));
      recreateAppointmentList(appointmentsJSON);
  } else if (selectedCategory === "category3") {
      // Sort by Datetime
      appointmentsJSON.sort((a, b) => new Date(a.datetime) - new Date(b.datetime));
      recreateAppointmentList(appointmentsJSON);
  }
}
/**
* deleteAppointmentById()
* @param {*} id 
*/
async function deleteAppointmentById(id) {
  const url = PluginHelper.getPluginRestUrl(`AppointmentsPlugin/deleteBy/${id}`)
  const headers = new Headers();
  headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  const options = {
      method: "DELETE",
      headers: headers,
      redirect: "follow"
  }

  await fetch(url, options).then(data => console.log(data)).catch(err => console.log(err));
  
  await getAllAppointments();
}

console.log("Init")
getAllAppointments();