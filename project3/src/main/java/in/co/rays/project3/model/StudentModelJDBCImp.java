package in.co.rays.project3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.dto.StudentDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.JDBCDataSource;


/**
 * JDBC implements of Student model
 * @author Yash Pandey
 *
 */
public class StudentModelJDBCImp implements StudentModelInt {
	
	private static Logger log = Logger.getLogger(StudentModelJDBCImp.class);
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
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_STUDENT");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
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
	* add new student
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		long pk = 0;
		
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getFirstname());
			pstmt.setString(3, dto.getLastname());
			pstmt.setDate(4, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(5, dto.getMobileno());
			pstmt.setString(6, dto.getEmailid());
			pstmt.setLong(7, dto.getCollegeid());
			pstmt.setString(8, dto.getCollegename());
			pstmt.setString(9, dto.getCreatedby());
			pstmt.setString(10, dto.getModifiedby());
			pstmt.setTimestamp(11, dto.getCreateddatetime());
			pstmt.setTimestamp(12, dto.getModifieddatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	
	/**
	* delete student information
	* @param b
	* @throws DatabaseException
	*/
	public void delete(StudentDTO dto) throws ApplicationException {
		
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");
	
		
	}

	
	/**
	* update student detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		
		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DOB=?,MOBILE_NO=?,EMAILID=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, dto.getCollegeid());
			pstmt.setString(2, dto.getCollegename());
			pstmt.setString(3, dto.getFirstname());
			pstmt.setString(4, dto.getLastname());
			pstmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(6, dto.getMobileno());
			pstmt.setString(7, dto.getEmailid());
			pstmt.setString(8, dto.getCreatedby());
			pstmt.setString(9, dto.getModifiedby());
			pstmt.setTimestamp(10, dto.getCreateddatetime());
			pstmt.setTimestamp(11, dto.getModifieddatetime());
			pstmt.setLong(12, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Student ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
		
	}

	public List list() throws ApplicationException {
	
		return list(0, 0);
	}

	
	/**
	* to show list of student
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentDTO dto = new StudentDTO();
				
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setDob(rs.getDate(4));
				dto.setMobileno(rs.getString(5));
				dto.setEmailid(rs.getString(6));
				dto.setCollegeid(rs.getLong(7));
				dto.setCollegename(rs.getString(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
		
	}

	public List search(StudentDTO dto) throws ApplicationException {
		
		return search(dto, 0, 0);
	}

	
	/**
	* search student information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getFirstname() != null && dto.getFirstname().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + dto.getFirstname() + "%'");
			}
			if (dto.getLastname() != null && dto.getLastname().length() > 0) {
				sql.append(" AND LAST_NAME like '" + dto.getLastname() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getDob());
			}
			if (dto.getMobileno() != null && dto.getMobileno().length() > 0) {
				sql.append(" AND MOBILE_NO like '" + dto.getMobileno() + "%'");
			}
			if (dto.getEmailid() != null && dto.getEmailid().length() > 0) {
				sql.append(" AND EMAILID like '" + dto.getEmailid() + "%'");
			}
			if (dto.getCollegename() != null && dto.getCollegename().length() > 0) {
				sql.append(" AND COLLEGE_NAME = " + dto.getCollegename());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setDob(rs.getDate(4));
				dto.setMobileno(rs.getString(5));
				dto.setEmailid(rs.getString(6));
				dto.setCollegeid(rs.getLong(7));
				dto.setCollegename(rs.getString(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
				list.add(dto);
				
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	
	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	
	public StudentDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
		StudentDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setDob(rs.getDate(4));
				dto.setMobileno(rs.getString(5));
				dto.setEmailid(rs.getString(6));
				dto.setCollegeid(rs.getLong(7));
				dto.setCollegename(rs.getString(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	
	/**
	* find the information with the help of Email
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		
		log.debug("Model findBy Email Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAILID=?");
		StudentDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstname(rs.getString(2));
				dto.setLastname(rs.getString(3));
				dto.setDob(rs.getDate(4));
				dto.setMobileno(rs.getString(5));
				dto.setEmailid(rs.getString(6));
				dto.setCollegeid(rs.getLong(7));
				dto.setCollegename(rs.getString(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
				
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return dto;

	}

}
