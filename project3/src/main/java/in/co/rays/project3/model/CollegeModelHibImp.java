package in.co.rays.project3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;



/**
 * Hibernate implements of college model
 * @author Yash Pandey
 *
 */
public class CollegeModelHibImp implements CollegeModelInt {

	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null; 
        CollegeDTO duplicatecollegename = findByName(dto.getName());
		
		if(duplicatecollegename!=null){
			throw new DuplicateRecordException("College Name Already Exist");
		}
		try{
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			if(tx!=null){
				tx.rollback();
			}
			throw new ApplicationException("Exception in college add"+e.getMessage());	
		}finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(CollegeDTO dto) throws ApplicationException {
		Session session = null;
	    Transaction tx = null;
	    try{
	    	session = HibDataSource.getSession();
	    	tx = session.beginTransaction();
	    	session.delete(dto);
	    	tx.commit();
	    	
	    }catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			throw new ApplicationException("Exception in college delete"+e.getMessage());
		}finally {
			session.close();
		}
		
	}

	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		
		try{
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			if(tx!=null){
				tx.rollback();
			}
			
			throw new ApplicationException("Exception in college update"+e.getMessage());
		}finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		List list = null;
		Session session = null;
		try{
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if(pageSize>0){
				pageNo = ((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in college list");
		}finally {
			session.close();
		}
		return list;
	}

	public List search(CollegeDTO dto) throws ApplicationException {
		
		return search(dto, 0, 0);
	}

	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list =null;
		try{
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if(dto.getId()>0){
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getName()!=null && dto.getName().length()>0){
				criteria.add(Restrictions.like("name", dto.getName()+"%"));
			}
			if(dto.getAddress()!=null && dto.getAddress().length()>0){
				criteria.add(Restrictions.like("address", dto.getAddress()+"%"));
			}
			if(dto.getState()!=null && dto.getState().length()>0){
				criteria.add(Restrictions.like("state", dto.getState()+"%"));
			}
			if(dto.getCity()!=null && dto.getCity().length()>0){
				criteria.add(Restrictions.like("city", dto.getCity()+"%"));
			}
			
			if(pageSize>0){
				criteria.setFirstResult((pageNo-1)*pageSize); 
				criteria.setMaxResults(pageSize);
			}
			
			list = criteria.list();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in college search");
		}finally {
			session.clear();
		}
		return list;
	}

	public CollegeDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		CollegeDTO dto =  null;
		try{
			session = HibDataSource.getSession();
			dto = (CollegeDTO) session.get(CollegeDTO.class, pk);
		}catch(Exception e){
			throw new ApplicationException("Exception: Exception in getting college by pk ");
		}finally {
			session.close();
		}
		return dto;
	}

	public CollegeDTO findByName(String name) throws ApplicationException {
		Session session = null;
		CollegeDTO dto = null;
		try{
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list =  criteria.list();
			if(list.size()==1){
				dto = (CollegeDTO) list.get(0);
			}
			
		}catch(HibernateException e){
			throw new ApplicationException("Exception in getting college"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
