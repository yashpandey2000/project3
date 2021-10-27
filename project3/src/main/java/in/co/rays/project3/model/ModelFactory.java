package in.co.rays.project3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

public class ModelFactory {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) { 
			if ("Hibernate".equals(DATABASE)) {
				 marksheetModel=new MarksheetModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				 marksheetModel=new MarksheetModelJDBCImp();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}
	
	
	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 collegeModel=new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				 collegeModel=new CollegeModelJDBCImp();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}
	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 roleModel=new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				 roleModel=new RoleModelJDBCImp();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

public UserModelInt getUserModel() {
		
		System.out.println("hhhhhhhhhhhhh"+DATABASE);
		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				 userModel = new UserModelJDBCImp();
			}
			modelCache.put("userModel", userModel);
		}

		 System.out.println("mf end-------------->"+userModel);
		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				 studentModel = new StudentModelJDBCImp();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				 courseModel = new CourseModelJDBCImp();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimeTableModelInt getTimetableModel() {
		TimeTableModelInt timetableModel = (TimeTableModelInt) modelCache.get("timetableModel");
		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 timetableModel = new TimeTableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				 timetableModel = new TimeTableModelJDBCImp();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				 subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImp();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImp();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}

}
