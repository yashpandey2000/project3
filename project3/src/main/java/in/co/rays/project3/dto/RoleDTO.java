package in.co.rays.project3.dto;

/**
 * Role JavaDto encapsulates Role attributes
 * @author Yash Pandey
 *
 */
public class RoleDTO extends BaseDTO {
	
	public static final int ADMIN = 1;
	public static final int STUDENT = 3;
	public static final int FACULTY = 5;
	public static final int KIOSK = 4;
	public static final int COLLEGE = 2;
	
	private String name;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return name;
	}
	
	
	
	

}
