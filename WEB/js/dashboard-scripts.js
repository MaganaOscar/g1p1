class Authentication {

    // checks for authenticated user in sessionStorage
    static checkAuth() {
        
        console.log("checkAuth...");
        if (sessionStorage.getItem("authenticated") != null){
            console.log("auth dash-->")
            // after validating user 
            // get user info from Session
            return JSON.parse(sessionStorage.getItem("authenticated"));
        } else {
            // redirect to login  / index
            window.location.replace("index.html");
        }
        return null;
    }


    // Initialize log out process
    static logOut() {
        sessionStorage.clear();
        window.location.replace("index.html");
    }


}
class DashboardEvents {
    
    constructor(employee) {
        this.employee = employee;
        this.buildEmployeeInfoHTML(employee);
    }

    async getAllReimbursementRequest (emp_id) {
        
        try {
            console.log("getAllReimbursementRequest.....");
            let response = await fetch(`http://localhost:7474/reimbursements/emp/${emp_id}`);

            if(!response.ok) {
                throw new Error("Sorry! Currently experiencing network issues");
            }

            // handle data on client side
            let reimbursements = await response.json();
            sessionStorage.setItem("reimbursements", JSON.stringify(reimbursements));

            let empJobCode = JSON.parse(sessionStorage.getItem("authenticated")).job_code;

            this.buildRequestHTML(reimbursements, empJobCode);

        } catch (err) {
            console.error(err.message);
        }
         
    }


    // Filter through for Reimbursement results
    getRejectedReimbursements(job_code) {
        console.log("getRejectedReimbursements().....");
        console.log(job_code)
        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"))
                                .filter((item) => {
                                    if (item.rb_status == "rejected") {
                                        return item;
                                    }
                                });
        $('#reimbursements').html("");
        this.buildRequestHTML(reimbursements, job_code);
    }

