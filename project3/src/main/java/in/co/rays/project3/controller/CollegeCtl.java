package in.co.rays.project3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.CollegeModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;




/**
 * college functionality ctl is To perform add,delete ,update operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })

public class CollegeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
private static Logger log = Logger.getLogger(CollegeCtl.class);

       
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("cname"))){
			request.setAttribute("cname", PropertyReader.getvalue("error.require", "College Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("cname"))){
			request.setAttribute("cname","Invalid College Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("caddress"))){
			request.setAttribute("caddress", PropertyReader.getvalue("error.require", "College Addrss"));
			pass = false;
		}
//		else if(!DataValidator.isAddress(request.getParameter("caddress"))){
//			request.setAttribute("caddress", "Invalid Address");
//			pass = false;
//		}
		if(DataValidator.isNull(request.getParameter("cstate"))){
			request.setAttribute("cstate", PropertyReader.getvalue("error.require", "State"));
		pass = false;	
		}
		else if(!DataValidator.isName(request.getParameter("cstate"))){
			request.setAttribute("cstate", "Invalid State Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("ccity"))){
			request.setAttribute("ccity", PropertyReader.getvalue("error.require", "City"));
			pass = false;
		}
		
		else if(!DataValidator.isName(request.getParameter("ccity"))){
			request.setAttribute("ccity", "Invalid City Name");			
		pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("cphone"))){
			request.setAttribute("cphone",PropertyReader.getvalue("error.require", "Mobile No"));
			pass = false;
		}
		
		else if(!DataValidator.isPasswordLength(request.getParameter("cphone"))){
			request.setAttribute("cphone", "Invalid Phone No");
			pass = false;
		}
		
		log.debug("validate end");
		return pass;
	}
	
protected BaseDTO populateBean(HttpServletRequest request){
		
	CollegeDTO dto = new CollegeDTO();
		
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("cname")));
		dto.setAddress(DataUtility.getString(request.getParameter("caddress")));
		dto.setState(DataUtility.getString(request.getParameter("cstate")));
		dto.setCity(DataUtility.getString(request.getParameter("ccity")));
		dto.setPhoneno(DataUtility.getString(request.getParameter("cphone")));
		
		populateDTO(dto, request);
		
		return dto;
		
	}
   

/**
 * Dispaly logic inside it
 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		if (id > 0 || op != null) {
			CollegeDTO dto;
			try {
			  dto=model.findByPK(id);
			  ServletUtility.setDto(dto, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
		
	}

	
	 /**
     * Submit logic inside it
     */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String op=request.getParameter("operation");
	       long id=DataUtility.getLong(request.getParameter("id"));
	       CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
	       if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
	    	   CollegeDTO dto = (CollegeDTO) populateBean(request);
				System.out.println("...===+" + id + ">>>>>>..." + dto);
				try {
					if (id > 0) {
						model.update(dto);
						ServletUtility.setDto(dto, request);
						
						ServletUtility.setSuccessMessage("Data is successfully Update", request);

					} else {
						System.out.println("college add" + dto + "id...." + id);
						long pk = model.add(dto);
						ServletUtility.setSuccessMessage("Data succefully save", request);
					}
					ServletUtility.setDto(dto, request);
				} catch (ApplicationException e) {
					e.printStackTrace();
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("College already exists", request);
				} 
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				CollegeDTO dto = (CollegeDTO) populateBean(request);
				try {
					model.delete(dto);
					ServletUtility.forward(ORSView.COLLEGE_LIST_CTL, request, response);
					return;
				} catch (Exception e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}
			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;

			}
			ServletUtility.forward(getView(), request, response);
		
		
	}


	@Override
	protected String getView() {
		
		return ORSView.COLLEGE_VIEW;
	}
	

}
