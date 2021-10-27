package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.StudentDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.CollegeModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.StudentModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;


/**
 * Student functionality ctl is to perform add,delete ,update operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })

public class StudentCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(StudentCtl.class);
	
	protected void preload(HttpServletRequest request) {
		CollegeModelInt model =ModelFactory.getInstance().getCollegeModel() ;
		try {
			List l = model.list();
			request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}
	
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname",PropertyReader.getvalue("error.require","First Name"));
			pass = false;
		}
		else if(! DataValidator.isName(request.getParameter("firstname"))){
			request.setAttribute("firstname","Invalid First Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getvalue("error.require","Last Name"));
			pass = false;
		}
		
		else if(!DataValidator.isName(request.getParameter("lastname"))){
			request.setAttribute("lastname", "Invalid Last Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("college"))){
			request.setAttribute("college",PropertyReader.getvalue("error.require", "College Name"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getvalue("error.require", "DOB"));
			pass = false;
		}
		
		else if(!DataValidator.isValidAge(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getvalue("error.date", " Invalid DOB,"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.require", "MobileNo"));
			pass = false;
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile", "Invalid Mobile No");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getvalue("error.require", "Emailid") );
			pass = false;
		}
		else if(!DataValidator.isEmail(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getvalue("error.email", "Invalid"));
			pass = false;
		}
		
		log.debug("validate end");
		return pass;
		
	
	}
	
	
	protected BaseDTO populateBean(HttpServletRequest request){
		
		log.debug("StudentCtl Method populatebean Started");

		StudentDTO dto = new StudentDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setFirstname(DataUtility.getString(request.getParameter("firstname")));
		dto.setLastname(DataUtility.getString(request.getParameter("lastname")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobileno(DataUtility.getString(request.getParameter("mobile")));

		dto.setEmailid(DataUtility.getString(request.getParameter("email")));

		dto.setCollegeid(DataUtility.getLong(request.getParameter("college")));
		populateDTO(dto,request);
		
		log.debug("StudentCtl Method populatebean Ended");

		return dto;
		
		
		
	}
   
	
	
	
	/**
	 * Display logic inside it
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get model

		StudentModelInt model = ModelFactory.getInstance().getStudentModel();
		if (id > 0 || op != null) {
			StudentDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGett Ended");
		
	}

	/**
	 * Submit logic inside it
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		log.debug("StudentCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		StudentModelInt model = ModelFactory.getInstance().getStudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println(" save operation......");
			StudentDTO dto = (StudentDTO) populateBean(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					try {
						System.out.println("add dopsot");
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Student already exists", request);
					}

				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Student Email Id already exists", request);
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			StudentDTO dto = (StudentDTO) populateBean(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("StudentCtl Method doPost Ended");
		
	}




	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_VIEW;
	}

}
