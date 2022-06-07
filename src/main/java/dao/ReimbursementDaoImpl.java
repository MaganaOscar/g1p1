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
import model.ReimbursementPojo;

public class ReimbursementDaoImpl implements ReimbursementDao {
	
	private static final Logger LOG = LogManager.getLogger(ReimbursementDaoImpl.class);
	
	@Override
	public List<ReimbursementPojo> getAllRequests() throws ApplicationException {
		LOG.info("Enter getAllRequests() in ReimbursementDaoImpl...");
		try {
			List<ReimbursementPojo> reimbursements = new ArrayList<ReimbursementPojo>();
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details;";
			ResultSet resultSet = stmt.executeQuery(query);
			
			while(resultSet.next()) {
				reimbursements.add(new ReimbursementPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
						resultSet.getTimestamp(4), resultSet.getInt(5)));
			}
			LOG.info("Exited getAllRequestsByStatus() in ReimbursementDaoImpl...");
			return reimbursements;
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public List<ReimbursementPojo> getAllRequestsByStatus(String status) throws ApplicationException{
		LOG.info("Enter getAllRequestsByStatus() in ReimbursementDaoImpl...");
		try {
			List<ReimbursementPojo> reimbursements = new ArrayList<ReimbursementPojo>();
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE rb_status = '" + status + "';";
			ResultSet resultSet = stmt.executeQuery(query);
			
			while(resultSet.next()) {
				reimbursements.add(new ReimbursementPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
						resultSet.getTimestamp(4), resultSet.getInt(5)));
			}
			LOG.info("Exited getAllRequestsByStatus() in ReimbursementDaoImpl...");
			return reimbursements;
			
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public List<ReimbursementPojo> getEmployeeRequests(int emp_id) throws ApplicationException {
		LOG.info("Enter getEmployeeRequests() in ReimbursementDaoImpl...");
		try {
			List<ReimbursementPojo> reimbursements = new ArrayList<ReimbursementPojo>();
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE emp_id = " + emp_id + ";";
			ResultSet resultSet = stmt.executeQuery(query);
			
			while(resultSet.next()) {
				reimbursements.add(new ReimbursementPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
						resultSet.getTimestamp(4), resultSet.getInt(5)));
			}
			LOG.info("Exited getEmployeeRequests() in ReimbursementDaoImpl...");
			return reimbursements;
			
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public boolean updateRequestStatus(int rb_id, String newStatus) throws ApplicationException{
		LOG.info("Enter updateRequestStatus() in ReimbursementDaoImpl...");
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE reimbursement_details SET rb_status = '" + newStatus + "' WHERE rb_id = " + rb_id +";";
			LOG.info("Exited updateRequestStatus() in ReimbursementDaoImpl...");
			return stmt.executeUpdate(query) == 1;
			
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	
	@Override
	public boolean updateRequestDetail(int rb_id, double newAmount) throws ApplicationException{
		try{
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE reimbursement_details SET rb_amount = '" + newAmount + "' WHERE rb_id = " + rb_id +";";
			return stmt.executeUpdate(query) == 1;
			
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public boolean submitRequest(int emp_id, double amount) throws ApplicationException{
		LOG.info("Enter submitRequest() in ReimbursementDaoImpl...");
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) "
					+ "VALUES ('pending', " + amount + ", current_timestamp, " + emp_id + ");";
			LOG.info("Exited submitRequest() in ReimbursementDaoImpl...");
			return stmt.executeUpdate(query) == 1;
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public List<ReimbursementPojo> viewMyRequests(int emp_id, String status) throws ApplicationException{
		LOG.info("Enter viewMyRequests() in ReimbursementDaoImpl...");
		try {
			List<ReimbursementPojo> requests = new ArrayList<ReimbursementPojo>();
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE emp_id = " + emp_id + " AND rb_status = '" + status
					+ "';";
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()) {
				requests.add(new ReimbursementPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
						resultSet.getTimestamp(4), resultSet.getInt(5)));
			}
			LOG.info("Exited viewMyRequests() in ReimbursementDaoImpl...");
			return requests;
		} catch(SQLException e) {
			throw new ApplicationException(e.getMessage());
			
		}
	}
}
