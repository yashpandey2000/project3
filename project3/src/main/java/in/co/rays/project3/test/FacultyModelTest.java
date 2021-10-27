package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.FacultyDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.FacultyModelHibImp;
import in.co.rays.project3.model.FacultyModelInt;

public class FacultyModelTest {
	private static FacultyModelInt model = new FacultyModelHibImp();

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
		testadd();
		//testdelete();
		//testupdate();  
		//testlist();
		//tetsearch();
		//testfindbypk();
		//testfinbyemail();
		
	}

	private static void testfinbyemail() throws ApplicationException {
		FacultyDTO dto = model.findByEmailId("yash@gmail.com");
		System.out.println(dto.getId() + "\t" + dto.getFirstname() + "\t" + dto.getLastname() + "\t"
				+ dto.getGender() + "\t" + dto.getDateofjoining() + "\t" + dto.getQualification() + "\t"
				+ dto.getMobileno() + "\t" + dto.getEmailid() + "\t" + dto.getCollegeid() + "\t"
				+ dto.getCollegename() + "\t" + dto.getSubjectid() + "\t" + dto.getSubjectname() + "\t"
				+ dto.getCourseid() + "\t" + dto.getCoursename());
		
		
	}


	private static void testfindbypk() throws ApplicationException {
		FacultyDTO dto=model.findByPK(1L);
		System.out.println(dto.getId() + "\t" + dto.getFirstname() + "\t" + dto.getLastname() + "\t"
				+ dto.getGender() + "\t" + dto.getDateofjoining() + "\t" + dto.getQualification() + "\t"
				+ dto.getMobileno() + "\t" + dto.getEmailid() + "\t" + dto.getCollegeid() + "\t"
				+ dto.getCollegename() + "\t" + dto.getSubjectid() + "\t" + dto.getSubjectname() + "\t"
				+ dto.getCourseid() + "\t" + dto.getCoursename());
		
	}

	private static void tetsearch() throws ApplicationException {
FacultyDTO dto1=new FacultyDTO();
				
		ArrayList<FacultyDTO> a = (ArrayList<FacultyDTO>) model.search(dto1);
		for (FacultyDTO dto : a) {
			System.out.println(dto.getId() + "\t" + dto.getFirstname() + "\t" + dto.getLastname() + "\t"
					+ dto.getGender() + "\t" + dto.getDateofjoining() + "\t" + dto.getQualification() + "\t"
					+ dto.getMobileno() + "\t" + dto.getEmailid() + "\t" + dto.getCollegeid() + "\t"
					+ dto.getCollegename() + "\t" + dto.getSubjectid() + "\t" + dto.getSubjectname() + "\t"
					+ dto.getCourseid() + "\t" + dto.getCoursename());

			}
		
	}

	private static void testlist() throws ApplicationException {
		
		FacultyDTO dto=new FacultyDTO();
		 List list = new ArrayList();
	        list = model.list(0,0);
	        if (list.size() < 0) {
	            System.out.println("Test list fail");
	        }
	        Iterator it = list.iterator();
	        while (it.hasNext()) {
	            dto = (FacultyDTO) it.next();
	            System.out.println(dto.getId());
	            System.out.println(dto.getFirstname());
	            System.out.println(dto.getLastname());
	            System.out.println(dto.getDateofjoining());
	            System.out.println(dto.getMobileno());
	            System.out.println(dto.getEmailid());
	            System.out.println(dto.getCollegeid());
	            System.out.println(dto.getCreatedby());
	            System.out.println(dto.getCreateddatetime());
	            System.out.println(dto.getModifiedby());
		
	        }
	}

	private static void testupdate() {
		FacultyDTO dto=new FacultyDTO();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try{
		dto.setId(1l);
		dto.setFirstname("yashuu");
		dto.setLastname("pandey");
		dto.setGender("male");
		dto.setDateofjoining((sdf.parse("17/07/2000")));
		dto.setQualification("btech");
		dto.setMobileno("6464628746");
		dto.setEmailid("yash@gmail.com");
		dto.setCollegeid(2);
		dto.setCourseid(2);
		dto.setSubjectid(2);
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void testdelete() throws ApplicationException {
		FacultyDTO dto = new FacultyDTO();
		dto.setId(2l);
		model.delete(dto);
	}

	private static void testadd() throws ApplicationException, DuplicateRecordException {
		FacultyDTO dto=new FacultyDTO();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try{
		dto.setFirstname("selesh");
		dto.setLastname("sharma");
		dto.setGender("male");
		dto.setDateofjoining((sdf.parse("12/09/2015")));
		dto.setQualification("mcom");
		dto.setEmailid("selesh@gmail.com");
		dto.setMobileno("8945621233");
		dto.setCollegeid(1);
		dto.setCourseid(3);
		dto.setSubjectid(1);
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.add(dto);}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}

