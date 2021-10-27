package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.RoleDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.RoleModelHibImp;
import in.co.rays.project3.model.RoleModelInt;

public class RoleModelTest {

	private static RoleModelInt model = new RoleModelHibImp();
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		//testadd();
		//testdelete();
		//testupdate();
		testfindbypk();
	    // testfindbyname();
		//testsearch();
		//testlist();
	}

	private static void testlist() throws ApplicationException {
		// TODO Auto-generated method stub
		List list=model.list(0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()){
			RoleDTO dto=(RoleDTO) it.next();
			System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getDescription()+"\t"+dto.getCreatedby()+"\t"+dto.getCreateddatetime());
		}
		
		
	}

	private static void testsearch() throws ApplicationException {
		// TODO Auto-generated method stub
		RoleDTO dto1=new RoleDTO();
		List<RoleDTO> a=(List<RoleDTO>) model.search(dto1, 0, 0);
		for(RoleDTO dto: a){
			System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getDescription());
		}
		
	}

	private static void testfindbyname() throws ApplicationException {
		// TODO Auto-generated method stub
		RoleDTO dto=model.findByName("admin");
		System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getDescription());
	}

	private static void testfindbypk() throws ApplicationException {
		// TODO Auto-generated method stub
		RoleDTO dto=model.findByPK(1l);
		System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getDescription());
	}

	private static void testupdate() throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		RoleDTO dto=new RoleDTO();
		dto.setId(1L);
		dto.setName("admin");
		dto.setDescription("how r u student");
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		
		
	}

	private static void testdelete() throws ApplicationException {
		// TODO Auto-generated method stub
		RoleDTO dto=new RoleDTO();
		dto.setId(1L);
		model.delete(dto);
	}

	private static void testadd() throws ApplicationException, DuplicateRecordException {
		
		RoleDTO dto=new RoleDTO();
		dto.setName("hello");
		dto.setDescription("how r u");
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		System.out.println("ROle ADd");
		model.add(dto);
	}
}
