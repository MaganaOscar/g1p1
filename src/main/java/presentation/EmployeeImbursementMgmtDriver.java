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
		
		//get all requests by status
		server.get("/reimbursements/{status}", ctx -> {
			List<ReimbursementPojo> reimbursements = mainServ.getAllRequests(ctx.pathParam("status"));
			ctx.json(reimbursements);
		});
		//get all requests for employee
		server.get("/reimbursements/emp/{emp_id}", ctx -> {
//			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
			int emp_id = ctx.sessionAttribute("emp_id");
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
		
		//update reimbursement request
		server.put("/reimbursements", ctx -> {
			int rb_id = Integer.parseInt(ctx.formParam("rb_id"));
			String rb_status = ctx.formParam("rb_status");
			mainServ.updateRequest(rb_id, rb_status);
		});
		
		//create reimbursement request
		server.post("/reimbursements", ctx -> {
			int emp_id = Integer.parseInt(ctx.formParam("emp_id"));
			double amount = Double.parseDouble(ctx.formParam("amount"));
			mainServ.submitRequest(emp_id, amount);
		});
		
		//on login, hold emp_id in session
		server.post("/login", ctx -> {
			String email = ctx.formParam("email");
			String password = ctx.formParam("password");
			EmployeePojo user = mainServ.validateLogin(email, password);
			if (user == null) {
				ctx.status(400);
			} else {
				user.setPassword(null);
				ctx.sessionAttribute("emp_id", user.getEmp_id());
				int s = ctx.sessionAttribute("emp_id");
				Integer emp_id = user.getEmp_id();
				System.out.println(s);
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
		//update employee, should convert to user form and default to old values
//		server.put("/employees/{emp_id}", ctx -> {
//			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
//			mainServ
//		});
//		
		//get all employees
		server.get("employees", ctx -> {
			List<EmployeePojo> employees = mainServ.getAllEmployees();
			ctx.json(employees);
		});
	}

}
