package service;

import java.util.List;

import model.EmployeePojo;
import model.ReimbursementPojo;

public interface MainService {
	EmployeePojo validateLogin(String email, String password);
	EmployeePojo logout(int emp_id);
	EmployeePojo getEmployee(int emp_id);
	boolean updateEmployee(int emp_id, int changeColumn, String newInfo);
	List<EmployeePojo> getAllEmployees();
	
	List<ReimbursementPojo> getAllRequests(String status);
	List<ReimbursementPojo> getEmployeeRequests(int emp_id);
	boolean updateRequest(int rb_id, String newStatus);
	boolean submitRequest(int emp_id, double amount);
	List<ReimbursementPojo> viewMyRequests(int emp_id, String status);
}
