package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.util.Iterable;

import in.co.rays.project3.dto.CourseDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.CourseModelHibImp;
import in.co.rays.project3.model.CourseModelInt;

public class CourseModelTest {
	
	public static CourseModelInt model = new CourseModelHibImp();
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
		testadd();
		//testupdate();
		//testdelete();
		//testfindbypk();
		//testfindbyname();
		//testsearch();
		//testlist();
	}

	

	private static void testlist() throws ApplicationException {
	
		CourseDTO dto = new CourseDTO();
		List list = new ArrayList();
		list = model.list(0, 0);
		Iterator it = list.iterator();
		while(it.hasNext()){
		    dto = 	(CourseDTO) it.next();
		    System.out.println(dto.getId());
			System.out.println("\t"+dto.getCoursename());
			System.out.println("\t"+dto.getDuration());
			System.out.println("\t"+dto.getDescription());
			System.out.println("\t"+dto.getCreatedby());
			System.out.println("\t"+dto.getModifiedby());
			System.out.println("\t"+dto.getCreateddatetime());
			System.out.println("\t"+dto.getModifieddatetime());
			
		    
			
		}
		
	}



	private static void testsearch() throws ApplicationException {
		
		CourseDTO d = new CourseDTO();
		List<CourseDTO> list = model.search(d, 0, 0);
		for (CourseDTO dto : list) {
			
			System.out.println(dto.getId());
			System.out.println("\t"+dto.getCoursename());
			System.out.println("\t"+dto.getDuration());
			System.out.println("\t"+dto.getDescription());
			System.out.println("\t"+dto.getCreatedby());
			System.out.println("\t"+dto.getModifiedby());
			System.out.println("\t"+dto.getCreateddatetime());
			System.out.println("\t"+dto.getModifieddatetime());
			
			
		}
		
		
		
		
	}



	private static void testfindbyname() throws ApplicationException {
		CourseDTO dto = model.findByName("btech");
		System.out.println(dto.getId());
		System.out.println("\t"+dto.getCoursename());
		System.out.println("\t"+dto.getDuration());
		System.out.println("\t"+dto.getDescription());
		System.out.println("\t"+dto.getCreatedby());
		System.out.println("\t"+dto.getModifiedby());
		System.out.println("\t"+dto.getCreateddatetime());
		System.out.println("\t"+dto.getModifieddatetime());
		
	}



	private static void testfindbypk() throws ApplicationException {
		CourseDTO dto = model.findByPK(2l);
		System.out.println(dto.getId());
		System.out.println("\t"+dto.getCoursename());
		System.out.println("\t"+dto.getDuration());
		System.out.println("\t"+dto.getDescription());
		System.out.println("\t"+dto.getCreatedby());
		System.out.println("\t"+dto.getModifiedby());
		System.out.println("\t"+dto.getCreateddatetime());
		System.out.println("\t"+dto.getModifieddatetime());
		
	}



	private static void testdelete() throws ApplicationException {
		CourseDTO dto = new CourseDTO();
		dto.setId(2l);
		model.delete(dto);
		
	}

	private static void testupdate() throws ApplicationException, DuplicateRecordException {
		CourseDTO dto   = new CourseDTO();
		dto.setId(1l);
		
		dto.setCoursename("MTECH");
		dto.setDuration("2yrs");
		dto.setDescription("dnvkldnkb");
		dto.setCreatedby("student");
		dto.setModifiedby("student");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		model.update(dto);
	}

	private static void testadd() throws ApplicationException, DuplicateRecordException {
		CourseDTO dto = new CourseDTO();
		dto.setId(3l);
		dto.setCoursename("BTECH");
		dto.setDuration("4 year");
		dto.setDescription("nerjgju");
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		model.add(dto);
	}

}
