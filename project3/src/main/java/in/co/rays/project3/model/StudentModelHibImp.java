package in.co.rays.project3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.dto.StudentDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;



/**
 * Hibernate implements of Student model
 * @author Yash Pandey
 *
 */
public class StudentModelHibImp implements StudentModelInt {

	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO cdto = model.findByPK(dto.getCollegeid());
		dto.setCollegename(cdto.getName());
		  
		  StudentDTO duplicateName = findByEmailId(dto.getEmailid());
		  if (duplicateName != null) { 
			  throw new DuplicateRecordException("Emailid already exists"); 
			  }

		
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Student Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
		
	}

	public void delete(StudentDTO dto) throws ApplicationException {
		
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
			throw new ApplicationException("Exception in Student Delete" + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO cdto = model.findByPK(dto.getCollegeid());
		dto.setCollegename(cdto.getName());
		
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
				throw new ApplicationException("Exception in Student Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		
	}

	public List list() throws ApplicationException {
		
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		 Session session = null;
	        List list = null;
	        try {
	            session = HibDataSource.getSession();
	            Criteria criteria = session.createCriteria(StudentDTO.class);

	            // if page size is greater than zero then apply pagination
	            if (pageSize > 0) {
	                pageNo = ((pageNo - 1) * pageSize) + 1;
	                criteria.setFirstResult(pageNo);
	                criteria.setMaxResults(pageSize);
	            }

	            list = criteria.list();
	        } catch (HibernateException e) {
	           
	            throw new ApplicationException(
	                    "Exception : Exception in  Student list");
	        } finally {
	            session.close();
	        }

		
		return list;
	}

	public List search(StudentDTO dto) throws ApplicationException {
		
		return search(dto, 0, 0);
	}

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(StudentDTO.class);
           if(dto!=null){
            if (dto.getId()>0 ) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }
            if (dto.getFirstname() != null && dto.getFirstname().length() > 0) {
                criteria.add(Restrictions.like("firstname", dto.getFirstname() + "%"));
            }
            if (dto.getEmailid() != null && dto.getEmailid().length() > 0) {
                criteria.add(Restrictions.like("emailid", dto.getEmailid()
                        + "%"));
            }
            if (dto.getCollegename() != null && dto.getCollegename().length() > 0) {
                criteria.add(Restrictions.like("lastname", dto.getCollegename()
                        + "%"));
            }
            if (dto.getDob() != null && dto.getDob().getDate() > 0) {
                criteria.add(Restrictions.eq("dob", dto.getDob()));
            }
            if (dto.getCollegeid() >0 ) {
                criteria.add(Restrictions.eq("collegeid", dto.getCollegeid()));
            }
            
            if (dto.getMobileno() != null && dto.getMobileno().length() > 0) {
                criteria.add(Restrictions.like("mobileno", dto.getMobileno()
                        + "%"));
            }
           }
            // if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (HibernateException e) {
           
            throw new ApplicationException("Exception in Student search");
        } finally {
            session.close();
        }

       return list;		
	}

	public StudentDTO findByPK(long pk) throws ApplicationException {
	
		Session session = HibDataSource.getSession();
		StudentDTO dto = null;
		try {
			dto = (StudentDTO) session.get(StudentDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting Student by pk");
		} finally {
			session.close();
		}
		return dto;
		 
	}

	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		
		Session session=HibDataSource.getSession();
		StudentDTO dto=null;
		try {
			Criteria criteria=session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("emailid", emailId));
			List list=criteria.list();
			if(list.size()==1){
				dto=(StudentDTO) list.get(0);
			}
		} catch (HibernateException e) {
           
            throw new ApplicationException(
                    "Exception in getting Student by email " + e.getMessage());

        } finally {
            session.close();
        }
		
		
		return dto;
	}

}
