package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.UserDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.RoleModelInt;
import in.co.rays.project3.model.UserModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;



/**
 * User list ctl is to perform search and show list operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(UserListCtl.class);
	
	protected void preload(HttpServletRequest request) {
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
		List list = model.list();
		request.setAttribute("roleList", list);
		} catch (Exception e) {
		log.error(e);

		}
		}
   
	
	protected BaseDTO populateBean(HttpServletRequest request){
		
		UserDTO dto = new UserDTO();

		dto.setFirstname(DataUtility.getString(request.getParameter("firstname")));

		dto.setLastname(DataUtility.getString(request.getParameter("lastname")));

		dto.setLoginid(DataUtility.getString(request.getParameter("loginid")));
		
		dto.setRoleid(DataUtility.getLong(request.getParameter("roleid")));
		
		//System.out.println(request.getParameter("roleid")+",,---------------------------,,-----------");
		
		populateDTO(dto,request);
		return dto;
		
		
	}

	
	/**
	 * Display logic inside it
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("UserListCtl doGet Start");
		List list =null;
		List next =null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		System.out.println("==========" + pageSize);
		UserDTO dto = (UserDTO) populateBean(request);
		
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		try {
		System.out.println("in ctllllllllll search");
		list = model.search(dto, pageNo, pageSize);
					
		 ArrayList<UserDTO> a = (ArrayList<UserDTO>) list;
					  
					
		next = model.search(dto, pageNo + 1, pageSize);
		ServletUtility.setList(list, request);
		if (list == null || list.size() == 0) {
		ServletUtility.setErrorMessage("No record found ", request);
		}
		if (next == null || next.size() == 0) {
		request.setAttribute("nextListSize", 0);

		} else {
		request.setAttribute("nextListSize", next.size());
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
		log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		log.debug("UserListCtl doPOst End");
	
	}

	
	/**
	 * Submit logic inside it
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("UserListCtl doPost Start");
		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
		UserDTO dto = (UserDTO) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("op---->" + op);

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		try {

		if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

		if (OP_SEARCH.equalsIgnoreCase(op)) {
		pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
		pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
		pageNo--;
		}

		} else if (OP_NEW.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.USER_CTL, request, response);
		return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
		return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
		pageNo = 1;
		if (ids != null && ids.length > 0) {
		UserDTO deletedto = new UserDTO();
		for (String id : ids) {
		deletedto.setId(DataUtility.getLong(id));
		model.delete(deletedto);
		ServletUtility.setSuccessMessage("Data Delete Successfully", request);
		}
		} else {
		ServletUtility.setErrorMessage("Select at least one record", request);
		}
		}
		if (OP_BACK.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
		return;
		}
		dto = (UserDTO) populateBean(request);
		//System.out.println("y yyyyyyyyyy"+dto.getRoleid());

		list = model.search(dto, pageNo, pageSize);

		ServletUtility.setDto(dto, request);
		next = model.search(dto, pageNo + 1, pageSize);

		ServletUtility.setList(list, request);
		ServletUtility.setList(list, request);
		if (list == null || list.size() == 0) {
		if (!OP_DELETE.equalsIgnoreCase(op)) {
		ServletUtility.setErrorMessage("No record found ", request);
		}
		}
		if (next == null || next.size() == 0) {
		request.setAttribute("nextListSize", 0);

		} else {
		request.setAttribute("nextListSize", next.size());
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
		log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		log.debug("UserListCtl doGet End");
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_LIST_VIEW;
	}

}
