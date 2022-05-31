package service;

import java.util.List;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import model.EmployeePojo;
import model.ReimbursementPojo;

public class MainServiceImpl implements MainService {
	
	EmployeeDao employeeDao;
	ReimbursementDao reimbursementDao;
	
	public MainServiceImpl() {
		employeeDao = new EmployeeDaoImpl();
		reimbursementDao = new ReimbursementDaoImpl();
	}

	@Override
	public EmployeePojo validateLogin(String email, String password) {
		return employeeDao.validateLogin(email, password);
	}

	@Override
	public EmployeePojo logout(int emp_id) {
		return employeeDao.logout(emp_id);
	}

	@Override
	public EmployeePojo getEmployee(int emp_id) {
		return employeeDao.getEmployee(emp_id);
	}

	@Override
	public boolean updateEmployee(int emp_id, int changeColumn, String newInfo) {
		return employeeDao.updateEmployee(emp_id, changeColumn, newInfo);
	}

	@Override
	public List<ReimbursementPojo> getAllRequests(String status) {
		return reimbursementDao.getAllRequests(status);
	}

	@Override
	public List<ReimbursementPojo> getEmployeeRequests(int emp_id) {
		return reimbursementDao.getEmployeeRequests(emp_id);
	}

	@Override
	public boolean updateRequest(int rb_id, String newStatus) {
		return reimbursementDao.updateRequest(rb_id, newStatus);
	}

	@Override
	public boolean submitRequest(int emp_id, double amount) {
		return reimbursementDao.submitRequest(emp_id, amount);
	}

	@Override
	public List<ReimbursementPojo> viewMyRequests(int emp_id, String status) {
		return reimbursementDao.viewMyRequests(emp_id, status);
	}

	@Override
	public List<EmployeePojo> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

}
