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
import in.co.rays.project3.dto.RoleDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.RoleModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;




/**
 * Role list ctl is to perform search and show list operation
 * @author Yash Pandey
 *
 */
@WebServlet(name="RoleListCtl",urlPatterns={"/ctl/RoleListCtl"})
public class RoleListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger(RoleListCtl.class);
	
	protected void preload(HttpServletRequest request) {
		RoleModelInt model=ModelFactory.getInstance().getRoleModel();
		try {
			List list=model.list();
			request.setAttribute("roleList", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	 protected BaseDTO populateBean(HttpServletRequest request){
		 
		 
		 RoleDTO dto = new RoleDTO();
			dto.setId(DataUtility.getLong(request.getParameter("name")));
			populateDTO(dto,request);
			//bean.setName(DataUtility.getString(request.getParameter("name")));
	      
			return dto;
		 
		 
	 }
	 
	 
	 /**
		 * Display logic inside it
		 */
	 
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		log.debug("RoleListCtl doGet Start");
		List list = null;
		List next = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		RoleDTO dto = (RoleDTO) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
			list = model.search(dto, pageNo, pageSize);
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
		}
		log.debug("RoleListCtl doGet End");
		
	}

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		log.debug("RoleListCtl doPost Start");
		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
		RoleDTO dto = (RoleDTO) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();

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
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					RoleDTO deletebean = new RoleDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			dto = (RoleDTO) populateBean(request);
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
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
		}
		log.debug("RoleListCtl doPost End");
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_LIST_VIEW;
	}

}
