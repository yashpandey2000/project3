package in.co.rays.project3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.CollegeDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.JDBCDataSource;

/**
 * JDBC implements of College model
 * @author Yash Pandey
 *
 */
public class CollegeModelJDBCImp implements CollegeModelInt {

	private static Logger log = Logger.getLogger(CollegeModelJDBCImp.class);
	
	/**
	* find pk
	* @return pk
	* @throws DatabaseException
	*/

	public long nextPK() throws DatabaseException {
		log.debug("nextPK start");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_college");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}

		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Database Exception" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("nextpk end");
		return pk = pk + 1;

	}

	
	/**
	* add new college
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	
	
	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		long pk = 0;
		CollegeDTO duplicatecollegename = findByName(dto.getName());
		if (duplicatecollegename != null) {
			throw new DuplicateRecordException("College Name Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);
			pk = nextPK();

			ps.setLong(1, pk);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getAddress());
			ps.setString(4, dto.getCity());
			ps.setString(5, dto.getState());
			ps.setString(6, dto.getPhoneno());
			ps.setString(7, dto.getCreatedby());
			ps.setString(8, dto.getModifiedby());
			ps.setTimestamp(9, dto.getCreateddatetime());
			ps.setTimestamp(10, dto.getModifieddatetime());
			ps.executeUpdate();

			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error(e);

			try {

				conn.rollback();

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception: exception in adding college" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return pk;
	}

	/**
	* delete college information
	* @param b
	* @throws DatabaseException
	*/
	
	public void delete(CollegeDTO dto) throws ApplicationException {
		log.debug("delete method start");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_college where id=?");
			ps.setLong(1, dto.getId());
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// TODO: handle exception
				throw new ApplicationException("Exception : delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in deleting college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("delete method ended");
	}

	/**
	* update college detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	
	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("update method start");
		Connection conn = null;
		CollegeDTO alreadyexist = findByName(dto.getName());

		if (alreadyexist != null) {
			throw new DuplicateRecordException("collegename already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_college set name=? , address=? , state=? , city=? , phone_no=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getAddress());
			ps.setString(3, dto.getState());
			ps.setString(4, dto.getCity());
			ps.setString(5, dto.getPhoneno());
			ps.setString(6, dto.getCreatedby());
			ps.setString(7, dto.getModifiedby());
			ps.setTimestamp(8, dto.getCreateddatetime());
			ps.setTimestamp(9, dto.getModifieddatetime());
			ps.setLong(10, dto.getId());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:update rollback exception " + e.getMessage());
			}
			throw new ApplicationException("Exception : exception in updating college ");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("update method end");
	}

	
	public List list() throws ApplicationException {

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
		Connection conn = null;
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_college");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CollegeDTO dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneno(rs.getString(6));
				dto.setCreatedby(rs.getString(7));
				dto.setModifiedby(rs.getString(8));
				dto.setCreateddatetime(rs.getTimestamp(9));
				dto.setModifieddatetime(rs.getTimestamp(10));
				list.add(dto);
			}

			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception:Exception in getting list of course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	
	public List search(CollegeDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	/**
	* search college information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	
	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("search method started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" and id = " + dto.getId());
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" and first_name like '" + dto.getName() + "%'");
			}
			if (dto.getAddress() != null && dto.getAddress().length() > 0) {
				sql.append(" and address like '" + dto.getAddress() + "%'");
			}
			if (dto.getState() != null && dto.getState().length() > 0) {
				sql.append(" and state like '" + dto.getState() + "%'");
			}
			if (dto.getCity() != null && dto.getCity().length() > 0) {
				sql.append(" and city like '" + dto.getCity() + "%'");
			}
			if (dto.getPhoneno() != null && dto.getPhoneno().length() > 0) {
				sql.append(" and phone_no like '" + dto.getPhoneno() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneno(rs.getString(6));
				dto.setCreatedby(rs.getString(7));
				dto.setModifiedby(rs.getString(8));
				dto.setCreateddatetime(rs.getTimestamp(9));
				dto.setModifieddatetime(rs.getTimestamp(10));
				list.add(dto);

			}

			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception : Exception in search college" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search method ended");

		return list;

	}

	
	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	
	public CollegeDTO findByPK(long pk) throws ApplicationException {
		CollegeDTO dto = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from st_college where id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneno(rs.getString(6));
				dto.setCreatedby(rs.getString(7));
				dto.setModifiedby(rs.getString(8));
				dto.setCreateddatetime(rs.getTimestamp(9));
				dto.setModifieddatetime(rs.getTimestamp(10));

			}
			rs.close();

		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception : Exception in getting college by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbypk end");
		return dto;
	}

	
	/**
	* find the infromation with the help of college name
	* @param name
	* @return bean
	* @throws ApplicationException
	*/
	
	public CollegeDTO findByName(String name) throws ApplicationException {
		Connection conn = null;
		CollegeDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from st_college where name=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneno(rs.getString(6));
				dto.setCreatedby(rs.getString(7));
				dto.setModifiedby(rs.getString(8));
				dto.setCreateddatetime(rs.getTimestamp(9));
				dto.setModifieddatetime(rs.getTimestamp(10));
			}
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception : Exception in getting college by name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbyname end");
		return dto;
	}

}
