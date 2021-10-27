package in.co.rays.project3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.CourseDTO;
import in.co.rays.project3.dto.SubjectDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;



/**
 * Hibernate implements of subject model
 * @author Yash Pandey
 *
 */
public class SubjectModelHibImp implements SubjectModelInt{

	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;
		Session session = null;
		Transaction tx = null;
		
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO cDto = cModel.findByPK(dto.getCourseid());
		dto.setCoursename(cDto.getCoursename());
		
		   SubjectDTO duplicataSub = findByName(dto.getSubjectname());
	    // Check if create Subject already exist
	        if (duplicataSub!= null && duplicataSub.getSubjectname()!=null) {
	        throw new DuplicateRecordException("Subject already exists");
	        }
	        
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in subject Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(SubjectDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in subject Delete" + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO cDto = cModel.findByPK(dto.getCourseid());
		dto.setCoursename(cDto.getCoursename());
		
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in subject update" + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public List list() throws ApplicationException {
		
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
	
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			if(pageSize>0){
				pageNo=((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
				
			}
			list=criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  subject list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(SubjectDTO dto) throws ApplicationException {
		
		return search(dto, 0 , 0);
	}

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			if(dto!=null){
			if(dto.getId()>0){
				criteria.add(Restrictions.eq("id", dto.getId()));
				
			}
			
			if(dto.getCourseid()>0){
				criteria.add(Restrictions.eq("courseid", dto.getCourseid()));
			}
			if(dto.getCoursename()!=null&& dto.getCoursename().length()>0){
				criteria.add(Restrictions.like("coursename", dto.getCoursename()+"%"));
			}
			if(dto.getSubjectname()!=null&& dto.getSubjectname().length()>0){
				criteria.add(Restrictions.like("subjectname", dto.getSubjectname()+"%"));
			}}
			if(pageSize>0){
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  subject search");
		} finally {
			session.close();
		}
		return list;
	}

	public SubjectDTO findByPK(long pk) throws ApplicationException {
		Session session=null;
		SubjectDTO dto=null;
		try {
			System.out.println(pk +"------inside subject");
			session=HibDataSource.getSession();
			dto=(SubjectDTO) session.get(SubjectDTO.class, pk);
			
		} catch (HibernateException e) {
            
            throw new ApplicationException(
                    "Exception : Exception in getting subject by pk");
        } finally {
            session.close();
        }
		System.out.println(dto);
		return dto;
		
	}

	public SubjectDTO findByName(String name) throws ApplicationException {
		
		Session session=null;
		SubjectDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("subjectname", name));
			List list=criteria.list();
			if(list.size()==1){
				dto=(SubjectDTO) list.get(0);
			}
		} catch (HibernateException e) {
            
            throw new ApplicationException(
                    "Exception in getting subject by Login " + e.getMessage());

        } finally {
            session.close();
        }
		return dto;
	}
	
}

