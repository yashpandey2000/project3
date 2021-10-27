package in.co.rays.project3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project3.util.ServletUtility;


/**
 *  welcome functionality controller.to  show welcome page
 * @author Yash Pandey
 *
 */
@WebServlet(name = "WelcomeCtl", urlPatterns = { "/WelcomeCtl" })
public class WelcomeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(WelcomeCtl.class);
	
	
	/**
	 * Display logic inside it
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("WelcomeCtl Method doGet Started");
		
		ServletUtility.forward(getView(), request, response);
		
		log.debug("WelcomeCtl Method doGet Ended");

	}

	

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.WELCOME_VIEW;
	}

}
