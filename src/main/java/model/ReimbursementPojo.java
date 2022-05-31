package model;

import java.sql.Timestamp;
import java.util.Objects;

public class ReimbursementPojo {

	private int rb_id;
	private String rb_status;
	private double rb_amount;
	private Timestamp rb_timestamp;
	private int emp_id;
	
	public ReimbursementPojo() {
		super();
	}

	public ReimbursementPojo(int rb_id, String rb_status, double rb_amount, Timestamp timestamp, int emp_id) {
		super();
		this.rb_id = rb_id;
		this.rb_status = rb_status;
		this.rb_amount = rb_amount;
		this.rb_timestamp = timestamp;
		this.emp_id = emp_id;
	}

	public int getRb_id() {
		return rb_id;
	}

	public void setRb_id(int rb_id) {
		this.rb_id = rb_id;
	}

	public String getRb_status() {
		return rb_status;
	}

	public void setRb_status(String rb_status) {
		this.rb_status = rb_status;
	}

	public double getRb_amount() {
		return rb_amount;
	}

	public void setRb_amount(double rb_amount) {
		this.rb_amount = rb_amount;
	}

	public Timestamp getRb_timestamp() {
		return rb_timestamp;
	}

	public void setRb_timestamp(Timestamp rb_timestamp) {
		this.rb_timestamp = rb_timestamp;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(emp_id, rb_amount, rb_id, rb_status, rb_timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementPojo other = (ReimbursementPojo) obj;
		return emp_id == other.emp_id && Double.doubleToLongBits(rb_amount) == Double.doubleToLongBits(other.rb_amount)
				&& rb_id == other.rb_id && Objects.equals(rb_status, other.rb_status)
				&& Objects.equals(rb_timestamp, other.rb_timestamp);
	}

	@Override
	public String toString() {
		return "ReimbursementPojo [rb_id=" + rb_id + ", rb_status=" + rb_status + ", rb_amount=" + rb_amount
				+ ", rb_timestamp=" + rb_timestamp + ", emp_id=" + emp_id + "]";
	}
	
	
	
}
