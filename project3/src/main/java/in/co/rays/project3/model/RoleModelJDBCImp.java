package in.co.rays.project3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project3.dto.RoleDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.HibDataSource;
import in.co.rays.project3.util.JDBCDataSource;



/**
 * JDBC implements of Role model
 * @author Yash Pandey
 *
 */
public class RoleModelJDBCImp implements RoleModelInt{
	
	private static Logger log = Logger.getLogger(RoleModelJDBCImp.class);
	

	/**
	* find pk
	* @return pk
	* @throws DatabaseException
	*/
	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_role");
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				pk = (int) r.getLong(1);
			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}
	
	
	/**
	* add new role
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		
		Connection con = null;
		long pk = 0;
		RoleDTO duplicataRole = findByName(dto.getName());
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}
		try {
			pk=nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDescription());
			ps.setString(4, dto.getCreatedby());
			ps.setString(5, dto.getModifiedby());
			ps.setTimestamp(6, dto.getCreateddatetime());
			ps.setTimestamp(7, dto.getModifieddatetime());
		    ps.executeUpdate();
			ps.close();
			con.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model add End");

		return pk;
	}

	

	/**
	* delete role information
	* @param b
	* @throws DatabaseException
	*/
	
	
	public void delete(RoleDTO dto) throws ApplicationException {
		Connection con = null;
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from st_role where ID=?");
			ps.setLong(1, dto.getId());
			System.out.println("Delete data successfully");
			ps.executeUpdate();
			ps.close();
			con.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete roleO");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model delete Started");

		
	}

	
	
	/**
	* update role detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	
	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		RoleDTO duplicataRole = findByName(dto.getName());
		if (duplicataRole != null && duplicataRole.getId() != dto.getId()) {
			throw new DuplicateRecordException("Role already exists");
		}
		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(
					"update st_role set NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDescription());
			ps.setString(3, dto.getCreatedby());
			ps.setString(4, dto.getModifiedby());
			ps.setTimestamp(5, dto.getCreateddatetime());
			ps.setTimestamp(6, dto.getModifieddatetime());
			ps.setLong(7, dto.getId());

			System.out.println("update data successfully");
			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating role ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		
	}

	
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	
	
	/**
	* to show list of role
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_role");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		RoleDTO dto=null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 dto = new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedby(rs.getString(4));
				dto.setModifiedby(rs.getString(5));
				dto.setCreateddatetime(rs.getTimestamp(6));
				dto.setModifieddatetime(rs.getTimestamp(7));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

		
	}

	public List search(RoleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	* search role information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	
	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		 StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

		    if (dto != null) {
		        if (dto.getId() > 0) {
		            sql.append(" AND ID = " + dto.getId());
		        }
		        if (dto.getName() != null && dto.getName().length() > 0) {
		            sql.append(" AND NAME like '" + dto.getName() + "%'");
		        }
		       
		    }

		    // if page size is greater than zero then apply pagination
		    if (pageSize > 0) {
		        // Calculate start record index
		        pageNo = (pageNo - 1) * pageSize;

		        sql.append(" Limit " + pageNo + ", " + pageSize);
		        // sql.append(" limit " + pageNo + "," + pageSize);
		    }

		    System.out.println(sql);
		    ArrayList<RoleDTO> list = new ArrayList<RoleDTO>();
		    Connection conn = null;
		    try {
		        conn = JDBCDataSource.getConnection();
		        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		        ResultSet rs = pstmt.executeQuery();
		        while (rs.next()) 
		        {
		        	
		            dto = new RoleDTO();
		            dto.setId(rs.getLong(1));
		            dto.setName(rs.getString(2));
		            dto.setDescription(rs.getString(3));
	         

		            list.add(dto);
		        }
		        rs.close();
		    } catch (Exception e) {
		    	throw new ApplicationException("exception in role model  search"+e.getMessage());
		    } finally {
		        JDBCDataSource.closeConnection(conn);
		    }

		    //log.debug("Model search End");
		    return list;
		}



	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	
	public RoleDTO findByPK(long pk) throws ApplicationException {
		Connection con = null;
		RoleDTO rdto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_role where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rdto = new RoleDTO();
				rdto.setId(rs.getLong(1));
				rdto.setName(rs.getString(2));
				rdto.setDescription(rs.getString(3));

			}
			ps.close();
			con.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting role by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("model findBy pk end");

		return rdto;
	}

	

	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	public RoleDTO findByName(String name) throws ApplicationException {
		Connection con = null;
		RoleDTO rdto = null;
		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_role where NAME=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rdto = new RoleDTO();
				rdto.setId(rs.getLong(1));
				rdto.setName(rs.getString(2));
				rdto.setDescription(rs.getString(3));

			}
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by emailId");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug("Model findBy EmailId End");

		return rdto;
	}
	
	
}


