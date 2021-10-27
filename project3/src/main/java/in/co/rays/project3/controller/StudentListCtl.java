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
import in.co.rays.project3.dto.StudentDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.CollegeModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.model.StudentModelInt;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;




/**
 *  Student list ctl is to perform search and show list operation
 * @author Yash Pandey
 *
 */
@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	
	 private static Logger log = Logger.getLogger(StudentListCtl.class);
	    
	    protected void preload(HttpServletRequest request) {
			CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
			try {
				List list=model.list();
				request.setAttribute("collegeList", list);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
   
	    
	    protected BaseDTO populateBean(HttpServletRequest request){
	    	
	    	 StudentDTO dto = new StudentDTO();

	         dto.setFirstname(DataUtility.getString(request.getParameter("firstName")));
	         dto.setLastname(DataUtility.getString(request.getParameter("lastName")));
	         dto.setEmailid(DataUtility.getString(request.getParameter("email")));
	         dto.setCollegeid(DataUtility.getLong(request.getParameter("collegeId")));
	         populateDTO(dto,request);
	       // System.out.println("kkkkkkjjj"+dto.getCollegeid()+"....."+dto.getFirstname()+"''"+dto.getEmailid());
	         return dto;
	    	
	    	
	    	
	    }
	
	    
	    /**
		 *Display logic inside it
		 */
	    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 log.debug("StudentListCtl doGet Start");
	        List list = null;
	        List next=null;
	        int pageNo = 1;

	        int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));

	        StudentDTO dto = (StudentDTO) populateBean(request);

	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        System.out.println(op+"---------------------------------");

	        StudentModelInt model =ModelFactory.getInstance().getStudentModel();
	        try {
	            list = model.search(dto, pageNo, pageSize);
	            next = model.search(dto, pageNo+1, pageSize);
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }if(next==null||next.size()==0){
					request.setAttribute("nextListSize", 0);
					
				}else{
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
	        log.debug("StudentListCtl doGet End");
		
		
		
	}

	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		log.debug("StudentListCtl doPost Start");
        List list = null;
        List next=null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader .getvalue("page.size")) : pageSize;

        StudentDTO dto = (StudentDTO) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        StudentModelInt model =ModelFactory.getInstance().getStudentModel();
         String[] ids=request.getParameterValues("ids");
        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            }else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
				return;
			}else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					StudentDTO deletebean = new StudentDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
            
			 if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			}
			 dto = (StudentDTO) populateBean(request);
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setDto(dto, request);
            next = model.search(dto, pageNo+1, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }if(next==null||next.size()==0){
				request.setAttribute("nextListSize", 0);
				
			}else{
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
        log.debug("StudentListCtl doGet End");
		
		
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_LIST_VIEW;
	}

}
