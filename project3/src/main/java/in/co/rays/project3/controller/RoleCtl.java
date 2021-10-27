package in.co.rays.project3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.RoleDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.RoleModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;




/**
 *Role functionality ctl is to perform add,delete ,update operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })

public class RoleCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(RoleCtl.class);
	
	 protected boolean validate(HttpServletRequest request){
		   log.debug("validate start");
		   boolean pass = true;
		   
		   if(DataValidator.isNull(request.getParameter("name"))){ 
			   request.setAttribute("name", PropertyReader.getvalue("error.require", "Name"));
			  pass = false; 
		   }
		   else if(!DataValidator.isName(request.getParameter("name"))){
			   request.setAttribute("name","Invalid Role Name");
			   pass = false;
		   }
		   
		   if(DataValidator.isNull(request.getParameter("desc"))){
			   request.setAttribute("desc",PropertyReader.getvalue("error.require","Description"));
			   pass = false;
			   
		   } else if(!DataValidator.isName(request.getParameter("desc"))){
			   request.setAttribute("desc","Invalid Description");
			   pass = false;
		   }
		   
		   log.debug("validate end");
		     
		   return pass;
	   
	   }
	 
	 protected BaseDTO populateBean(HttpServletRequest request){
		 
		 RoleDTO dto = new RoleDTO();
			dto.setId(DataUtility.getLong(request.getParameter("id")));

			dto.setName(DataUtility.getString(request.getParameter("name")));
			dto.setDescription(DataUtility.getString(request.getParameter("desc")));
			populateDTO(dto,request);
			return dto;
		 
		 
		 
	 }
    
	 
	 /**
		 * Display logic inside it
		 */
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		if (id > 0 || op != null) {
			RoleDTO dto;
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
		
	}

	
	/**
	 * Submit logic inside it
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		System.out.println(" method do postkkkkkkkkk");
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			RoleDTO dto = (RoleDTO) populateBean(request);
			System.out.println("kkkkkkkkkkkk"+dto);
          //  System.out.println("kkkkk"+dto.getName()+"sdf"+dto.getDescription());
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					try {
						long pk = model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Role already exists", request);
					}

				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Role already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			RoleDTO dto = (RoleDTO) populateBean(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("RoleCtl Method doPOst Ended");
	}
		



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_VIEW;
	}

}
