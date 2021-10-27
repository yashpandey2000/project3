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
import in.co.rays.project3.dto.MarksheetDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.MarksheetModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.StudentModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;

/**
 * 
 * Marksheet functionality ctl.to perform add,delete ,update operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = "/ctl/MarksheetCtl")

public class MarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	protected void preload(HttpServletRequest request) {
		StudentModelInt model = ModelFactory.getInstance().getStudentModel();
		
		 try {
				List list = model.list();
				request.setAttribute("studentList", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	 protected boolean validate(HttpServletRequest request){
		   log.debug("validate start");
		boolean pass = true;
		
		
		if(DataValidator.isNull(request.getParameter("rollno"))){
			request.setAttribute("rollno",PropertyReader.getvalue("error.require","RollNo"));
			pass = false;
		}
		else if(DataValidator.isRollNo(request.getParameter("rollno"))){
			request.setAttribute("rollno", "Invalid Roll No (Enter Roll no in format as  Ex. 01cs01)");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("studentid"))){
			request.setAttribute("studentid", PropertyReader.getvalue("error.require","Student Name" ));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("physics"))){
			request.setAttribute("physics", PropertyReader.getvalue("error.require","Physics"));
			pass = false;
		}   
		else if(DataUtility.getInt(request.getParameter("physics"))>100) {
			request.setAttribute("physics", "Marks Can Not Be More Than 100");
			pass = false;
		}  
		else if(DataUtility.getInt(request.getParameter("physics"))<0){
			request.setAttribute("physics", "Marks Can Not Be Negative");
			pass = false;
		}  
		  
		else if(!DataValidator.isInteger(request.getParameter("physics")) && DataValidator.isNotNull(request.getParameter("physics"))){
			request.setAttribute("physics", "Enter Number Only");
			pass = false;
		}
		
		
		
		if(DataValidator.isNull(request.getParameter("chemistry"))){
			request.setAttribute("chemistry", PropertyReader.getvalue("error.require","chemistry"));
			pass = false;
		}   
		else if(DataUtility.getInt(request.getParameter("chemistry"))>100) {
			request.setAttribute("chemistry", "Marks Can Not Be More Than 100");
			pass = false;
		}  
		else if(DataUtility.getInt(request.getParameter("chemistry"))<0){
			request.setAttribute("chemistry", "Marks Can Not Be Negative");
			pass = false;
		}  
		  
		else if(!DataValidator.isInteger(request.getParameter("chemistry")) && DataValidator.isNotNull(request.getParameter("physics"))){
			request.setAttribute("chemistry", "Enter Number Only");
			pass = false;
		}
		
		

		if(DataValidator.isNull(request.getParameter("math"))){
			request.setAttribute("math", PropertyReader.getvalue("error.require","math"));
			pass = false;
		}   
		else if(DataUtility.getInt(request.getParameter("math"))>100) {
			request.setAttribute("math", "Marks Can Not Be More Than 100");
			pass = false;
		}  
		else if(DataUtility.getInt(request.getParameter("math"))<0){
			request.setAttribute("math", "Marks Can Not Be Negative");
			pass = false;
		}  
		  
		else if(!DataValidator.isInteger(request.getParameter("math")) && DataValidator.isNotNull(request.getParameter("physics"))){
			request.setAttribute("math", "Enter Number Only");
			pass = false;
		}
		
		
		 log.debug("validate end");
	   
		   return pass;
		}
	 
	 
	 protected BaseDTO populateBean(HttpServletRequest request){
		 
		 log.debug("marksheet populate bean start");
			MarksheetDTO dto = new MarksheetDTO();
		 
		 dto.setId(DataUtility.getLong(request.getParameter("id")));
		  dto.setRollno(DataUtility.getString(request.getParameter("rollno")));
		  dto.setStudentid(DataUtility.getLong(request.getParameter("studentid")));
		  dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		   dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		   dto.setMaths(DataUtility.getInt(request.getParameter("math")));
		   
		   populateDTO(dto, request);
		   
		   
		   return dto;
		 
		 
	 }
		
       
	 /**
		 * Display logic inside it
		 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("marksheet ctl doget  start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (id > 0) {
			MarksheetDTO dto;
			try {
				dto = model.fingByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl doget  end");
		
	}

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("marksheet ctl dopost  start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("--------"+id);
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateBean(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					System.out.println("in post method.........." + dto.getRollno()+"...."+dto.getStudentid());
					
					long pk = model.add(dto);
					
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateBean(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				System.out.println("in catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("marksheet ctl dopost  end");
	
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW;
	}

}
