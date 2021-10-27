package in.co.rays.project3.model;

import java.util.List;

import in.co.rays.project3.dto.FacultyDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;



/**
 * Interface of Faculty model
 * @author Yash Pandey
 *
 */
public interface FacultyModelInt {
	
	public long add(FacultyDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(FacultyDTO dto)throws ApplicationException;
	public void update(FacultyDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(FacultyDTO dto)throws ApplicationException;
	public List search(FacultyDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public FacultyDTO findByPK(long pk)throws ApplicationException;
	public FacultyDTO  findByEmailId(String emailId)throws ApplicationException;

}
