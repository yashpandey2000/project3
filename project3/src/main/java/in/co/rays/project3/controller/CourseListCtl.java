package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.CourseDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.CourseModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;



/**
 *  course list ctl is to perform search and show list operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger("CourseListCtl.class");
	   
	protected void preload(HttpServletRequest request){
		CourseModelInt model=ModelFactory.getInstance().getCourseModel();
		try{
			List list=model.list();
			request.setAttribute("courseList", list);
		}catch(Exception e){
			log.error(e);
		}
	}
	
	protected BaseDTO populateBean(HttpServletRequest request) {
		CourseDTO dto = new CourseDTO();
		dto.setId(DataUtility.getLong(request.getParameter("course")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));
		populateDTO(dto,request);
		return dto;

	}
	
	 /**
     * Display logic inside it
     */
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("Course ctl do get start");
		List list=null;
		List next=null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		CourseDTO dto = (CourseDTO) populateBean(request);
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);
			//System.out.println("<>>><<<>>>>+"+list);
			next = model.search(dto, pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
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
			e.printStackTrace();
		}

		log.debug("Course ctl do get end");
		
		
	}

	 /**
     * Submit logic inside it
     */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		log.debug("Course List do post start");
		List list=null;
		List next=null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
		CourseDTO dto = (CourseDTO) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		CourseModelInt model =ModelFactory.getInstance().getCourseModel();
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
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					CourseDTO deletebean = new CourseDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}

				} else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}
			dto = (CourseDTO) populateBean(request);
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			 next = model.search(dto, pageNo + 1, pageSize);
			 ServletUtility.setList(list, request);
			if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
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
		log.debug("Course List do post end");
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_LIST_VIEW;
	}

}
