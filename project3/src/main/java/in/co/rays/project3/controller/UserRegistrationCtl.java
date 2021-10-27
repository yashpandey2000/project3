package in.co.rays.project3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.RoleDTO;
import in.co.rays.project3.dto.UserDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.UserModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;


/**
 * User registration functionality Controller. Performs operation for User
 * @author Yash Pandey
 *
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })

public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);
	public static final String OP_SIGN_UP = "SignUp";
    
	
protected boolean validate(HttpServletRequest request){
	
		
		boolean pass = true;
		
		//System.out.println("inside validate method");
		//System.out.println(request.getParameter("dob"));
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname", PropertyReader.getvalue("error.require","First Name") );
			pass=false;
		}
		else if(!DataValidator.isName(request.getParameter("firstname"))){
			request.setAttribute("firstname", "invalid First Name");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getvalue("error.require" , "Last Name"));
			pass=false;
		}
		else if(!DataValidator.isName(request.getParameter("lastname"))){
			request.setAttribute("lastname", "invalid Last Name");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getvalue("error.require" , "Emailid"));
			pass=false;
		}
		else if(!DataValidator.isEmail(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getvalue("error.email","invalid Emailid"));;
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("password"))){
			request.setAttribute("password",PropertyReader.getvalue("error.require" , "Password"));
			pass=false;
		}
		
		else if(!DataValidator.isPassword(request.getParameter("password"))){
			request.setAttribute("password", PropertyReader.getvalue("error.password" , "invalid"));
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("confirmpassword"))){
			request.setAttribute("confirmpassword", PropertyReader.getvalue("error.require" , "Confirm Password"));
			pass=false;
		}
	
		
//		if(DataValidator.isNull(request.getParameter("address"))){
//			request.setAttribute("address1", PropertyReader.getvalue("error.require" , "Address" ));
//		
//			pass=false;
//		}
//		
//		else if(!DataValidator.isAddress(request.getParameter("address"))){
//			request.setAttribute("address1", "invalid Address");
//		
//			pass=false;
//		}
		
		if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getvalue("error.require","Gender"));
			pass=false;
		}
		
		
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob1", PropertyReader.getvalue("error.require" ,"DOB"));
			pass=false;
		}
		
		
		else if(!DataValidator.isValidAge(request.getParameter("dob"))){
			request.setAttribute("dob1", PropertyReader.getvalue("error.date" , "DOB"));
		
			pass=false;
		}
		
		if(!request.getParameter("password").equals(request.getParameter("confirmpassword")) && !"".equals(request.getParameter("confirmpassword"))){
			request.setAttribute("confirmpassword","Password & Confirm Password should match");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.require" ,"MobileNo"));
		
			pass=false;
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.mobile","Invalid"));
			pass=false;
		}
		
		log.debug("validate end");
		return pass;
		
	}


protected BaseDTO populateBean(HttpServletRequest request) {
	log.debug("UserRegistrationCtl Method populatedto Started");

	UserDTO dto=new UserDTO();

	dto.setId(DataUtility.getLong(request.getParameter("id")));
	dto.setRoleid(RoleDTO.STUDENT);
	dto.setFirstname(DataUtility.getString(request.getParameter("firstname")));
	dto.setLastname(DataUtility.getString(request.getParameter("lastname")));
	dto.setLoginid(DataUtility.getString(request.getParameter("loginid")));
	dto.setPassword(DataUtility.getString(request.getParameter("password"))); 
	dto.setConfirmpassword(DataUtility.getString(request.getParameter("confirmpassword")));
	dto.setGender(DataUtility.getString(request.getParameter("gender")));
	dto.setMobileno(DataUtility.getString(request.getParameter("mobile")));
	dto.setDob(DataUtility.getDate(request.getParameter("dob")));
	
populateDTO(dto, request);
	
	log.debug("UserRegistrationCtl Method populatedto Ended");

	return dto;
}
	
/**
 * Display logic inside it
 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("operation");
		long id=DataUtility.getLong(request.getParameter("id"));
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		if(OP_SIGN_UP.equalsIgnoreCase(op)){
			UserDTO dto=(UserDTO) populateBean(request);
			try {
				long pk=userModel.registerUser(dto);
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Registration successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.setSuccessMessage("Registration successfully", request);
			ServletUtility.forward(ORSView.USER_REGISTRATION_VIEW, request, response);
		}else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		

	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