    getResolvedReimbursements(job_code) {
        console.log("getResolvedReimbursements().....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"))
                                .filter((item) => {
                                    if (item.rb_status == "resolved") {
                                        return item;
                                    }
                                });
            $('#reimbursements').html("");          
            this.buildRequestHTML(reimbursements,  job_code);
    }


    getPendingReimbursements(job_code) {
        console.log("getPendingReimbursements() .....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"))
                                .filter((item) => {
                                    if (item.rb_status == "pending") {
                                        return item;
                                    }
                                });
        $('#reimbursements').html("");
        this.buildRequestHTML(reimbursements, job_code);
    }

    getAllReq(job_code) {
        console.log("getAllReq() .....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"));
        console.log(reimbursements)
        $('#reimbursements').html("");
        this.buildRequestHTML(reimbursements, job_code);
    }


    // Generate new Reimbursement request
    async requestReimbursement(emp_id) {
        console.log("requestReimbursement(emp_id)...");
    
        // get user's reimbursement amount 
        let amount = prompt("Please enter the amount to be Reimbursed", 22.50)

        while(parseFloat(amount) === NaN){
            amount = prompt("Please enter a correct number for your amount ")
        }
        const formData = new FormData();
        formData.append("emp_id", emp_id);
        formData.append("amount", amount);

        try {
            let response = await fetch('http://localhost:7474/reimbursements', {
            method: 'POST',
            body: formData,
            });
            let result = await response.text();
            console.log(result);

            if (!response.ok){

                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);

            } else {
                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);
            }
            
        } catch (error) {
            console.log(error.message);

        }


    }

    // construct Reimbursement list
    buildRequestHTML(reimbursements, job_code) {
        console.log("buildRequestHTM().....");

        reimbursements.map((item) => {
            let date = new Date(item.rb_timestamp);
            let dateFormat = (date.getDate() +
                        "/"+(date.getMonth()+1)+
                        "/"+date.getFullYear()+
                        " "+date.getHours()+
                        ":"+date.getMinutes()+
                        ":"+date.getSeconds());

            let status;

            if (item.rb_status === "pending") {
                status = "text-warning";
            } else if (item.rb_status === "resolved") {
                status = "text-success";
            } else if (item.rb_status === "rejected") {
                status = "text-danger";
            }
            
            let html = `
            <div class="mb-4">
                <h4 class="small font-weight-bold 
                d-flex align-items-center justify-content-between">
                    <span class="rb_id${item.rb_id}">ID: ${item.rb_id}</span>
                    <span class="status ${status}" id="status${item.rb_id}" >Status: ${item.rb_status}</span> 
                    <span class="amount${item.rb_id}"> $${item.rb_amount}</span>
                </h4>
                `;


            // displays for regular employees
            if (job_code == 100) {
                html +=`
                    <div class="d-flex align-items-center justify-content-between">
                        <h4 class="small font-weight-bold">Date: ${dateFormat}</h4>
                        <button class="btn btn-primary btn-sm" id="rb_${item.rb_id}" 
                        onclick="DashboardEvents.updateRequest(event)"
                        >Update</button>
                    </div>
                </div>
                <div class="dropdown-divider"></div>
                `;
            } else if (job_code == 200) {
                //display for managers
                html +=`
                    <div class="d-flex align-items-center justify-content-between">
                        <h4 class="small font-weight-bold">Date: ${dateFormat}</h4>
                        <button type="button" class="btn btn-danger btn-sm" 
                        onclick="DashboardEvents.updateRequestStatus(event)"
                        id="rejected_${item.rb_id}">Reject</button>
                        <button type="button" class="btn btn-success btn-sm" 
                        onclick="DashboardEvents.updateRequestStatus(event)"
                        id="resolved_${item.rb_id}">Resolve</button>
                    </div>
                </div>
                <div class="dropdown-divider"></div>
                `;
            }
                

            //update html body for Reimbursements
            document.getElementById("reimbursements").innerHTML += html;

        });

    } 


    static async updateRequest(event) {
        console.log("updateRequest()...");

        // get request ID
        const reqID = event.currentTarget.id.split("_")[1];
        // get req value
        const amount = $(`span.amount${reqID}`).val();

          // get user's reimbursement amount 
          let newAmount = prompt("Please enter the amount to be Reimbursed", amount)

          while(parseFloat(amount) === NaN){
              newAmount = prompt("Please enter a correct number for your amount ")
          }
          const formData = new FormData();
          formData.append("rb_id", reqID);
          formData.append("amount", newAmount);
  
          try {
              let response = await fetch('http://localhost:7474/reimbursements/details/update', {
              method: 'PUT',
              body: formData,
              });
              let result = await response.text();
              console.log(result);
  
              if (!response.ok){
  
                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);
  
              } else {

                // get old stored value
                let oldReq = JSON.parse(sessionStorage.getItem("reimbursements"));

                let newReq = oldReq.map((item) => {
                    if (item.rb_id == reqID) {
                        item.rb_amount = newAmount;
                        return item;
                    } else {
                        return item;
                    }
                });
                // store new values
                sessionStorage.setItem("reimbursements", JSON.stringify(newReq));

                // update html page
                $(`span.amount${reqID}`).text(`${newAmount}`);

                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);
     
              }
              
          } catch (error) {
              console.log(error.message);
  
          }
  


    }

    // construct employee Info Card
    buildEmployeeInfoHTML(emp) {   
        console.log(" buildEmployeeInfoHTML(emp).....");
        let htmlInfo = `
        <div class="d-flex align-items-center justify-content-between">
            <b>Employee ID</b>
            <b>${emp.emp_id}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Job Code</b>
            <b>${emp.job_code}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>First Name</b>
            <b>${emp.fname}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Last Name</b>
            <b>${emp.lname}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Email</b>
            <b>${emp.email}</b>
        </div>
        <button class="btn btn-primary float-right mt-4"
        data-toggle="modal" data-target="#employeeModal"
        ">Update</button>
        `;

        document.getElementById("employeeInfo").innerHTML = htmlInfo;

        // Nav display name
        document.getElementById("navName").innerHTML = `${emp.fname} ${emp.lname}`;


        // display search bar to search for an employee
        // only for manager
        if (emp.job_code == 200) {
            document.querySelector('.navbar-search div.input-group').hidden = false;
        }

        $('#EmpModal').html(
            `
            <div class="modal fade" id="employeeModal" tabindex="-1" role="dialog" aria-labelledby="employeeModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="requestModalLabel">Update Employee Info</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <form id="employeeInfoForm">
                    <div class="form-group">
                      <label for="employeeID" class="col-form-label">Employee ID</label>
                      <input type="number" class="form-control" id="employeeID" name="empID" value="${emp.emp_id}" disabled>
                    </div>
                    <div class="form-group">
                      <label for="jobcode" class="col-form-label">Job Code</label>
                      <input type="number" class="form-control" id="jobecode" name="jobcode" value="${emp.job_code}" disabled>
                    </div>
                    <div class="form-group">
                      <label for="fname" class="col-form-label">First Name</label>
                      <input type="text" class="form-control" id="fname" name="fname"value="${emp.fname}">
                    </div>
                    <div class="form-group">
                      <label for="lname" class="col-form-label">Last Name</label>
                      <input type="text" class="form-control" id="lname" name="lname" value="${emp.lname}">
                    </div>
                    <div class="form-group">
                      <label for="email" class="col-form-label">Email</label>
                      <input type="email" class="form-control" id="email" name="email" value="${emp.email}">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                  </form>
   
                </div>
               
              </div>
            </div>
          </div>
            
            
            `
        );

    }


    async updateEmployeeInfo(event) {
        console.log("updateEmployeeInfo....")
      
        //stop form submission to server
        event.preventDefault();
        
        const formData = new FormData();
        formData.append("empID", $("#employeeID").val());
        formData.append("fname", $("#fname").val());
        formData.append("lname", $("#lname").val());
        formData.append("email", $("#email").val());


        try {
            let response = await fetch('http://localhost:7474/employees/update', {
            method: 'PUT',
            body: formData,
            });

            if (!response.ok) {
                let result = await response.text();
                $('#employeeModal').css("display", "none");
                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);
                
                throw new Error(result);
            } else {
                let emp = await response.json();
                
                // update session object
                sessionStorage.setItem("authenticated", JSON.stringify(emp));

                // provide user feedback
                $('#employeeModal').css("display", "none");
                this.buildEmployeeInfoHTML(emp);
                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        Update Successful!
                    </div>`
                );

                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);
            }
        } catch (error) {
            console.error(error.message);
        }


    }

    // Methods for Manager --> Employee
    
    async getAllEmployeeReimbursements (){
        console.log("getAllEmployeeReimbursements () ....")

        try {
            console.log("getAllReimbursementRequest.....");
            let response = await fetch(`http://localhost:7474/reimbursements`);

            if(!response.ok) {
                let result = await response.text();
                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);

                throw new Error("Sorry! Currently experiencing network issues");
            }

            // handle data on client side
            let reimbursements = await response.json();
            sessionStorage.setItem("reimbursements", JSON.stringify(reimbursements));
            this.buildRequestHTML(reimbursements, 200);

        } catch (err) {
            console.error(err.message);
        }

    }

    static async updateRequestStatus(event) {
        console.log("updateRequestStatus()...");

        // get selected button details
        const req = {
            id: event.currentTarget.id.split("_")[1],
            status: event.currentTarget.id.split("_")[0]
        }
    

          const formData = new FormData();
          formData.append("rb_id", req.id);
          formData.append("rb_status", req.status);
  
          try {
              let response = await fetch('http://localhost:7474/reimbursements/status/update', {
              method: 'PUT',
              body: formData,
              });
              let result = await response.text();
              console.log(result);
  
              if (!response.ok){
  
                $('#flashMsg').html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                );
                $("#flashMsg .alert")                  
                .animate({
                  opacity: '0',
                  height: '0',
                }, 8000);
  
              } else {

                // get old stored session values
                let oldReq = JSON.parse(sessionStorage.getItem("reimbursements"));

                let newReq = oldReq.map((item) => {
                    if (item.rb_id == req.id) {
                        item.rb_status = req.status;
                        return item;
                    } else {
                        return item;
                    }
                });
                // store new values
                sessionStorage.setItem("reimbursements", JSON.stringify(newReq));

                    // update html page
                  $(`#reimbursements #status${req.id}`).text(`${req.status}`);
               
                  $('#flashMsg').html(                    
                      `<div class="alert alert-success" role="alert">
                          ${result}
                      </div>`
                  );
                  $("#flashMsg .alert")                  
                  .animate({
                    opacity: '0',
                    height: '0',
                  }, 8000);


              }
              
          } catch (error) {
              console.log(error.message);
  
          }
    }

    static async getAllEmployees() {
        console.log("getAllEmployees()...")
        try {
            let response = await fetch("http://localhost:7474/employees");
            
            if (!response.ok) {
                let result = await response.text();
                throw new Error(result);
            }

            let result = await response.json();

            let html =`
            <div class="card-header py-3" >
            <h5 class="m-0 font-weight-bold text-primary float-left">Employees</h5>
            </div>
            <div class="card-body">
                <table class="table table-hover">  
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Job Code</th>
                            <th scope="col">First</th>
                            <th scope="col">Last</th>
                            <th scope="col">Email</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            // construct employee table
           result.map((item) => {
            
            html +=`
                <tr>
                    <th scope="row">${item.emp_id}</th>
                    <td>${item.job_code}</td>
                    <td>${item.fname}</td>
                    <td>${item.lname}</td>
                    <td>${item.email}</td>
                </tr>
            `;            
           });

           html +=`
                        </tbody>
                    </table
                </div>
            </div>
           `;

           $('#list-wrapper').html(html);
            
        } catch (error) {
            console.error(error.message);
        }
    }

    
}


// Check for session credentials when page is loaded
window.onload = () => {

    // Get session user
    const employee = Authentication.checkAuth();


    // display search bar to search for an employee
    // only for manager
    if (employee.job_code == 200) {
        document.querySelector('.navbar-search div.input-group').hidden = false;
        document.querySelector('#getAllEmployees').hidden = false;
    }



    console.log("employee JSON -- ", employee);

    // Initialize dashboard actions
    const dashboard = new DashboardEvents(employee);

    $('#getAllReimbursements').click(() => {

        if (employee.job_code == 200) {
            return dashboard.getAllEmployeeReimbursements();
        } else if (employee.job_code == 100) { 
            return dashboard.getAllReimbursementRequest(employee.emp_id);
        }
        
    });

    $('#requestBtn').click(() => dashboard.requestReimbursement(employee.emp_id));

    // initialize submit event for editing employee info
    $('#employeeInfoForm').submit(dashboard.updateEmployeeInfo);

    $('#reqAll').click(() => dashboard.getAllReq(employee.job_code));
    $('#reqRejected').click(() => dashboard.getRejectedReimbursements(employee.job_code));
    $('#reqResolved').click(() => dashboard.getResolvedReimbursements(employee.job_code));
    $('#reqPending').click(() => dashboard.getPendingReimbursements(employee.job_code));

    // ends user's session
    $('#logOut').click(() => Authentication.logOut());

    $('#getAllEmployees').click(() => DashboardEvents.getAllEmployees());

    // get search result for specific user/employee
    $('#searchForm').submit((event) => {
        console.log("search for specific employee ID")
        event.preventDefault();

        const emp_id = $('#searchForm input').val().trim();
        console.log(emp_id)

        // Input validation
        try {
            if (emp_id == "") throw "No empty submissions allowed";
            if (isNaN(emp_id)) throw "Please select an integer for your search";
        } catch (error) {
            alert(error);
            return;
        }

        dashboard.getAllReimbursementRequest(emp_id)

    });

}
