package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.MarksheetDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.MarksheetModelHibImp;
import in.co.rays.project3.model.MarksheetModelInt;

public class MarksheetModelTest {
	
	public static MarksheetModelInt model = new MarksheetModelHibImp();
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
	     //testadd();
		//testdelete();
		//testupdate();
		//testfindbypk();
		//testfindbyrollno();
		testlist();
		//listsearch();
		//testgetmeritlist();
	}

	private static void testgetmeritlist() throws ApplicationException {
		
		MarksheetDTO dto = null;
		List list = new ArrayList();
		list = model.getMeritList(0, 0);
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (MarksheetDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollno());
			System.out.print("\t" + dto.getStudentid());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
		
	}

	private static void listsearch() throws ApplicationException {
		MarksheetDTO dto1 = new MarksheetDTO();
		ArrayList<MarksheetDTO> a = (ArrayList<MarksheetDTO>) model.search(dto1, 0, 0);
		for (MarksheetDTO dto : a) {

			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollno());
			System.out.print("\t" + dto.getStudentid());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}		
	}

	private static void testlist() throws ApplicationException {
		MarksheetDTO dto = null;
		List list = new ArrayList();
		list = model.list(0, 0);
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (MarksheetDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollno());
			System.out.print("\t" + dto.getStudentid());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
		
	}

	private static void testfindbyrollno() throws ApplicationException {
		MarksheetDTO dto =  model.findByRollNo("102");
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getRollno());
		System.out.print("\t" + dto.getStudentid());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getPhysics());
		System.out.print("\t" + dto.getChemistry());
		System.out.println("\t" + dto.getMaths());
		
		
	}

	private static void testfindbypk() throws ApplicationException {
		
		MarksheetDTO dto = model.fingByPK(1L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getRollno());
		System.out.print("\t" + dto.getStudentid());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getPhysics());
		System.out.print("\t" + dto.getChemistry());
		System.out.println("\t" + dto.getMaths());
		
	}

	

	private static void testupdate() throws ApplicationException, DuplicateRecordException {
		
		MarksheetDTO dto = new MarksheetDTO();
		dto.setId(1L);
		dto.setChemistry(50);
		dto.setMaths(60);
		dto.setRollno("101");
		dto.setStudentid(2l);
		dto.setPhysics(25);
		dto.setCreatedby("Admin");
		dto.setModifiedby("Admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
	}

	private static void testdelete() throws ApplicationException {
		MarksheetDTO dto = new MarksheetDTO();
		dto.setId(1L);
		model.delete(dto);
		
	}

	private static void testadd() throws ApplicationException, DuplicateRecordException {
		MarksheetDTO dto = new MarksheetDTO();
		dto.setChemistry(79);
		dto.setMaths(80);
		dto.setRollno("105");
		dto.setStudentid(2);
		dto.setPhysics(85);
		dto.setCreatedby("Admin");
		dto.setModifiedby("Admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.add(dto);
		
	}

}
