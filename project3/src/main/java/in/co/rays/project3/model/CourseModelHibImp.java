package in.co.rays.project3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project3.dto.CourseDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;


/**
 * Hibernate implements of course model
 * @author Yash Pandey
 *
 */
public class CourseModelHibImp implements CourseModelInt{

	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		CourseDTO existDto = findByName(dto.getCoursename());
		
		if (existDto != null) {
			throw new DuplicateRecordException("Course name already exist");
		}
		try{
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			if(tx!=null){
				tx.rollback();
			}
			throw new ApplicationException("Exception in course add"+e.getMessage());
		}finally {
			session.close();
		}
		
		return pk;
	}

	public void delete(CourseDTO dto) throws ApplicationException {
		Session session =null;
		Transaction tx = null;
		try{
		session =  HibDataSource.getSession();
		tx = session.beginTransaction();
		session.delete(dto);
        tx.commit();
		
		}catch(HibernateException e){
			e.printStackTrace();
			if(tx!=null){
				tx.rollback();
			}
			
			throw new ApplicationException("Exception in course delete"+e.getMessage());
		}finally {
			session.close();
		}
		
	}

	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		}catch(HibernateException e){
			e.printStackTrace();
			if(tx!=null){
				tx.rollback();
			}
			throw new ApplicationException("Exception in course update");
		}finally {
			session.close();
		}
		
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try{
			
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			if(pageSize>0){
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
		list = criteria.list();
		}catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : exception in course list");
		}
		return list;
	}

	public List search(CourseDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try{
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			if(dto.getId()>0){
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getCoursename()!=null&& dto.getCoursename().length()>0){
				criteria.add(Restrictions.like("coursename", dto.getCoursename()+"%"));
			}
			if(dto.getDuration()!=null&&dto.getDuration().length()>0){
				criteria.add(Restrictions.like("duration",dto.getDuration()+"%"));
			}
			if(dto.getDescription()!=null && dto.getDescription().length()>0){
				criteria.add(Restrictions.like("description",dto.getDescription()+"%"));
			}
			
			if(pageSize>0){
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}catch(HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in search course");
		}finally {
			session.close();
		}
		return list;
	}
	
	public CourseDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		CourseDTO dto = null;
		try{
			session = HibDataSource.getSession();
			dto = (CourseDTO) session.get(CourseDTO.class, pk);
			
		}catch(HibernateException e){
			throw new ApplicationException("Exception : Exception in getting course by pk");
		}finally {
			session.close();
		}
		return dto;
	}

	public CourseDTO findByName(String name) throws ApplicationException {
		Session session = null;
		CourseDTO dto = null;
		try{
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("coursename", name));
			List list  = criteria.list();
			if(list.size()>0){
				dto = (CourseDTO) list.get(0);
			}
		}catch(HibernateException e){
			throw new ApplicationException("exception :exception in getting course by name "+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}


	

}
