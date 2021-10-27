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
import in.co.rays.project3.dto.TimeTableDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.CourseModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.SubjectModelInt;
import in.co.rays.project3.model.TimeTableModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.DataValidator;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;



/**
 * TimeTable functionality ctl is to perform add,delete ,update operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt  model1 = ModelFactory.getInstance().getSubjectModel();
		try {
			List l = model.list();
			List l1 = model1.list();
			request.setAttribute("courseList", l);
			request.setAttribute("subjectList", l1);
		} catch (Exception e) {
			log.error(e);
		}
	}
       
	
	public boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("courseid"))){
			request.setAttribute("coursename", PropertyReader.getvalue("error.require", "Course Name"));
		pass = false;
		}
		if(DataValidator.isNull(request.getParameter("subjectid"))){
			request.setAttribute("subjectname", PropertyReader.getvalue("error.require", "Subject Name"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("sem"))){
			request.setAttribute("sem",PropertyReader.getvalue("error.require", "Semester"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("examtime"))){
			request.setAttribute("examtime", PropertyReader.getvalue("error.require", "Examtime"));
		pass = false;
		}
		if(DataValidator.isNull(request.getParameter("examdate"))){
			request.setAttribute("examdate", PropertyReader.getvalue("error.require", "Examdate"));
			pass = false;
		}
		log.debug("validate end");
		return pass;
		
	}
    
	protected BaseDTO populateBean(HttpServletRequest request){
		
		log.debug("time table populate start");
		TimeTableDTO dto = new TimeTableDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseid(DataUtility.getLong(request.getParameter("courseid")));
		dto.setSemester(DataUtility.getString(request.getParameter("sem")));
		dto.setSubjectid(DataUtility.getLong(request.getParameter("subjectid")));
		dto.setExamdate(DataUtility.getDate(request.getParameter("examdate")));
		dto.setExamtime(DataUtility.getString(request.getParameter("examtime")));
		populateDTO(dto,request);
		log.debug("time table populate end");
		
		return dto;
		
		
		
	}
	
	
	/**
	 * Display logic inside it
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("time table do get start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimeTableModelInt model =ModelFactory.getInstance().getTimetableModel() ;
		if (id > 0 || op != null) {
			TimeTableDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table doget end");
		
		
	}
	
	
	
	/**
	 * Submit logic inside it
	 */

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("time table dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimeTableModelInt model = ModelFactory.getInstance().getTimetableModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimeTableDTO dto = (TimeTableDTO) populateBean(request);
			TimeTableDTO dto1 = null;
			TimeTableDTO dto2 = null;
			TimeTableDTO dto3 = null;
			try {
				if (id > 0) {
					 model.update(dto);
					ServletUtility.setDto(dto, request);
					
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					try {
						dto1 = model.checkByCourseName(dto.getCourseid(), dto.getExamdate());
						dto2 = model.checkBySubjectName(dto.getCourseid(), dto.getSubjectid(), dto.getExamdate());
						dto3 = model.checkBysemester(dto.getCourseid(), dto.getSubjectid(), dto.getSemester(),
								dto.getExamdate());
						
						if (dto1 == null && dto2 == null && dto3 == null) {
							
							model.add(dto);
							ServletUtility.setDto(dto, request);
							ServletUtility.setSuccessMessage("Data is successfully saved", request);
						} else {
							ServletUtility.setDto(dto, request);
							ServletUtility.setErrorMessage("Exam already exist!", request);

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				//ServletUtility.setBean(bean, request);

			} catch (Exception e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TimeTableDTO dto = (TimeTableDTO) populateBean(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table dopost end");
		
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}

}
