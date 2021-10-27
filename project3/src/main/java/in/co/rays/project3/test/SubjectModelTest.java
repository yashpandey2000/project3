package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.SubjectDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.SubjectModelHibImp;
import in.co.rays.project3.model.SubjectModelInt;

public class SubjectModelTest {
	
	private static SubjectModelInt model = new SubjectModelHibImp();

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		// testadd();
	    // testdelete();
		//testupdate();
		//testfindbyname();
		//testfindbypk();
		//testsearch();
		//testlist();
		
		
	}

	private static void testlist() throws ApplicationException {
		SubjectDTO dto = new SubjectDTO();
		List list = new ArrayList();
		list = model.list(0, 0);

		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (SubjectDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectname());
			System.out.print("\t" + dto.getCourseid());
			System.out.print("\t" + dto.getCoursename());
			System.out.print("\t" + dto.getDescription());
			System.out.print("\t"+dto.getCreatedby());
			System.out.print("\t"+dto.getModifiedby());
			System.out.print("\t"+dto.getCreateddatetime());
			System.out.print("\t"+dto.getModifieddatetime());
		}
		
	}

	private static void testsearch() throws ApplicationException {
		SubjectDTO dto1 = new SubjectDTO();
		ArrayList<SubjectDTO> a = (ArrayList<SubjectDTO>) model.search(dto1, 0, 0);
		for (SubjectDTO dto : a) {
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectname());
			System.out.print("\t" + dto.getCourseid());
			System.out.print("\t" + dto.getCoursename());
			System.out.print("\t" + dto.getDescription());
			System.out.print("\t"+dto.getCreatedby());
			System.out.print("\t"+dto.getModifiedby());
			System.out.print("\t"+dto.getCreateddatetime());
			System.out.print("\t"+dto.getModifieddatetime());
			
		}
		
	}

	private static void testfindbypk() throws ApplicationException {
		SubjectDTO dto = model.findByPK(1L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectname());
		System.out.print("\t" + dto.getCourseid());
		System.out.print("\t" + dto.getCoursename());
		System.out.print("\t" + dto.getDescription());
		System.out.print("\t"+ dto.getCreatedby());
		System.out.print("\t"+ dto.getModifiedby());
		System.out.print("\t"+ dto.getCreateddatetime());
		System.out.print("\t"+ dto.getModifieddatetime());
		
	}

	private static void testfindbyname() throws ApplicationException {
		SubjectDTO dto = model.findByName("science");
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectname());
		System.out.print("\t" + dto.getCourseid());
		System.out.print("\t" + dto.getCoursename());
		System.out.print("\t" + dto.getDescription());
		System.out.print("\t"+dto.getCreatedby());
		System.out.print("\t"+dto.getModifiedby());
		System.out.print("\t"+dto.getCreateddatetime());
		System.out.print("\t"+dto.getModifieddatetime());
		
	}

	private static void testupdate() throws ApplicationException, DuplicateRecordException {
		SubjectDTO dto = new SubjectDTO();
		dto.setId(1L);
		dto.setSubjectname("english");
		dto.setCourseid(2);
		dto.setCoursename("bsc");
		dto.setDescription("hello");
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		
	}

	private static void testdelete() throws ApplicationException {
		// TODO Auto-generated method stub
		
		SubjectDTO dto = new SubjectDTO();
		dto.setId(1l);
		model.delete(dto);
	}

	private static void testadd() throws ApplicationException, DuplicateRecordException {
		SubjectDTO dto = new SubjectDTO();
		dto.setSubjectname("english");
		dto.setCourseid(2);
		dto.setDescription("hey");
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
	    model.add(dto);
		
	}
}