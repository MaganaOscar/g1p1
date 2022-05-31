package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.EmployeePojo;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public EmployeePojo validateLogin(String email, String password) {
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
				return new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6));
			}
		} catch(SQLException e) {
			e.printStackTrace();
//			throw new SystemException();
			return null;
		}
	}

	@Override
	public EmployeePojo logout(int emp_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeePojo getEmployee(int emp_id) {
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE emp_id =" + emp_id + ";";
			ResultSet resultSet = stmt.executeQuery(query);
			
			if(!resultSet.isBeforeFirst()) {
				return null;
			} else {
				resultSet.next();
				return new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateEmployee(int emp_id, int changeColumn, String newInfo) {
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			
			//fname
			if(changeColumn == 1) {
				String query = "UPDATE employee_details SET fname ='" + newInfo + "' WHERE emp_id =" + emp_id + ";";
				return stmt.executeUpdate(query) == 1;
				
			//lname	
			} else if(changeColumn == 2) {
				String query = "UPDATE employee_details SET lname ='" + newInfo + "' WHERE emp_id =" + emp_id + ";";
				return stmt.executeUpdate(query) == 1;
			//email
			} else if(changeColumn == 3) {
				String query = "UPDATE employee_details SET email ='" + newInfo + "' WHERE emp_id =" + emp_id + ";";
				return stmt.executeUpdate(query) == 1;
			//password
			} else if(changeColumn == 4) {
				String query = "UPDATE employee_details SET password = crypt('" + newInfo + "', password) WHERE emp_id =" + emp_id + ";";
				return stmt.executeUpdate(query) == 1;
			} else {
				return false;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<EmployeePojo> getAllEmployees() {
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
			return employees;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
