package dao;

import java.util.List;

import exception.ApplicationException;
import model.EmployeePojo;

public interface EmployeeDao {

	EmployeePojo validateLogin(String email, String password) throws ApplicationException;

	EmployeePojo logout(int emp_id) throws ApplicationException;

	EmployeePojo getEmployee(int emp_id) throws ApplicationException;

	List<EmployeePojo> getAllEmployees() throws ApplicationException;

	EmployeePojo updateEmployee(int emp_id, String fname, String lname, String email) throws ApplicationException;
}
