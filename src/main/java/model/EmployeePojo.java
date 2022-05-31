package model;

import java.util.Objects;

public class EmployeePojo {
	
	private int emp_id;
	private String password;
	private int job_code;
	private String fname;
	private String lname;
	private String email;
	
	public EmployeePojo() {
		super();
	}

	public EmployeePojo(int emp_id, String password, int job_code, String fname, String lname, String email) {
		super();
		this.emp_id = emp_id;
		this.password = password;
		this.job_code = job_code;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getJob_code() {
		return job_code;
	}

	public void setJob_code(int job_code) {
		this.job_code = job_code;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmployeePojo [emp_id=" + emp_id + ", password=" + password + ", job_code=" + job_code + ", fname="
				+ fname + ", lname=" + lname + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, emp_id, fname, job_code, lname, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePojo other = (EmployeePojo) obj;
		return Objects.equals(email, other.email) && emp_id == other.emp_id && Objects.equals(fname, other.fname)
				&& job_code == other.job_code && Objects.equals(lname, other.lname)
				&& Objects.equals(password, other.password);
	}
	
	
}
