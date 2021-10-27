package in.co.rays.project3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.dto.CourseDTO;
import in.co.rays.project3.dto.FacultyDTO;
import in.co.rays.project3.dto.SubjectDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;



/**
 * Hibernate implements of Faculty model
 * @author Yash Pandey
 *
 */
public class FacultyModelHibImp implements FacultyModelInt{

	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO dto1 = model.findByPK(dto.getCollegeid());
		String CollegeName = dto1.getName();
        dto.setCollegename(CollegeName);
        
		CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		CourseDTO dto2 = model1.findByPK(dto.getCourseid());
		String CourseName = dto2.getCoursename();
		dto.setCoursename(CourseName);
		
		SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
		System.out.println(model2+"   "+ dto.getSubjectid());
		SubjectDTO dto3 = model2.findByPK(dto.getSubjectid());
		System.out.println(dto3+"-----");
		String SubjectName = dto3.getSubjectname();
		System.out.println(SubjectName);
		dto.setSubjectname(SubjectName);
		
		FacultyDTO duplicataRole = findByEmailId(dto.getEmailid());
		
				if (duplicataRole != null) {
					throw new DuplicateRecordException("Faculty already exists");
				}
				
				try {
					session = HibDataSource.getSession();
					tx = session.beginTransaction();
					session.save(dto);
					pk = dto.getId();
					tx.commit();
				} catch (HibernateException e) {
					e.printStackTrace();
					// TODO: handle exception
					if (tx != null) {
						tx.rollback();

					}
					throw new ApplicationException("Exception in faculty Add " + e.getMessage());
				} finally {
					session.close();
				}
				return pk;
	}

	public void delete(FacultyDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in faculty delete " + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO dto1 = model.findByPK(dto.getCollegeid());
		String CollegeName = dto1.getName();
        dto.setCollegename(CollegeName);
        
		CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		CourseDTO dto2 = model1.findByPK(dto.getCourseid());
		String CourseName = dto2.getCoursename();
		dto.setCoursename(CourseName);
		
		SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO dto3 = model2.findByPK(dto.getSubjectid());
		String SubjectName = dto3.getSubjectname();
		dto.setSubjectname(SubjectName);
		
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in faculty update " + e.getMessage());
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
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  faculty list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(FacultyDTO dto) throws ApplicationException {
		
		return search(dto, 0, 0);
	}

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
	
		Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FacultyDTO.class);
          if(dto!=null){
            if (dto.getId() >0) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }
            if (dto.getFirstname() != null && dto.getFirstname().length() > 0) {
                criteria.add(Restrictions.like("firstname", dto.getFirstname() + "%"));
            }
            if (dto.getEmailid() != null && dto.getEmailid().length() > 0) {
                criteria.add(Restrictions.like("emailid", dto.getEmailid()
                        + "%"));
            }
            if (dto.getLastname() != null && dto.getLastname().length() > 0) {
                criteria.add(Restrictions.like("lastname", dto.getLastname() + "%"));
            }
            if (dto.getCollegeid() > 0) {
                criteria.add(Restrictions.eq("collegeid", dto.getCollegeid()));
            }
            if (dto.getCourseid() > 0) {
                criteria.add(Restrictions.eq("courseid", dto.getCourseid()));
            }
            if (dto.getSubjectid() > 0) {
                criteria.add(Restrictions.eq("subjectid", dto.getSubjectid()));
            }}

            // if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (HibernateException e) {
            
            throw new ApplicationException("Exception in course search");
        } finally {
            session.close();
        }

       
        return list;
		
	}

	public FacultyDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (FacultyDTO) session.get(FacultyDTO.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting faculty by pk");
		} finally {
			session.close();
		}
		return dto;
		
	}

	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
	
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("emailid", emailId));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (FacultyDTO) list.get(0);
			}
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting faculty by Login " + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
		
	}

}
