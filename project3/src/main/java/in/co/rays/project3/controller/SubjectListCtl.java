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
import in.co.rays.project3.dto.SubjectDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.CourseModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.SubjectModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;





/**
 * Subject list ctl is to perform search and show list operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(SubjectListCtl.class);
    
	protected void preload(HttpServletRequest request) {
		
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			List list = model.list();
			request.setAttribute("courseList", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	
	protected BaseDTO populateBean(HttpServletRequest request) {
		
		log.debug("subject ctl populate bean start");
		SubjectDTO dto = new SubjectDTO();
		
		dto.setCourseid(DataUtility.getLong(request.getParameter("courseid")));
		
		dto.setSubjectname(DataUtility.getString(request.getParameter("name")));
		
		populateDTO(dto,request);
		
		return dto;
			
		
	}
	
	/**
	 * Display logic inside it
	 */
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("subject ctl do get start");
		List list =null;;
		List next=null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		SubjectDTO dto = (SubjectDTO) populateBean(request);
		SubjectModelInt model =ModelFactory.getInstance().getSubjectModel();
		try {
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo+1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}if (next == null || next.size() == 0) {
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
		log.debug("subject ctl do get end");
	}

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("subject ctl dopost start");
		List list=null;
		List next=null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
		String[] ids = request.getParameterValues("ids");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		SubjectDTO dto = (SubjectDTO) populateBean(request);
		SubjectModelInt model =ModelFactory.getInstance().getSubjectModel();
        try{
        	if(OP_SEARCH.equalsIgnoreCase(op)||"Next".equalsIgnoreCase(op)||"Previous".equalsIgnoreCase(op)){
        		if(OP_SEARCH.equalsIgnoreCase(op)){
        			pageNo=1;
        		}else if("Next".equalsIgnoreCase(op)){
        			pageNo++;
        		}else if("Previous".equalsIgnoreCase(op)){
        			pageNo--;
        		}
        	}
        	else if (OP_RESET.equalsIgnoreCase(op)) {

    			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
    			return;
    		}else if(OP_NEW.equalsIgnoreCase(op)){
        		ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
        		return;
        	}else if(OP_DELETE.equalsIgnoreCase(op)){
        		pageNo=1;
        		
        		if(ids !=null&& ids.length>0){
        			System.out.println("kjkjk____");
        			SubjectDTO deleteBean=new SubjectDTO();
        			for(String id:ids){
        				deleteBean.setId(DataUtility.getLong(id));
        				model.delete(deleteBean);
        				ServletUtility.setSuccessMessage("Data Delete Successfully", request);
        			}
        		}else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
        	}
        	 if(OP_BACK.equalsIgnoreCase(op)){
        		 System.out.println("jijijij");
        		ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
        		return;
        	}
        	 dto = (SubjectDTO) populateBean(request);
        	list = model.search(dto, pageNo, pageSize);
        	ServletUtility.setDto(dto, request);
        	next = model.search(dto, pageNo+1, pageSize);
			ServletUtility.setList(list, request);
			
			if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println("last endpopopop"+list);
				ServletUtility.setErrorMessage("No record found ", request);
			}if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		}
        catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		log.debug("subject ctl do post end");
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_LIST_VIEW ;
	}

}
