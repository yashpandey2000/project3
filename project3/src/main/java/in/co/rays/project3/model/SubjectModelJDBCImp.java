package in.co.rays.project3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.SubjectDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.JDBCDataSource;



/**
 * JDBC implements of Subject model
 * @author Yash Pandey
 *
 */
public class SubjectModelJDBCImp implements SubjectModelInt {
     
	private static Logger log = Logger.getLogger(SubjectModelJDBCImp.class);
	/**
	* find pk
	* @return pk
	* @throws DatabaseException
	*/
	public long nextPK() throws DatabaseException {
		long pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(ID) from st_subject");
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				pk = (int) r.getLong(1);
			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}
	
	/**
	* add new subject
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		long pk = 0;
		Connection conn = null;
		try {
			pk = nextPK();
			System.out.println(pk);
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getSubjectname());
			ps.setLong(3, dto.getCourseid());
			ps.setString(4, dto.getCoursename());
			ps.setString(5, dto.getDescription());
			ps.setString(6, dto.getCreatedby());
			ps.setString(7, dto.getModifiedby());
			ps.setTimestamp(8, dto.getCreateddatetime());
			ps.setTimestamp(9, dto.getModifieddatetime());
		    ps.executeUpdate();
			
			ps.close();
			conn.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
		
	}

	
	/**
	* delete subject information
	* @param b
	* @throws DatabaseException
	*/
	
	public void delete(SubjectDTO dto) throws ApplicationException {
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_subject where ID=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			ps.close();
			conn.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");

		
	}

	
	

	/**
	* update subject detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_subject set SUBJECT_NAME=?,COURSE_ID=?,COURSE_NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");

			ps.setString(1, dto.getSubjectname());
			ps.setLong(2, dto.getCourseid());
			ps.setString(3, dto.getCoursename());
			ps.setString(4, dto.getDescription());
			ps.setString(5, dto.getCreatedby());
			ps.setString(6, dto.getModifiedby());
			ps.setTimestamp(7, dto.getCreateddatetime());
			ps.setTimestamp(8, dto.getModifieddatetime());
			ps.setLong(9, dto.getId());
			System.out.println("update data successfully");
			ps.executeUpdate();
			ps.close();
			conn.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating subject ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	* to show list of subject
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List search(SubjectDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* search role information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public SubjectDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public SubjectDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
