/**
 * 
 */
function validateLogin() {

	console.log("data printed on the console.....");

	const email = document.getElementById("email").value;
	const password = document.getElementById("password").value;

	fetch("http://localhost:7474/login", {
		method: 'post', body: JSON.stringify(email, password)
	}).then(response => response.json())
		.then(responseJson => {

			if (responseJson.email == email && responseJson.password == password) {
				alert("validation succeeded");
				window.location.replace("EmplyeesPage.html");
			} else {
				alert("validation failed");
				window.location.replace("ManagerPage.html");
			}

		})

}