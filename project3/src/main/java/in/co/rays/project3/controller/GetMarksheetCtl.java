package in.co.rays.project3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.MarksheetDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.MarksheetModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;






/**
 * GetMarksheet functionality ctl.to perform  get marksheet opeation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })

public class GetMarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("rollno"))){
			request.setAttribute("rollno", PropertyReader.getvalue("error.require", "Roll No"));
			pass = false;
		}
		/*else if(!DataValidator.isRollNo(request.getParameter("rollno"))){
			request.setAttribute("rollno", "Enter Valid RollNo");
			pass = false;
		}*/
		
		log.debug("validate end");
		return pass;
		
	}
	
	
protected BaseDTO populateBean(HttpServletRequest request){
		
		
	     MarksheetDTO dto = new MarksheetDTO();
	     
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRollno(DataUtility.getString(request.getParameter("rollno")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		dto.setPhysics(DataUtility.getInt(request.getParameter("maths")));
		
		
		return dto;
		}
	
	

/**
 * Display logic inside it
 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("marksheet ctl do get start");
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl do get end");
		
	}

	/**
	 * Submit logic inside it
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("marksheet ctl do post start");
		System.out.println("get marksheet do post <><>>");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		MarksheetDTO dto = (MarksheetDTO) populateBean(request);
		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				dto = model.findByRollNo(dto.getRollno());
				if (dto != null) {
					ServletUtility.setDto(dto, request);
				} else {
					ServletUtility.setErrorMessage("Roll No does not exist", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.equals(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl do post end");
		
	}




	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
