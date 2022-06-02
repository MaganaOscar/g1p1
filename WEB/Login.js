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
			return response.json();
		} else {
			throw new Error("Something went wrong");
		}
	})
	.then(response => {
		sessionStorage.setItem("user", JSON.stringify(response));
		if(response.job_code === 100) {
			window.location.replace("http://localhost:5500/EmployeePage.html");
		} else if(response.job_code === 200) {
			window.location.replace("http://localhost:5500/ManagerPage.html");
		} else {
			throw new Error("something went wrong");
		}
	})
	.catch(error => {
		console.log(error);
		document.getElementById('errors').innerHTML = "*Invalid Email/Password"
	});
}