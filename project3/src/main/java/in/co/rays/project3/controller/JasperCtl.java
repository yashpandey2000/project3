package in.co.rays.project3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.impl.SessionImpl;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import in.co.rays.project3.dto.UserDTO;
import in.co.rays.project3.util.HibDataSource;
import in.co.rays.project3.util.JDBCDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;



/**
 * Jasper functionality Controller. Performs operation for Print pdf of
 * MarksheetMeriteList
 *
 * @author Yash Pandey
 *
 */
@WebServlet(name = "JasperCtl", urlPatterns = { "/ctl/JasperCtl" })
public class JasperCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
   
	/**
	 * Display logic inside it
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Users\\Yash Pandey\\OneDrive\\Desktop\\html\\workspace neon\\project3\\src\\main\\webapp\\jasper\\Blank_A4.jrxml");


		HttpSession session = request.getSession(true);
		UserDTO dto = (UserDTO) session.getAttribute("user");
		dto.getFirstname();
		dto.getLastname();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", 1l);
		java.sql.Connection conn = null;

		ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project3.bundle.system");

		String Database = rb.getString("DATABASE");
		if ("Hibernate".equalsIgnoreCase(Database)) {
			System.out.println("y============================");
		conn = ((SessionImpl) HibDataSource.getSession()).connection();
		System.out.println("A============================");
		}

		if ("JDBC".equalsIgnoreCase(Database)) {
		conn = JDBCDataSource.getConnection();
		}

		/*Filling data into the report */
		System.out.println("S============================");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);

		System.out.println("H============================");
		/* Export Jasper report*/
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		response.setContentType("application/pdf");
		response.getOutputStream().write(pdf);
		response.getOutputStream().flush();
		} catch (Exception e) {

		}
}

	
	
	/**
	 * Submit logic inside it
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		
		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return null;
	}

}
