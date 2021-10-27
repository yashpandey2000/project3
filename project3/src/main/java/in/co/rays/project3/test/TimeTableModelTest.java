package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.TimeTableDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.model.TimeTableModelHibImp;
import in.co.rays.project3.model.TimeTableModelInt;

public class TimeTableModelTest {
	
	public static TimeTableModelInt model = new TimeTableModelHibImp();
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException, DatabaseException, ParseException {
		//testadd(); 
		//testdelete();
		//testupdate(); 
		
		//testfindbypk();
		//testlist();
	     //testsearch();
		
		
		//testbycoursename();
		//testbysubjectname();
		//testbysemester();
	}

	private static void testbysemester() throws ApplicationException, DuplicateRecordException, ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		TimeTableDTO dto=model.checkBysemester(1, 1,"4th", sdf.parse("01/12/2019"));
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectid());
		System.out.print("\t" + dto.getSubjectname());
		System.out.print("\t" + dto.getCourseid());
		System.out.print("\t" + dto.getCoursename());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamdate());
		System.out.print("\t" + dto.getExamtime());
		System.out.print("\t" + dto.getCreatedby());
		System.out.print("\t" + dto.getModifieddatetime());
		
		
	}

	private static void testbysubjectname() throws ApplicationException, DuplicateRecordException, ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		TimeTableDTO dto=model.checkBySubjectName(1, 1, sdf.parse("01/12/2019"));
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectid());
		System.out.print("\t" + dto.getSubjectname());
		System.out.print("\t" + dto.getCourseid());
		System.out.print("\t" + dto.getCoursename());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamdate());
		System.out.print("\t" + dto.getExamtime());
		System.out.print("\t" + dto.getCreatedby());
		System.out.print("\t" + dto.getModifieddatetime());
		
		
	}

	
	private static void testbycoursename() throws ApplicationException, DuplicateRecordException, ParseException {
		
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		TimeTableDTO dto=model.checkByCourseName(1,sdf.parse("01/12/2019") );
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectid());
		System.out.print("\t" + dto.getSubjectname());
		System.out.print("\t" + dto.getCourseid());
		System.out.print("\t" + dto.getCoursename());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamdate());
		System.out.print("\t" + dto.getExamtime());
		System.out.print("\t" + dto.getCreatedby());
		System.out.print("\t" + dto.getModifieddatetime());
		
			
}
	 
	

	private static void testsearch() throws ApplicationException {
		TimeTableDTO dto1=new TimeTableDTO();
		
		List<TimeTableDTO> a=(List<TimeTableDTO>) model.search(dto1, 0, 0);
		for(TimeTableDTO dto: a){
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectid());
			System.out.print("\t" + dto.getSubjectname());
			System.out.print("\t" + dto.getCourseid());
			System.out.print("\t" + dto.getCoursename());
			System.out.print("\t" + dto.getSemester());
			System.out.print("\t" + dto.getExamdate());
			System.out.print("\t" + dto.getExamtime());
			System.out.print("\t" + dto.getCreatedby());
			System.out.print("\t" + dto.getModifieddatetime());
		}
		
	}

	private static void testlist() throws ApplicationException {
		
		TimeTableDTO dto=new TimeTableDTO();
		List list=model.list(0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()){
			 dto=(TimeTableDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectid());
			System.out.print("\t" + dto.getSubjectname());
			System.out.print("\t" + dto.getCourseid());
			System.out.print("\t" + dto.getCoursename());
			System.out.print("\t" + dto.getSemester());
			System.out.print("\t" + dto.getExamdate());
			System.out.print("\t" + dto.getExamtime());
			System.out.print("\t" + dto.getCreatedby());
			System.out.print("\t" + dto.getModifieddatetime());
			
		}
		
	}

	private static void testfindbypk() throws ApplicationException {
		TimeTableDTO dto=model.findByPK(1L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectid());
		System.out.print("\t" + dto.getSubjectname());
		System.out.print("\t" + dto.getCourseid());
		System.out.print("\t" + dto.getCoursename());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamdate());
		System.out.print("\t" + dto.getExamtime());
		System.out.print("\t" + dto.getCreatedby());
		System.out.print("\t" + dto.getModifieddatetime());
	}

	private static void testupdate() throws ApplicationException, DuplicateRecordException, DatabaseException, ParseException {
		TimeTableDTO dto=new TimeTableDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dto.setId(1L);
		dto.setSubjectid(2);
		dto.setCourseid(2);
		dto.setSemester("fourth");
		dto.setExamdate(sdf.parse("22/09/2020"));
		dto.setExamtime("4hr");
		dto.setCreatedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		
		model.update(dto);
		
	}

	private static void testdelete() throws ApplicationException {
		TimeTableDTO dto=new TimeTableDTO();
		dto.setId(1l);
		model.delete(dto);
		
	}

	private static void testadd() {
		TimeTableDTO dto=new TimeTableDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	try{	
		dto.setId(1l);
		dto.setCourseid(1l);
		dto.setSubjectid(1l);
		dto.setSemester("4th");
		dto.setExamtime("12:00-1:00 pm");
		dto.setExamdate(sdf.parse("01/12/2019"));
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.add(dto);
	}catch(Exception e){
		e.printStackTrace();
	}
	}
		

}
