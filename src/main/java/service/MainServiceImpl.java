package service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import exception.ApplicationException;
import model.EmployeePojo;
import model.ReimbursementPojo;

public class MainServiceImpl implements MainService {
	
	private static final Logger LOG = LogManager.getLogger(MainServiceImpl .class);
	
	EmployeeDao employeeDao;
	ReimbursementDao reimbursementDao;
	
	public MainServiceImpl() {
		employeeDao = new EmployeeDaoImpl();
		reimbursementDao = new ReimbursementDaoImpl();
	}

	@Override
	public EmployeePojo validateLogin(String email, String password) throws ApplicationException{
		LOG.info("Entered validateLogin() in service.");
		LOG.info("Exited validateLogin() in service.");
		return employeeDao.validateLogin(email, password);
		
	}

	@Override
	public EmployeePojo logout(int emp_id) throws ApplicationException {
		LOG.info("Entered logout() in service.");
		LOG.info("Exited logout() in service.");
		return employeeDao.logout(emp_id);
	}

	@Override
	public EmployeePojo getEmployee(int emp_id) throws ApplicationException {
		LOG.info("Entered getEmployee() in service.");
		LOG.info("Exited  getEmployee() in service.");
		return employeeDao.getEmployee(emp_id);
	}

	@Override
	public boolean updateEmployee(int emp_id, int changeColumn, String newInfo) throws ApplicationException{
		LOG.info("Entered updateEmployee() in service.");
		LOG.info("Exited updateEmployee() in service.");
		return employeeDao.updateEmployee(emp_id, changeColumn, newInfo);
	}

	@Override
	public List<ReimbursementPojo> getAllRequests(String status) throws ApplicationException{
		LOG.info("Entered getAllRequests() in service.");
		LOG.info("Exited getAllRequests() in service.");
		return reimbursementDao.getAllRequests(status);
	}

	@Override
	public List<ReimbursementPojo> getEmployeeRequests(int emp_id) throws ApplicationException {
		LOG.info("Entered getEmployeeRequests() in service.");
		LOG.info("Exited getEmployeeRequests() in service.");
		return reimbursementDao.getEmployeeRequests(emp_id);
	}

	@Override
	public boolean updateRequest(int rb_id, String newStatus) throws ApplicationException{
		LOG.info("Entered updateRequest() in service.");
		LOG.info("Exited updateRequest() in service.");
		return reimbursementDao.updateRequest(rb_id, newStatus);
	}

	@Override
	public boolean submitRequest(int emp_id, double amount) throws ApplicationException{
		LOG.info("Entered submitRequest() in service.");
		LOG.info("Exited submitRequest() in service.");
		return reimbursementDao.submitRequest(emp_id, amount);
	}

	@Override
	public List<ReimbursementPojo> viewMyRequests(int emp_id, String status) throws ApplicationException {
		LOG.info("Entered viewMyRequests() in service.");
		LOG.info("Exited viewMyRequests() in service.");
		return reimbursementDao.viewMyRequests(emp_id, status);
	}

	@Override
	public List<EmployeePojo> getAllEmployees() throws ApplicationException {
		LOG.info("Entered getAllEmployees() in service.");
		LOG.info("Exited getAllEmployees() in service.");
		return employeeDao.getAllEmployees();
	}

}
