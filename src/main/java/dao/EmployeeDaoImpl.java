package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.ApplicationException;
import model.EmployeePojo;
import service.MainServiceImpl;

public class EmployeeDaoImpl implements EmployeeDao {
	
	private static final Logger LOG = LogManager.getLogger(EmployeeDaoImpl.class);

	@Override
	public EmployeePojo validateLogin(String email, String password) throws ApplicationException{
		LOG.info("Enter validateLogin() in EmployeeDaoImpl...");
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE email = '" + email + "' AND password = crypt('"
					+ password + "', password);";
			ResultSet resultSet = stmt.executeQuery(query);
			
			if(!resultSet.isBeforeFirst()) {
				return null;
			} else {
				resultSet.next();
				LOG.info("Exited validateLogin() in EmployeeDaoImpl...");
				return new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6));
			}
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		
	}

	@Override
	public EmployeePojo logout(int emp_id) throws ApplicationException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeePojo getEmployee(int emp_id) throws ApplicationException {
		LOG.info("Enter getEmployee() in EmployeeDaoImpl...");
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE emp_id =" + emp_id + ";";
			ResultSet resultSet = stmt.executeQuery(query);
			
			if(!resultSet.isBeforeFirst()) {
				LOG.info("Exit getEmployee() in EmployeeDaoImpl...");
				return null;
			} else {
				resultSet.next();
				LOG.info("Exit getEmployee() in EmployeeDaoImpl...");
				return new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6));
			}
			
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public boolean updateEmployee(int emp_id, int changeColumn, String newInfo) throws ApplicationException{
		LOG.info("Enter updateEmployee() in EmployeeDaoImpl...");
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			
			//fname
			if(changeColumn == 1) {
				String query = "UPDATE employee_details SET fname ='" + newInfo + "' WHERE emp_id =" + emp_id + ";";
				LOG.info("Exited getAllEmployees() in EmployeeDaoImpl...");
				return stmt.executeUpdate(query) == 1;
				
			//lname	
			} else if(changeColumn == 2) {
				String query = "UPDATE employee_details SET lname ='" + newInfo + "' WHERE emp_id =" + emp_id + ";";
				LOG.info("Exited updateEmployee() in EmployeeDaoImpl...");
				return stmt.executeUpdate(query) == 1;
			//email
			} else if(changeColumn == 3) {
				String query = "UPDATE employee_details SET email ='" + newInfo + "' WHERE emp_id =" + emp_id + ";";
				LOG.info("Exited updateEmployee() in EmployeeDaoImpl...");
				return stmt.executeUpdate(query) == 1;
			//password
			} else if(changeColumn == 4) {
				String query = "UPDATE employee_details SET password = crypt('" + newInfo + "', password) WHERE emp_id =" + emp_id + ";";
				LOG.info("Exited updateEmployee() in EmployeeDaoImpl...");
				return stmt.executeUpdate(query) == 1;
			} else {
				LOG.info("Exited updateEmployee() in EmployeeDaoImpl...");
				return false;
			}
			
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public List<EmployeePojo> getAllEmployees() throws ApplicationException{
		LOG.info("Enter getAllEmployees() in EmployeeDaoImpl...");
		try {
			List<EmployeePojo> employees = new ArrayList<EmployeePojo>();
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details;";
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()) {
				employees.add(new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6)));
			}
			LOG.info("Exited getAllEmployees() in EmployeeDaoImpl...");
			return employees;
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

}
