package in.co.rays.project3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project3.util.ServletUtility;



/**
 * Error functionality controller.perform error page operation
 * @author Yash Pandey
 *
 */
@WebServlet(name="ErrorCtl", urlPatterns="/ErrorCtl")
public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Display Logics inside this method
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
		
	}

	

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
		
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ERROR_VIEW;
	}

}
