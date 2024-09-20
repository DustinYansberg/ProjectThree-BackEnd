
/**
* getAllDepartments()
*/
async function getAllAppointments() {
  const url = PluginHelper.getPluginRestUrl("AppointmentsPlugin/getAll");
  const headers = new Headers();
  headers.append("X-XSRF-TOKEN", PluginHelper.getCsrfToken());

  const options = {
      method: "GET",
      headers: headers,
      redirect: "follow"
  }

  const appointments = await fetch(url, options);
  const appointmentsJSON = await appointments.json();

  console.log(appointments)
  console.log(appointmentsJSON)

  recreateAppointmentList(appointmentsJSON);

  return appointments;
}

function recreateAppointmentList(appointmentsJSON) {
  document.getElementById("appointmentList").innerHTML = "<tr><th>Appointment ID</th><th>Title</th><th>Description</th><th>Organizer</th><th>Attendee</th><th>Datetime</th><th>Checked-In</th></tr>";
  for(let a of appointmentsJSON) {
      let row = document.createElement("tr");
      row.innerHTML = '<td>' + a.id + '</td><td>' + a.title + '</td><td>' + a.description + '</td><td>' + a.organizerId + '</td><td>' + a.attendeeId + '</td><td>' + a.checkedIn + '</td>'
          + '<td><button type="button" onclick="deleteAppointmentById(' + a.id + ')">Delete</button></td>';
  document.getElementById('appointmentList').appendChild(row);
      console.log(row.innerHTML);
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

getAllAppointments();