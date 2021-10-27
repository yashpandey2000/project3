package in.co.rays.project3.dto;

import java.util.Date;


/**
 * Faculty JavaDto encapsulates Faculty attributes
 * @author Yash Pandey
 *
 */
public class FacultyDTO extends BaseDTO{
	
private String firstname;
private String lastname;
private String gender;
private String emailid;
private Date dateofjoining;
private String qualification;
private String mobileno;
private long collegeid;
private String collegename;
private long courseid ; 
private String coursename;
private long subjectid;
private String subjectname;
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
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}

public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public Date getDateofjoining() {
	return dateofjoining;
}
public void setDateofjoining(Date dateofjoining) {
	this.dateofjoining = dateofjoining;
}
public String getQualification() {
	return qualification;
}
public void setQualification(String qualification) {
	this.qualification = qualification;
}
public String getMobileno() {
	return mobileno;
}
public void setMobileno(String mobileno) {
	this.mobileno = mobileno;
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
public long getCourseid() {
	return courseid;
}
public void setCourseid(long courseid) {
	this.courseid = courseid;
}
public String getCoursename() {
	return coursename;
}
public void setCoursename(String coursename) {
	this.coursename = coursename;
}
public long getSubjectid() {
	return subjectid;
}
public void setSubjectid(long subjectid) {
	this.subjectid = subjectid;
}
public String getSubjectname() {
	return subjectname;
}
public void setSubjectname(String subjectname) {
	this.subjectname = subjectname;
}
public String getKey() {
	// TODO Auto-generated method stub
	return id+"";
}
public String getValue() {
	// TODO Auto-generated method stub
	return firstname+""+lastname;
}


	
}
