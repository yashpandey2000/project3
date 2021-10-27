package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.CollegeModelHibImp;
import in.co.rays.project3.model.CollegeModelInt;

public class CollegeModelTest {
	
	public static CollegeModelInt model = new CollegeModelHibImp();

	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		testadd();
		//testfindByName();
		//testlist();
		//testsearch();
		//testfindbypk();
		//testdelete();
		//testupdate(); 
	}


	private static void testsearch() throws ApplicationException {
		CollegeDTO dto = new CollegeDTO();
		ArrayList<CollegeDTO> list = (ArrayList<CollegeDTO>) model.search(dto, 0, 0);
		for (CollegeDTO ll : list) {
			System.out.println(ll.getId());
			System.out.println("\t"+ll.getName());
			System.out.println("\t"+ll.getAddress());
			System.out.println("\t"+ll.getState());
			System.out.println("\t"+ll.getCity());
			System.out.println("\t"+ll.getPhoneno());
			System.out.println("\t"+ll.getCreatedby());
			System.out.println("\t"+ll.getModifiedby());
			System.out.println("\t"+ll.getCreateddatetime());
			System.out.println("\t"+ll.getModifieddatetime());
			
		}
	}


	private static void testupdate() throws ApplicationException, DuplicateRecordException {
		CollegeDTO dto = new CollegeDTO();
		 dto.setId(1L);
	     dto.setName("svvv");
	     dto.setAddress("pune");
	     dto.setState("mp");
	     dto.setCity("indore");
	     dto.setPhoneno("073124244");
	     dto.setCreatedby("Admin");
	     dto.setModifiedby("Admin");
	     dto.setCreateddatetime(new Timestamp(new Date().getTime()));
	     dto.setModifieddatetime(new Timestamp(new Date().getTime()));
         model.update(dto);
	}


	private static void testdelete() throws ApplicationException {
		CollegeDTO dto = new CollegeDTO();
		dto.setId(2l);
		model.delete(dto);
	}


	private static void testfindbypk() throws ApplicationException {
		CollegeDTO dto = model.findByPK(2l);
		System.out.println(dto.getId());
		System.out.println(dto.getName());
		System.out.println(dto.getAddress());
		System.out.println(dto.getState());
		System.out.println(dto.getCity());
		System.out.println(dto.getPhoneno());
		System.out.println(dto.getCreatedby());
		System.out.println(dto.getModifiedby());
		System.out.println(dto.getCreateddatetime());
		System.out.println(dto.getModifieddatetime());
	}


	private static void testlist() throws ApplicationException {
		CollegeDTO dto = null;
		List list = new ArrayList();
		list = model.list(0,0);
		Iterator<CollegeDTO> it = list.iterator();
		while(it.hasNext()){
			 dto = it.next();
			 System.out.println(dto.getId());
			 System.out.println("\t"+dto.getName());
			 System.out.println("\t"+dto.getAddress());
			 System.out.println("\t"+dto.getState());
			 System.out.println("\t"+dto.getCity());
			 System.out.println("\t"+dto.getPhoneno());
			 System.out.println("\t"+dto.getCreatedby());
			 System.out.println("\t"+dto.getModifiedby());
			 System.out.println("\t"+dto.getCreateddatetime());
			 System.out.println("\t"+dto.getModifieddatetime());
			 
		}
		
	}


	private static void testfindByName() throws ApplicationException {
		CollegeDTO dto  = model.findByName("sage");
		System.out.println(dto.getId());
		System.out.println(dto.getName());
		System.out.println(dto.getAddress());
	System.out.println(dto.getState());
	System.out.println(dto.getCity());
	System.out.println(dto.getPhoneno());
	System.out.println(dto.getCreatedby());
	System.out.println(dto.getModifiedby());
	System.out.println(dto.getCreateddatetime());
	System.out.println(dto.getModifieddatetime());
		
		
		}


	private static void testadd() throws ApplicationException, DuplicateRecordException {
		CollegeDTO dto = new CollegeDTO();
		dto.setId(2l);
		dto.setName("prestige");
		dto.setAddress("indore");
		dto.setState("m.p.");
		dto.setCity("indore");
		dto.setPhoneno("6264649746");
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		long pk = model.add(dto);
	}
}
