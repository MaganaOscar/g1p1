package dao;

import java.util.List;

import exception.ApplicationException;
import model.ReimbursementPojo;

public interface ReimbursementDao {
	
	List<ReimbursementPojo> getAllRequests() throws ApplicationException;
	List<ReimbursementPojo> getAllRequestsByStatus(String status) throws ApplicationException;
	List<ReimbursementPojo> getEmployeeRequests(int emp_id) throws ApplicationException;
	boolean updateRequestStatus(int rb_id, String newStatus) throws ApplicationException;
	boolean updateRequestDetail(int rb_id, double newAmount) throws ApplicationException;
	boolean submitRequest(int emp_id, double amount) throws ApplicationException;
	List<ReimbursementPojo> viewMyRequests(int emp_id, String status) throws ApplicationException;
}
