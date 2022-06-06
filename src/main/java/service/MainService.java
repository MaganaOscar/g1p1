package service;

import java.util.List;

import exception.ApplicationException;
import model.EmployeePojo;
import model.ReimbursementPojo;

public interface MainService {
	EmployeePojo validateLogin(String email, String password) throws ApplicationException;
	EmployeePojo logout(int emp_id) throws ApplicationException;
	EmployeePojo getEmployee(int emp_id) throws ApplicationException;
	boolean updateEmployee(int emp_id, int changeColumn, String newInfo) throws ApplicationException;
	List<EmployeePojo> getAllEmployees() throws ApplicationException;
	
	List<ReimbursementPojo> getAllRequests(String status) throws ApplicationException;
	List<ReimbursementPojo> getEmployeeRequests(int emp_id) throws ApplicationException;
	boolean updateRequest(int rb_id, String newStatus) throws ApplicationException;
	boolean submitRequest(int emp_id, double amount) throws ApplicationException;
	List<ReimbursementPojo> viewMyRequests(int emp_id, String status) throws ApplicationException;
}
