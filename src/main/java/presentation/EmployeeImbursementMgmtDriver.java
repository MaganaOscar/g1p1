package presentation;

import java.util.List;

import io.javalin.Javalin;
import model.EmployeePojo;
import model.ReimbursementPojo;
import service.MainService;
import service.MainServiceImpl;

public class EmployeeImbursementMgmtDriver {

	public static void main(String[] args) {

		MainService mainServ = new MainServiceImpl();
		
		Javalin server = Javalin.create((config) -> config.enableCorsForAllOrigins());
		server.start(7474);
		
		//get all employee reimbursements by status
		server.get("/reimbursements", ctx -> {
			List<ReimbursementPojo> reimbursements = mainServ.getAllRequests();
					
			if (reimbursements != null) {
				ctx.json(reimbursements).status(200);
			} else {
				ctx.result("No Results Found").status(404);
			}
		});
				
		//get all requests by status
		server.get("/reimbursements/{status}", ctx -> {
			List<ReimbursementPojo> reimbursements = mainServ.getAllRequestsByStatus(ctx.pathParam("status"));
			ctx.json(reimbursements);
		});
		//get all requests for employee
		server.get("/reimbursements/emp/{emp_id}", ctx -> {
			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
			List<ReimbursementPojo> reimbursements = mainServ.getEmployeeRequests(emp_id);
			ctx.json(reimbursements);
		});
		
		//get all requests for employee by status
		server.get("/reimbursements/emp/{emp_id}/{status}", ctx -> {
			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
			String status = ctx.pathParam("status");
			List<ReimbursementPojo> reimbursements = mainServ.viewMyRequests(emp_id, status);
			ctx.json(reimbursements);
		});
		
		//update reimbursement request status
		server.put("/reimbursements/status/update", ctx -> {
			int rb_id = Integer.parseInt(ctx.formParam("rb_id"));
			String rb_status = ctx.formParam("rb_status");

			// submit parameters to DB			
			if(mainServ.updateRequestStatus(rb_id, rb_status)) {
				ctx.result("Reimbursement Status Updated Successfully!").status(200);
			} else {
				ctx.result("Reimbursement Status Update failed to submit!").status(417);
			}	
		});
				
		//update reimbursement request details
		server.put("/reimbursements/details/update", ctx -> {
			int rb_id = Integer.parseInt(ctx.formParam("rb_id"));
			double rb_amount = Double.parseDouble(ctx.formParam("amount"));
					
			// submit parameters to DB			
			if(mainServ.updateRequestDetail(rb_id, rb_amount)) {
				ctx.result("Reimbursement Amount Updated Successfully!").status(200);
			} else {
				ctx.result("Reimbursement Amount Update failed to submit!").status(417);
			}	
		});
		
		//create reimbursement request
		server.post("/reimbursements", ctx -> {
			int emp_id = Integer.parseInt(ctx.formParam("emp_id"));
			double amount = Double.parseDouble(ctx.formParam("amount"));
			// submit parameters to DB			
			if(mainServ.submitRequest(emp_id, amount)) {
				ctx.result("Reimbursement submitted Successfully!").status(201);
			} else {
				ctx.result("Reimbursement request failed to submit!").status(417);
			}
		});
		
		//on login, hold emp_id in session
		server.post("/login", ctx -> {
			String email = ctx.formParam("email");
			String password = ctx.formParam("password");
			EmployeePojo user = mainServ.validateLogin(email, password);
			if (user == null) {
				ctx.result("No match found. Please try again.").status(400);
			} else {
				user.setPassword(null);
				ctx.json(user);
			}
		});
		
		//clear out session on logout
		server.get("/logout", ctx -> {
			ctx.consumeSessionAttribute("emp_id");
		});
		
		//get specific employee details
		server.get("/employees/{emp_id}", ctx -> {
			int emp_id = ctx.sessionAttribute("emp_id");
			EmployeePojo emp = mainServ.getEmployee(emp_id);
			ctx.json(emp);
		});
		
		//update employee Info
		server.put("/employees/update", ctx -> {
			// get formdata from client side
			int emp_id = Integer.parseInt(ctx.formParam("empID"));
			String fname = ctx.formParam("fname");
			String lname = ctx.formParam("lname"); 
			String email = ctx.formParam("email");
			EmployeePojo emp = mainServ.updateEmployee(emp_id, fname, lname, email);
							
			if(emp != null) {
				ctx.status(200).json(emp);
			} else {
				ctx.result("Employee Information Update Failure.").status(417);
			}	
		});
		
		//get all employees
		server.get("employees", ctx -> {
			List<EmployeePojo> employees = mainServ.getAllEmployees();
			if(employees != null) {
				ctx.status(200).json(employees);				
			} else {
				ctx.result("No results found.").status(404);
			}
		});
	}

}
