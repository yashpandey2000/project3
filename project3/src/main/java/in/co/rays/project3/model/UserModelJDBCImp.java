package in.co.rays.project3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.UserDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.exception.RecordNotFoundException;
import in.co.rays.project3.util.EmailBuilder;
import in.co.rays.project3.util.EmailMessage;
import in.co.rays.project3.util.EmailUtility;
import in.co.rays.project3.util.JDBCDataSource;



/**
 * JDBC implements of User model
 * @author Yash Pandey
 *
 */
public class UserModelJDBCImp implements UserModelInt{

	private static Logger log = Logger.getLogger(UserModelJDBCImp.class);
	/**
	* find pk
	* @return pk
	* @throws DatabaseException
	*/
	public long nextPK() throws DatabaseException {
		log.debug("user pk start");
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_USER");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
				System.out.println(pk);
			}
		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Database Exception" + e);

		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("user pk is end");
		return pk = pk + 1;

	}

	
	/**
	* add new college
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	
	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
	
		log.debug("user add is started");
		Connection con = null;
		long pk = 0;
		UserDTO existDto = null;
		existDto = findByLogin(dto.getLoginid());
		if (existDto != null) {
			throw new DuplicateRecordException("login id already exist");
		}
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			pk = nextPK();
			
			PreparedStatement ps = con
					.prepareStatement("insert into ST_USER values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getFirstname());
			ps.setString(3, dto.getLastname());
			ps.setString(4, dto.getGender());
			ps.setLong(5, dto.getRoleid());
			ps.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			ps.setString(7, dto.getMobileno());
			ps.setString(8, dto.getLoginid());
			ps.setString(9, dto.getPassword());
//			ps.setTimestamp(10, dto.getLastlogin());
//			ps.setInt(11, dto.getUnsuccessfullogin());
//			ps.setString(12, dto.getLastloginip());
//			ps.setString(13, dto.getRegisteredip());
			ps.setString(10, dto.getCreatedby());
			ps.setString(11, dto.getModifiedby());
			ps.setTimestamp(12, dto.getCreateddatetime());
			ps.setTimestamp(13, dto.getModifieddatetime());
			ps.executeUpdate();
			
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();

			} catch (Exception e2) {
				log.error(e);
				e2.printStackTrace();
				e.printStackTrace();
				throw new ApplicationException("exception: add rollback exception:" + e2.getMessage());

			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("user model add is ended");
		return pk;
		
	}

	
	
	/**
	* delete college information
	* @param b
	* @throws DatabaseException
	*/
	public void delete(UserDTO dto) throws ApplicationException {
		
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from ST_USER where id=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");
		
	}

	
	/**
	* update college detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
	
		Connection con = null;
		PreparedStatement ps = null;
		UserDTO dtoExist = findByLogin(dto.getLoginid());
		// Check if updated LoginId already exist
		if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(
					"update ST_USER set FIRST_NAME=?,LAST_NAME=?,GENDER=?,ROLE_ID=?,DOB=?,,MOBILE_NO=?,LOGIN_id =?,PASSWORD=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			
			ps.setString(1, dto.getFirstname());
			ps.setString(2, dto.getLastname());
			ps.setString(3, dto.getGender());
			ps.setLong(4, dto.getRoleid());
			ps.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			ps.setString(6, dto.getMobileno());
			ps.setString(7, dto.getLoginid());
			ps.setString(8, dto.getPassword());
//			ps.setTimestamp(9, dto.getLastlogin());
//			ps.setInt(10, dto.getUnsuccessfullogin());
//			ps.setString(11, dto.getLastloginip());
//			ps.setString(12, dto.getRegisteredip());
			ps.setString(9, dto.getCreatedby());
			ps.setString(10, dto.getModifiedby());
			ps.setTimestamp(11, dto.getCreateddatetime());
			ps.setTimestamp(12, dto.getModifieddatetime());
			ps.setLong(13, dto.getId());
			
			ps.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model update End");
		
	}
	
	
	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/

	public UserDTO findByPK(long pk) throws ApplicationException {
		
		Connection con = null;
		PreparedStatement ps = null;
		UserDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select * from ST_USER where id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleid(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileno(rs.getString(7));
				dto.setLoginid(rs.getString(8));
				dto.setPassword(rs.getString(9));
//				dto.setLastlogin(rs.getTimestamp(10));	
//				dto.setUnsuccessfullogin(rs.getInt(11));	
//				dto.setLastloginip(rs.getString(12));
//				dto.setRegisteredip(rs.getString(13));
				dto.setCreatedby(rs.getString(10));
				dto.setModifiedby(rs.getString(11));
				dto.setCreateddatetime(rs.getTimestamp(12));
				dto.setModifieddatetime(rs.getTimestamp(13));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model findByPK End");
		return dto;
	}
	
	/**
	* find the information with the help of login
	* @param login
	* @return bean
	* @throws ApplicationException
	*/

	public UserDTO findByLogin(String login) throws ApplicationException {
		
		Connection con = null;
		PreparedStatement ps = null;
		UserDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select * from ST_USER where LOGIN_ID=?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleid(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileno(rs.getString(7));
				dto.setLoginid(rs.getString(8));
				dto.setPassword(rs.getString(9));
//				dto.setLastlogin(rs.getTimestamp(10));	
//				dto.setUnsuccessfullogin(rs.getInt(11));	
//				dto.setLastloginip(rs.getString(12));
//				dto.setRegisteredip(rs.getString(13));
				dto.setCreatedby(rs.getString(10));
				dto.setModifiedby(rs.getString(11));
				dto.setCreateddatetime(rs.getTimestamp(12));
				dto.setModifieddatetime(rs.getTimestamp(13));
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model findByLogin End");

		return dto;
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	
	/**
	* to show list of college
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList list = null;
		UserDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where 1=1");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleid(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileno(rs.getString(7));
				dto.setLoginid(rs.getString(8));
				dto.setPassword(rs.getString(9));
//				dto.setLastlogin(rs.getTimestamp(10));	
//				dto.setUnsuccessfullogin(rs.getInt(11));	
//				dto.setLastloginip(rs.getString(12));
//				dto.setRegisteredip(rs.getString(13));
				dto.setCreatedby(rs.getString(10));
				dto.setModifiedby(rs.getString(11));
				dto.setCreateddatetime(rs.getTimestamp(12));
				dto.setModifieddatetime(rs.getTimestamp(13));
				
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model list End");
		
		return list;
	}

	
	/**
	* search college information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
	
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList list = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getFirstname() != null && dto.getFirstname().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + dto.getFirstname() + "%'");
			}
			if (dto.getLastname() != null && dto.getLastname().length() > 0) {
				sql.append(" AND LASTNAME like '" + dto.getLastname() + "%'");
			}
			if (dto.getLoginid() != null && dto.getLoginid().length() > 0) {
				sql.append(" AND LOGIN like '" + dto.getLoginid() + "%'");
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + dto.getPassword() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getGender());
			}
			if (dto.getMobileno() != null && dto.getMobileno().length() > 0) {
				sql.append(" AND MOBILENO = " + dto.getMobileno());
			}
			if (dto.getRoleid() > 0) {
				sql.append(" AND ROLEID = " + dto.getRoleid());
			}
//			if (dto.getUnsuccessfullogin() > 0) {
//				sql.append(" AND UNSUCCESSFULLOGIN = " + dto.getUnsuccessfullogin());
//			}
			if (dto.getGender() != null && dto.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + dto.getGender() + "%'");
			}
//			if (dto.getLastlogin() != null && dto.getLastlogin().getTime() > 0) {
//				sql.append(" AND LASTLOGIN = " + dto.getLastlogin());
//			}
//			if (dto.getRegisteredip() != null && dto.getRegisteredip().length() > 0) {
//				sql.append(" AND REGISTEREDIP like '" + dto.getRegisteredip() + "%'");
//			}
//			if (dto.getLastloginip() != null && dto.getLastloginip().length() > 0) {
//				sql.append(" AND LOGINIP like '" + dto.getLastloginip() + "%'");
//			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}
		list = new ArrayList();
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleid(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileno(rs.getString(7));
				dto.setLoginid(rs.getString(8));
				dto.setPassword(rs.getString(9));
//				dto.setLastlogin(rs.getTimestamp(10));	
//				dto.setUnsuccessfullogin(rs.getInt(11));	
//				dto.setLastloginip(rs.getString(12));
//				dto.setRegisteredip(rs.getString(13));
				dto.setCreatedby(rs.getString(10));
				dto.setModifiedby(rs.getString(11));
				dto.setCreateddatetime(rs.getTimestamp(12));
				dto.setModifieddatetime(rs.getTimestamp(13));
				
				
				list.add(dto);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return list;

		
	}

	public List search(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException {
		
		log.debug("model changePassword Started");
        boolean flag = false;
        UserDTO beanExist = null;

        beanExist = findByPK(id);
        if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
            beanExist.setPassword(newPassword);
            try {
                update(beanExist);
            } catch (DuplicateRecordException e) {
            log.error(e);
                throw new ApplicationException("LoginId is already exist");
            }
            
            flag = true;
            
        	} else {
            throw new RecordNotFoundException("Loginid not exist");
        	}

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("login", beanExist.getLoginid());
        map.put("password", beanExist.getPassword());
        map.put("firstName", beanExist.getFirstname());
        map.put("lastName", beanExist.getLastname());

        String message = EmailBuilder.getChangePasswordMessage(map);

        EmailMessage msg = new EmailMessage();

        msg.setTo(beanExist.getLoginid());
        msg.setSubject("Rays Ors Password has been changed Successfully.");
        msg.setMessage(message);
        msg.setMessageType(EmailMessage.HTML_MSG);

        EmailUtility.sendMail(msg);

        log.debug("Model changePassword End");
        return flag;

	}

	public UserDTO authenticate(String login, String password) throws ApplicationException {
		
		log.debug("user model authenticate method start");
		UserDTO dto = null;
		Connection con = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where login_id=? and password=?");
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleid(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileno(rs.getString(7));
				dto.setLoginid(rs.getString(8));
				dto.setPassword(rs.getString(9));
//				dto.setLastlogin(rs.getTimestamp(10));	
//				dto.setUnsuccessfullogin(rs.getInt(11));	
//				dto.setLastloginip(rs.getString(12));
//				dto.setRegisteredip(rs.getString(13));
				dto.setCreatedby(rs.getString(10));
				dto.setModifiedby(rs.getString(11));
				dto.setCreateddatetime(rs.getTimestamp(12));
				dto.setModifieddatetime(rs.getTimestamp(13));
				
				

			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug(" user Model authenticate End");
		return dto;
	}

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		
		log.debug("model forgetpassword started");
		boolean flag =false;
		UserDTO userData = findByLogin(login);
		
		if(userData == null){
		
			throw new RecordNotFoundException("email does not exist");
		}
		
		flag = true;
			
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("login" , userData.getLoginid());
			map.put("password", userData.getPassword());
			map.put("firstName", userData.getFirstname());
			map.put("lastName", userData.getLastname());
			
			String message = EmailBuilder.getForgetPasswordMessage(map);
			
			EmailMessage msg = new EmailMessage();
			msg.setTo(login);
			msg.setSubject("ors Forget password reset");
			msg.setMessage(message);
			msg.setMessageType(EmailMessage.HTML_MSG);
			
			EmailUtility.sendMail(msg);
			
			log.debug("model forgetpassword ended");
		return flag;	
	}

	public boolean resetPassword(UserDTO dto) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("model registeruser started");
		long pk = add(dto);
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("login", dto.getLoginid());
		map.put("password",dto.getPassword());
		
		String message = EmailBuilder.getUserRegistrationMessage(map);
		
		EmailMessage msg = new EmailMessage();
		
		msg.setTo(dto.getLoginid());
		msg.setSubject("registration is successful for ORS project");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		
		EmailUtility.sendMail(msg);
		
		log.debug("model registeruser ended");
		return pk ;
	}

	public List getRoles(UserDTO dto) throws ApplicationException {
		
		log.debug("Model roles started");
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		StringBuffer sql = new StringBuffer("select * from st_user where role_id=?");
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, dto.getRoleid());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
			dto=new UserDTO();
			
			dto.setId(rs.getInt(1));
			dto.setFirstname(rs.getString(2));
			dto.setLastname(rs.getString(3));
			dto.setLoginid(rs.getString(4));
			
			dto.setPassword(rs.getString(5));
			
			dto.setDob(rs.getDate(6));
			dto.setMobileno(rs.getString(7));
			dto.setRoleid(rs.getLong(8));
			
			dto.setGender(rs.getString(9));

			dto.setCreatedby(rs.getString(10));
			dto.setModifiedby(rs.getString(11));
			dto.setCreateddatetime(rs.getTimestamp(12));
			dto.setModifieddatetime(rs.getTimestamp(13));
				
				list.add(dto);		
			}	
			rs.close();
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:exception in getting role");
		}
		log.debug("Model Role end");
			return list;	
	}

}
