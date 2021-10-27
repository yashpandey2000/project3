package in.co.rays.project3.dto;

import java.util.Date;

/**
 * Student JavaDto encapsulates Student attributes
 * @author Yash Pandey
 *
 */
public class StudentDTO extends BaseDTO{
	private String firstname;
	private String lastname;
	private Date dob;
	private String mobileno;
	private String emailid;
	private long collegeid;
	private String collegename;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public long getCollegeid() {
		return collegeid;
	}
	public void setCollegeid(long collegeid) {
		this.collegeid = collegeid;
	}
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return firstname+" "+lastname;
	}
	
	
	

}
