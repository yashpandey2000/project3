package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.FacultyDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.CollegeModelInt;
import in.co.rays.project3.model.CourseModelInt;
import in.co.rays.project3.model.FacultyModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;



/**
 *  Faculty list ctl is to perform search and show list operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })

public class FacultyListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(FacultyListCtl.class);
	
	protected void preload(HttpServletRequest request) {
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		CourseModelInt model1=ModelFactory.getInstance().getCourseModel();
		try {
			List list=model.list();
			List list1=model1.list();
			request.setAttribute("collegeList", list);
			request.setAttribute("courseList", list1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	
	protected BaseDTO populateBean(HttpServletRequest request) {

		FacultyDTO dto = new FacultyDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFirstname(DataUtility.getString(request.getParameter("fname")));
		dto.setLastname(DataUtility.getString(request.getParameter("lname")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setEmailid(DataUtility.getString(request.getParameter("loginid")));
		dto.setQualification(DataUtility.getString(request.getParameter("qual")));
		dto.setMobileno(DataUtility.getString(request.getParameter("mobile")));
		dto.setCollegeid(DataUtility.getInt(request.getParameter("collegeid")));
		
		//dto.setCollegename(DataUtility.getString(request.getParameter("collegename")));
		dto.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
		
		//dto.setCoursename(DataUtility.getString(request.getParameter("coursename")));
		dto.setSubjectid(DataUtility.getInt(request.getParameter("subjectid")));
		//dto.setSubjectname(DataUtility.getString(request.getParameter("subjectname")));

		populateDTO(dto, request);

		return dto;

	}
       
	/**
	 * Display logic inside it
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("Faculty Ctl do get start");
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		FacultyDTO bean = (FacultyDTO) populateBean(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
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
			e.printStackTrace();
		}

		log.debug("Faculty Ctl do get end");
		
	}

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("Faculty Ctl do post start");
		List list;
		List next;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		String op = DataUtility.getString(request.getParameter("operation"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
		FacultyDTO dto = (FacultyDTO) populateBean(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		String[] ids = request.getParameterValues("ids");
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if ("Next".equalsIgnoreCase(op)) {
					pageNo++;
				} else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				System.out.println("kiljjj");
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println("helloooo"+ids);
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					FacultyDTO deleteBean = new FacultyDTO();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getLong(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("select at least one record", request);
				}
			}
			dto = (FacultyDTO) populateBean(request);
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("NO Record Found", request);

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
			e.printStackTrace();
		}
		log.debug("Faculty Ctl do post end");
		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_LIST_VIEW;
	}

}
