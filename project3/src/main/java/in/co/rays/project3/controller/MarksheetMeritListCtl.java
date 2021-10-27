package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project3.dto.MarksheetDTO;
import in.co.rays.project3.model.MarksheetModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;

/**
 *  marksheetmerit list functionlity controller to show merit list student
 * @author Yash Pandey
 *
 */
@WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})

public class MarksheetMeritListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(MarksheetMeritListCtl.class);

	/**
	 * Display logic inside it
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("Marksheet merit list do get  start");
		System.out.println("Inside merit list get");
		List list;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetDTO dto = (MarksheetDTO) populateBean(request);
		MarksheetModelInt model =ModelFactory.getInstance().getMarksheetModel();
		try {
			list = model.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) 
		{
			log.error(e);
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
		}
		log.debug("Marksheet merit list do get  end");

		
		
	}

	
	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		if(OP_BACK.equalsIgnoreCase(op)){
			
			ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
		}
		log.debug("dopost end");
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}
