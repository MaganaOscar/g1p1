function checkAuthentication() {
    if (sessionStorage.getItem("authenticated") != null) {
        location.replace("dashboard.html");
    } else {
        return false;
    }
}



window.onload = function () 
{
    // monitor form submission
    document.getElementById("loginForm").addEventListener("submit", handleSubmit);

    
    async function handleSubmit(event) {
        
        // prevent form submission
        event.preventDefault();
        console.log();

        const formData =  document.getElementById("loginForm");

        // validating credentials through http request
        try {
            let response = await fetch('http://localhost:7474/login', {
                method: 'POST',
                body: new FormData(this)
            });

            if (!response.ok){
                let msg ="You entered the wrong email or password. Please try again!";
                document.querySelector(".alert-warning").hidden = false;
                document.querySelector(".alert").innerText = msg;
                throw new Error (msg);
            }
            console.log(response);
            let result = await response.json();

            // Store Validated credentials in session storage
            sessionStorage.setItem("authenticated", JSON.stringify(result));

            // redirect to dashboard page after validating credentials
            window.location.replace("dashboard.html");

        } catch (err) {
            console.error(err.message);
        }
    }

  
}