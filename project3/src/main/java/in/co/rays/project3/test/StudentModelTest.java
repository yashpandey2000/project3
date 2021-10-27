package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.StudentDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.StudentModelHibImp;
import in.co.rays.project3.model.StudentModelInt;

public class StudentModelTest {
	
	public static StudentModelInt model = new StudentModelHibImp();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		testadd();
		//testdelete();
		//testupdate();
		//testfindbypk();
		//testfindbyemailid();
		//testsearch();
		//testlist();
	}
	
	private static void testlist() throws ApplicationException {
		StudentDTO dto=new StudentDTO();   
        List list = new ArrayList();
        list = model.list(0,0);
        if (list.size() < 0) {
            System.out.println("Test list fail");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            dto = (StudentDTO) it.next();
            System.out.println(dto.getId());
    		System.out.println(dto.getFirstname());
    		System.out.println(dto.getLastname());
    		System.out.println(dto.getDob());
    		System.out.println(dto.getMobileno());
    		System.out.println(dto.getEmailid());
    		System.out.println(dto.getCollegeid());
    		System.out.println(dto.getCollegename());
    		System.out.println(dto.getCreatedby());
    		System.out.println(dto.getModifiedby());
    		System.out.println(dto.getCreateddatetime());
    		System.out.println(dto.getModifieddatetime());
        }
           
	}

	private static void testsearch() throws ApplicationException {
		StudentDTO dto=new StudentDTO();
		
		ArrayList<StudentDTO> a = (ArrayList<StudentDTO>) model.search(dto);
		for (StudentDTO uu : a) {

			System.out.println(uu.getId() + "\t" + uu.getFirstname() + "\t" + uu.getLastname() + "\t" + uu.getDob()
					+ "\t" + uu.getEmailid() + "\t" + uu.getMobileno() + "\t" + uu.getCollegeid() + "\t"
					+ uu.getCollegename());}
		
	}

	private static void testfindbyemailid() throws ApplicationException {
		StudentDTO dto = model.findByEmailId("aaa@gmail.com");
		System.out.println(dto.getId());
		System.out.println(dto.getFirstname());
		System.out.println(dto.getLastname());
		System.out.println(dto.getDob());
		System.out.println(dto.getMobileno());
		System.out.println(dto.getEmailid());
		System.out.println(dto.getCollegeid());
		System.out.println(dto.getCollegename());
		System.out.println(dto.getCreatedby());
		System.out.println(dto.getModifiedby());
		System.out.println(dto.getCreateddatetime());
		System.out.println(dto.getModifieddatetime());
		
		
	}

	private static void testfindbypk() throws ApplicationException {
		StudentDTO dto = model.findByPK(2l);
		
		System.out.println(dto.getId());
		System.out.println(dto.getFirstname());
		System.out.println(dto.getLastname());
		System.out.println(dto.getDob());
		System.out.println(dto.getMobileno());
		System.out.println(dto.getEmailid());
		System.out.println(dto.getCollegeid());
		System.out.println(dto.getCollegename());
		System.out.println(dto.getCreatedby());
		System.out.println(dto.getModifiedby());
		System.out.println(dto.getCreateddatetime());
		System.out.println(dto.getModifieddatetime());
		
		
	}

	private static void testupdate() throws ApplicationException, DuplicateRecordException {
		
		StudentDTO dto = new StudentDTO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try{
		dto.setId(1l);
		dto.setFirstname("naveen");
		 dto.setLastname("dixit");
	        dto.setDob(sdf.parse("1/11/2000"));
	        dto.setMobileno("8542212554");
	        dto.setEmailid("dixit@hg.com");
	        dto.setCollegeid(3L);
	        dto.setCreatedby("Admin");
			dto.setModifiedby("Admin");
			dto.setCreateddatetime(new Timestamp(new Date().getTime()));
			dto.setModifieddatetime(new Timestamp(new Date().getTime()));
			
			model.update(dto);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testdelete() throws ApplicationException {
		
		StudentDTO dto = new StudentDTO();
		dto.setId(1l);
		model.delete(dto);
		
	}
	
	private static void testadd() {
		StudentDTO dto = new StudentDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try{
		dto.setId(1L);
		dto.setFirstname("nayan");
		dto.setLastname("mehta");
		dto.setDob(sdf.parse("18/07/2002"));
		dto.setMobileno("94066262662");
		dto.setEmailid("nayanm@gmail.com");
		dto.setCollegeid(2);
		dto.setCreatedby("Admin");
		dto.setModifiedby("Admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		 model.add(dto);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
