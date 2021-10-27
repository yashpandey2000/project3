package in.co.rays.project3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.UserDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.exception.RecordNotFoundException;
import in.co.rays.project3.util.EmailBuilder;
import in.co.rays.project3.util.EmailMessage;
import in.co.rays.project3.util.EmailUtility;
import in.co.rays.project3.util.HibDataSource;



/**
 * Hibernate implements of User model
 * @author Yash Pandey
 *
 */
public class UserModelHibImp implements UserModelInt {

	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		System.out.println("1234");
		UserDTO existDto = null;
		existDto = findByLogin(dto.getLoginid());
		if (existDto != null) {
			throw new DuplicateRecordException("login id already exist");
		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
            session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		System.out.println("56789");
		/* log.debug("Model add End"); */
		return dto.getId();
		
	}

	public void delete(UserDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
		
	}

	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		
		Session session = null;
		Transaction tx = null;
		UserDTO existDto = findByLogin(dto.getLoginid());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public UserDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public UserDTO findByLogin(String login) throws ApplicationException {
		
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("loginid", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (UserDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
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
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
		
	}

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<UserDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getFirstname() != null && dto.getFirstname().length() > 0) {
					criteria.add(Restrictions.like("firstname", dto.getFirstname() + "%"));
				}

				if (dto.getLastname() != null && dto.getLastname().length() > 0) {
					criteria.add(Restrictions.like("lastname", dto.getLastname() + "%"));
				}
				if (dto.getLoginid() != null && dto.getLoginid().length() > 0) {
					criteria.add(Restrictions.like("loginid", dto.getLoginid() + "%"));
				}
				if (dto.getPassword() != null && dto.getPassword().length() > 0) {
					criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
				}
				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getDob() != null && dto.getDob().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
//				if (dto.getLastlogin() != null && dto.getLastlogin().getTime() > 0) {
//					criteria.add(Restrictions.eq("lastLogin", dto.getLastlogin()));
//				}
				if (dto.getRoleid() > 0) {
					criteria.add(Restrictions.eq("roleid", dto.getRoleid()));
				}
//				if (dto.getUnsuccessfullogin() > 0) {
//					criteria.add(Restrictions.eq("unSuccessfulLogin", dto.getUnsuccessfullogin()));
//				}
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<UserDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		return list;
		
	}

	public List search(UserDTO dto) throws ApplicationException {
		
		
		return search(dto, 0, 0);
	}

	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		boolean flag = false;
		UserDTO dtoExist = null;

		dtoExist = findByPK(id);
		System.out.println("in method password" + dtoExist.getPassword() + "jjjjjjj" + oldPassword);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (DuplicateRecordException e) {

				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", dtoExist.getLoginid());
		map.put("password", dtoExist.getPassword());
		map.put("firstName", dtoExist.getFirstname());
		map.put("lastName", dtoExist.getLastname());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getLoginid());
		msg.setSubject("Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;
	}

	public UserDTO authenticate(String login, String password) throws ApplicationException {
		
		Session session = null;
		UserDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from UserDTO where login_id=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserDTO userData = findByLogin(login);
		boolean flag = false;
		System.out.println("i am forget password method" + userData);
		if (userData == null) {
			throw new RecordNotFoundException("Email Id Does not matched.");

		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLoginid());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstname());
		map.put("lastName", userData.getLastname());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;
		
	}

	public boolean resetPassword(UserDTO dto) throws ApplicationException, RecordNotFoundException {
		
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLoginid());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLoginid());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLoginid());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLoginid());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
