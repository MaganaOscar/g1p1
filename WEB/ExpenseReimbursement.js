/**
 * 
 */
 function validateLogin(){
	
	console.log("data printed on the console.....");
	
	if(document.getElementById("email").value == "" && document.getElementById("password").value== ""){
		alert("validation succeeded");
	}else{
		alert("validation failed");
	}
	
}