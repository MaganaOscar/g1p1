/**
 * 
 *
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

}*/
function login(event) {
	event.preventDefault();
	let form = document.getElementById('loginForm');
	let formData = new FormData(form);
	fetch("http://localhost:7474/login", {
		method: 'POST',
		body: formData
	})
	.then(response => {
		if(response.ok) {
			window.location.replace("http://localhost:5500/Main.html");
		}
		throw new Error("Something went wrong");
	})
	.catch(error => {
		console.log(error);
		document.getElementById('errors').innerHTML = "*Invalid Email/Password"
	});
}