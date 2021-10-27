package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



import in.co.rays.project3.dto.BaseDTO;
import in.co.rays.project3.dto.MarksheetDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.model.MarksheetModelInt;
import in.co.rays.project3.model.ModelFactory;
import in.co.rays.project3.util.DataUtility;
import in.co.rays.project3.util.PropertyReader;
import in.co.rays.project3.util.ServletUtility;


/**
 *marksheet functionality ctl.to show list of marksheet
 * @author Yash Pandey
 *
 */
@WebServlet(name="MarksheetListCtl",urlPatterns= {"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(MarksheetListCtl.class);

	protected void preload(HttpServletRequest request){
		MarksheetModelInt model=ModelFactory.getInstance().getMarksheetModel();
		try {
			List list= model.list();
			request.setAttribute("RollNo", list);
			
		} catch (Exception e) {
			// TODO: handle exception
		}}
	
	 protected BaseDTO populateBean(HttpServletRequest request){
			
		  MarksheetDTO dto = new MarksheetDTO();
	    	
		  dto.setId(DataUtility.getLong(request.getParameter("Id")));
	        dto.setRollno(DataUtility.getString(request.getParameter("rollno")));
	       // dto.setStudentid(DataUtility.getLong(request.getParameter("studentId")));
	        dto.setName(DataUtility.getString(request.getParameter("name")));
	       
	    	
	    	return dto;
	    	
	    	
	    	
	    }
	 
	 
	 /**
		 * Display logic inside it
		 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;

        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getvalue("page.size")) : pageSize;

        MarksheetDTO dto = (MarksheetDTO) populateBean(request);

        List list = null;
        List next = null;
        MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
        try {
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setDto(dto, request);
            next = model.search(dto, pageNo+1, pageSize);
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

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
        log.debug("MarksheetListCtl doGet End");
		
	}
	
	
	/**
	 * Submit logic inside it
	 */

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		 log.debug("MarksheetListCtl doPost Start");

	        List list = null;
	        List next = null;

	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	        pageNo = (pageNo == 0) ? 1 : pageNo;

	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getvalue("page.size")) : pageSize;

	        MarksheetDTO dto = (MarksheetDTO) populateBean(request);

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get the selected checkbox ids array for delete list
	        String[] ids = request.getParameterValues("ids");

	        MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();

	        try {

	            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
	                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                }

	            } else if (OP_NEW.equalsIgnoreCase(op)) {
	                ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
	                        response);
	                return;
	            } else if (OP_RESET.equalsIgnoreCase(op)) {

	    			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
	    			return;
	    		}else if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
					return;
				}else if (OP_DELETE.equalsIgnoreCase(op)) {
	                pageNo = 1;
	                if (ids != null && ids.length > 0) {
	                    MarksheetDTO deletebean = new MarksheetDTO();
	                    for (String id : ids) {
	                        deletebean.setId(DataUtility.getLong(id));
	                        model.delete(deletebean);
	                        ServletUtility.setSuccessMessage("Data Delete Successfully", request);
	                    }
	                } else {
	                    ServletUtility.setErrorMessage(
	                            "Select at least one record", request);
	                }
	            }
	            dto = (MarksheetDTO) populateBean(request);
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

	        log.debug("MarksheetListCtl doPost End");
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
