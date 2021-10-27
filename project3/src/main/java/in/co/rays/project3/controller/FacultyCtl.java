package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.FacultyDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.CollegeModelInt;
import in.co.rays.project3.model.CourseModelInt;
import in.co.rays.project3.model.FacultyModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.SubjectModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;



/**
 * Faculty functionality ctl is to perform add,delete ,update operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })

public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(FacultyCtl.class);
	
	protected void preload(HttpServletRequest request) {
		try {
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
		
			List l = model.list();
			List li = model1.list();
			List list = model2.list();
			request.setAttribute("collegeList", l);
			request.setAttribute("courseList", li);
			request.setAttribute("subjectList", list);
	     System.out.println("............"+l+".."+li+".."+list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("fname"))){
			request.setAttribute("fname", PropertyReader.getvalue( "error.require" , "First Name"));
		pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("fname"))){
			request.setAttribute("lname", "Invalid First Name");
			pass= false;
		}
		if(DataValidator.isNull(request.getParameter("lname"))){
			request.setAttribute("lname", PropertyReader.getvalue("error.require", "Last Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("lname"))){
			request.setAttribute("lname", "Invalid Last Name");
		  pass = false;	
		}
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getvalue("error.require", "Emailid"));
			pass = false;
		}
		else if(!DataValidator.isEmail(request.getParameter("loginid"))){
			request.setAttribute("loginid", "Invalid Emailid");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("doj"))){
			request.setAttribute("doj", PropertyReader.getvalue("error.require", "Date Of Joining"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.require", "Mobile"));
			pass = false;
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile", "Invalid Mobile No");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("qual"))){
			request.setAttribute("qual", PropertyReader.getvalue("error.require", "Qualification"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("collegeid"))){
			request.setAttribute("collegename", PropertyReader.getvalue("error.require", "College Name"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("courseid"))){
			request.setAttribute("coursename", PropertyReader.getvalue("error.require", "Course Name"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("subjectid"))){
			request.setAttribute("subjectname", PropertyReader.getvalue("error.require", "Subject Name"));
		pass = false;
		}if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getvalue("error.require", "Gender"));
			pass = false;
		}

		 log.debug("validate end");
		return pass;
	
	}
	
	
	protected BaseDTO populateBean(HttpServletRequest request) {
		log.debug("faculty ctl populate bean start");
		System.out.println("faculty bean populate start");
		FacultyDTO dto = new FacultyDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFirstname(DataUtility.getString(request.getParameter("fname")));
		dto.setLastname(DataUtility.getString(request.getParameter("lname")));
		dto.setEmailid(DataUtility.getString(request.getParameter("loginid")));
		dto.setQualification(DataUtility.getString(request.getParameter("qual")));
		dto.setDateofjoining(DataUtility.getDate(request.getParameter("doj")));
		dto.setGender(request.getParameter("gender"));
		dto.setMobileno(DataUtility.getString(request.getParameter("mobile")));
		dto.setCollegeid(DataUtility.getLong(request.getParameter("collegeid")));
		dto.setCourseid(DataUtility.getLong(request.getParameter("courseid")));
		dto.setSubjectid(DataUtility.getLong(request.getParameter("subjectid")));
		populateDTO(dto,request);
		log.debug("faculty ctl populate bean end");
		return dto;

	}
	
	
	/**
	 * Display logic inside it
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		FacultyModelInt model =ModelFactory.getInstance().getFacultyModel() ;
		FacultyDTO dto = new FacultyDTO();
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("faculty ctl do get end");
		
		
		
	}

	
	/**
	 * Submit logic inside it
	 */

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("faculty do post start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("hellooooo"+id+"hhh"+op);
		FacultyModelInt model =ModelFactory.getInstance().getFacultyModel() ;
		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyDTO dto = (FacultyDTO) populateBean(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					
					try {
						 model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Faculty id already exists", request);
					} 

				}
				ServletUtility.setDto(dto, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Faculty id already exists", request);
			} 
		
		}else if(OP_DELETE.equalsIgnoreCase(op)){
			System.out.println("alteast");
			FacultyDTO dto=(FacultyDTO) populateBean(request);
			try{
				model.delete(dto);
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			}catch(ApplicationException e){
				log.debug(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_CANCEL.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("faculty do post end");
		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}
