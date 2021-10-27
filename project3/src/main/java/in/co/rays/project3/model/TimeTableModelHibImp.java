package in.co.rays.project3.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.CourseDTO;
import in.co.rays.project3.dto.SubjectDTO;
import in.co.rays.project3.dto.TimeTableDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;




/**
 * Hibernate implements of TimeTable model
 * @author Yash Pandey
 *
 */
public class TimeTableModelHibImp implements TimeTableModelInt {

	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO Cbean = null;
		Cbean = Cmodel.findByPK(dto.getCourseid());
		dto.setCoursename(Cbean.getCoursename());

		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = Smodel.findByPK(dto.getSubjectid());
		System.out.println(Sbean);
		dto.setSubjectname(Sbean.getSubjectname());

		Session session = null;
		Transaction tx = null;
		long pk = 0;

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
			throw new ApplicationException("Exception in timetable Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(TimeTableDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Timetable delete " + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO Cbean = null;
		Cbean = Cmodel.findByPK(dto.getCourseid());
		dto.setCoursename(Cbean.getCoursename());

		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = Smodel.findByPK(dto.getSubjectid());
		dto.setSubjectname(Sbean.getSubjectname());
		
		Session session = null;
		Transaction tx = null;
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
			throw new ApplicationException("Exception in timetable update " + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  TimetableDTO list");
		} finally {
			session.close();
		}
		return list;
		
		
	}

	public List search(TimeTableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			if(dto!=null){
			if (dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getCoursename() != null && dto.getCoursename().length() > 0) {
				criteria.add(Restrictions.like(" coursename ", dto.getCoursename() + "%"));
			}
			if (dto.getSubjectname() != null && dto.getSubjectname().length() > 0) {
				criteria.add(Restrictions.like("subjectname", dto.getSubjectname() + "%"));
			}
			if (dto.getSemester() != null && dto.getSemester().length() > 0) {
				criteria.add(Restrictions.like("semester", dto.getSemester() + "%"));
			}
			if (dto.getExamdate() != null && dto.getExamdate().getDate() > 0) {
				criteria.add(Restrictions.eq("examdate", dto.getExamdate()));
			}
			if (dto.getSubjectid() > 0) {
				criteria.add(Restrictions.eq("subjectid", dto.getSubjectid()));
			}
			if (dto.getCourseid() > 0) {
				criteria.add(Restrictions.eq("courseid", dto.getCourseid()));
			}}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
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

	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (TimeTableDTO) session.get(TimeTableDTO.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	public TimeTableDTO checkByCourseName(long courseId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.and(Restrictions.eq("courseid", courseId), Restrictions.eq("examdate", date)));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
		
		
	}

	public TimeTableDTO checkBySubjectName(long courseId, long subjectId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		Session session = null;
		TimeTableDTO dto = null;
		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseid", courseId));
			dis.add(Restrictions.eq("subjectid", subjectId));
			dis.add(Restrictions.eq("examdate", date));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by subjectname");
		} finally {
			session.close();
		}
		return dto;
		
	}

	public TimeTableDTO checkBysemester(long courseId, long subjectId, String semester, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		
		
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseid", courseId));
			dis.add(Restrictions.eq("subjectid", subjectId));
			dis.add(Restrictions.like("semester", semester));
			dis.add(Restrictions.eq("examdate", date ));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
		} finally {
			session.close();
		}
		return dto;
		
		
	}

}
