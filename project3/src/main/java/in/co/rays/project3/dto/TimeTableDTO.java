package in.co.rays.project3.dto;

import java.util.Date;

/**
 * TimeTable JavaDto encapsulates TimeTable attributes
 * @author Yash Pandey
 *
 */
public class TimeTableDTO extends BaseDTO {

	private long courseid;
	private String coursename;
	private long subjectid;
	private String subjectname;
	private String semester;
	private String examtime;
	private Date examdate;
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
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getExamtime() {
		return examtime;
	}
	public void setExamtime(String examtime) {
		this.examtime = examtime;
	}
	public Date getExamdate() {
		return examdate;
	}
	public void setExamdate(Date examdate) {
		this.examdate = examdate;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return coursename;
	}
	
	

}
