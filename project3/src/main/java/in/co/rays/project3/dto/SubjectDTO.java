package in.co.rays.project3.dto;

/**
 * Subject JavaDto encapsulates Subject attributes
 * @author Yash Pandey
 *
 */
public class SubjectDTO extends BaseDTO {
	private long courseid;
	private String coursename;
	private String subjectname;
	private String description;
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
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return subjectname;
	}
	
	

}
