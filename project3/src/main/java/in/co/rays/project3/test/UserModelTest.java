package in.co.rays.project3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project3.dto.UserDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.exception.RecordNotFoundException;
import in.co.rays.project3.model.UserModelHibImp;
import in.co.rays.project3.model.UserModelInt;

public class UserModelTest {

	public static UserModelInt model = new UserModelHibImp();
	
	public static void main(String[] args) throws Exception {
		//addTest();
		//testupdate();
		//testdelete();
		
		//testsearch();
		//testlist();
		
		//testfindbypk();
	    //testfindbyloginid();
		
		//testauthenticate();
		//testchangepass(); //**
		
		//testregisteruser();
		//testforgetpassword();
		
		
	}
	
	

	private static void testforgetpassword() throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		 model.forgetPassword("keldgdfgash2323@gmail.com");
		System.out.println("is working");
		
	}



	private static void testregisteruser() {
		UserDTO dto = new UserDTO();
		
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			
		dto.setFirstname("ram");
		dto.setLastname("mehta");
		dto.setLoginid("keldgdfgash2323@gmail.com");
		dto.setPassword("1234");
		//bean.setConfirmpassword("1234");
		dto.setDob(sdf.parse("19/5/2016"));
		dto.setGender("male");
		dto.setRoleid(2);
		
		long pk = model.registerUser(dto);
		
			System.out.println("successful register");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private static void testchangepass() throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		 
		UserDTO dto = model.findByLogin("yashpanday2000@gmail.com");
		model.changePassword(1l , "Yash@123", "Yash@2323");
		System.out.println("changed successfully");
	}


	private static void testauthenticate() {
		
try{
			
			UserDTO dto = new UserDTO();
			dto.setLoginid("keldgdfgash2323@gmail.com");
			dto.setPassword("1234");
			dto =model.authenticate(dto.getLoginid(),dto.getPassword());
			if(dto!=null){
				
				System.out.println("login successful");
			}
			else{
				
				System.out.println("invalid loginid & password");
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}


	private static void testfindbyloginid() throws ApplicationException {
		
		UserDTO dto = model.findByLogin("yashpanday2000@gmail.com");
		System.out.println(dto.getId() + "\t" + dto.getFirstname() + "\t" + dto.getLastname() + "\t" + dto.getLoginid()
		+ "\t" + dto.getPassword() + "\t" + dto.getDob() + "\t" + dto.getMobileno() + "\t" + dto.getRoleid()
		+ "\t" + dto.getGender());
		
	}



	private static void testfindbypk() throws ApplicationException {
	
		UserDTO dto = model.findByPK(1L);
		System.out.println(dto.getId() + "\t" + dto.getFirstname() + "\t" + dto.getLastname() + "\t" + dto.getLoginid()
				+ "\t" + dto.getPassword() + "\t" + dto.getDob() + "\t" + dto.getMobileno() + "\t" + dto.getRoleid()
				+ "\t" + dto.getGender());
		
	}


	private static void testlist() throws ApplicationException {
		
		UserDTO dto = new UserDTO();
		List list = new ArrayList();
		list = model.list(1, 10);
		if (list.size() < 0) {
			System.out.println("list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (UserDTO) it.next();
			System.out.println(dto.getId());
			System.out.println(dto.getFirstname());
			System.out.println(dto.getLastname());
			System.out.println(dto.getLoginid());
			System.out.println(dto.getPassword());
			System.out.println(dto.getDob());
			System.out.println(dto.getRoleid());
			System.out.println(dto.getGender());
			System.out.println(dto.getMobileno());
			System.out.println(dto.getCreatedby());
			System.out.println(dto.getModifiedby());
			System.out.println(dto.getCreateddatetime());
			System.out.println(dto.getModifieddatetime());
		}
		
	}


	private static void testsearch() throws ApplicationException {
		
		UserDTO dto1 = new UserDTO();
	       dto1.setId(1L);
		
	        
			ArrayList<UserDTO> a = (ArrayList<UserDTO>) model.search(dto1,0,0);
			
			for (UserDTO dto : a) {
				System.out.println(dto.getId() + "\t" + dto.getFirstname() + "\t" + dto.getLastname() + "\t" + dto.getLoginid()
				+ "\t" + dto.getPassword() + "\t" + dto.getDob() + "\t" + dto.getMobileno() + "\t" + dto.getRoleid()
				+ "\t" + dto.getGender());
				
			
			}
		
	}


	private static void testdelete() throws ApplicationException {
		UserDTO dto = new UserDTO();
		dto.setId(1L);
		model.delete(dto);
		System.out.println("delete data successfully");
		
	}


	private static void testupdate() throws ApplicationException, DuplicateRecordException, ParseException {
		UserDTO dto = new UserDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dto.setId(1L);
		dto.setFirstname("yash");
		
		dto.setLastname("pandey");
		dto.setDob(sdf.parse("17/06/2000"));
		
		dto.setPassword("Yash@123");
		dto.setLoginid("yashpanday2000@gmail.com");
		dto.setGender("male");
		
		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setRoleid(1);
		dto.setMobileno("9406653787");
		
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("data update successfully");
		
		
	}


	public static void addTest() throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("heellloooooo");
		UserDTO dto = new UserDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		dto.setFirstname("Mayank");
		dto.setLastname("mishra");
		dto.setDob(sdf.parse("30/05/1995"));
		
		dto.setPassword("1234");
		dto.setLoginid("Mayankspp@gmail.com");
		dto.setGender("male");
		

		dto.setCreatedby("admin");
		dto.setModifiedby("admin");
		dto.setRoleid(1);
		dto.setMobileno("9406653787");
		
		dto.setCreateddatetime(new Timestamp(new Date().getTime()));
		dto.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		
		 long pk = model.add(dto); 
		System.out.println(pk + "data successfully insert"); 
	}

}
