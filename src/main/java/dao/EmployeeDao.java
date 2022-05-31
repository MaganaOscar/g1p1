package dao;

import java.util.List;

import model.EmployeePojo;

public interface EmployeeDao {
	
	EmployeePojo validateLogin(String email, String password);
	EmployeePojo logout(int emp_id);
	
	EmployeePojo getEmployee(int emp_id);
	List<EmployeePojo> getAllEmployees();
	boolean updateEmployee(int emp_id, int changeColumn, String newInfo);
}
