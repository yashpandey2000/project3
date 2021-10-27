package in.co.rays.project3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project3.dto.TimeTableDTO;
import in.co.rays.project3.exception.ApplicationException;
import in.co.rays.project3.exception.DatabaseException;
import in.co.rays.project3.exception.DuplicateRecordException;
import in.co.rays.project3.util.JDBCDataSource;



/**
 * JDBC implements of TimeTable model
 * @author Yash Pandey
 *
 */
public class TimeTableModelJDBCImp implements TimeTableModelInt {
	private static Logger log = Logger.getLogger(TimeTableModelJDBCImp.class);

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
			PreparedStatement ps = conn.prepareStatement("select max(ID) from ST_TIMETABLE");
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
		return pk = pk + 1;
	}
	
	/**
	* add new timetable
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		long pk = 0;
		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into ST_TIMETABLE values(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setLong(2, dto.getCourseid());
			ps.setString(3, dto.getCoursename());
			ps.setLong(4, dto.getSubjectid());
			ps.setString(5, dto.getSubjectname());
			ps.setString(6, dto.getSemester());
			ps.setString(7, dto.getExamtime());
			ps.setDate(8, new java.sql.Date(dto.getExamdate().getTime()));
			ps.setString(9, dto.getCreatedby());
			ps.setString(10, dto.getModifiedby());
			ps.setTimestamp(11, dto.getCreateddatetime());
			ps.setTimestamp(12, dto.getModifieddatetime());
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
	* delete timetable information
	* @param b
	* @throws DatabaseException
	*/
	public void delete(TimeTableDTO dto) throws ApplicationException {
		Connection conn =null;
	
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from ST_TIMETABLE where ID=?");
			ps.setLong(1, dto.getId());
			System.out.println("Delete data successfully");
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
			throw new ApplicationException("Exception : Exception in delete timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
		
	}

	
	/**
	* update timetable detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
		long pk = nextPK();
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update ST_TIMETABLE set SUBJECT_ID=?,SUBJECT_NAME=?,COURSE_ID=?,COURSE_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");

			ps.setLong(1, dto.getSubjectid());
			ps.setString(2, dto.getSubjectname());
			ps.setLong(3, dto.getCourseid());
			ps.setString(4, dto.getCoursename());
			ps.setString(5, dto.getSemester());
			ps.setDate(6,new java.sql.Date(dto.getExamdate().getTime()) );
			ps.setString(7, dto.getExamtime());
			ps.setString(8, dto.getCreatedby());
			ps.setString(9, dto.getModifiedby());
			ps.setTimestamp(10, dto.getCreateddatetime());
			ps.setTimestamp(11, dto.getModifieddatetime());
			ps.setLong(12, dto.getId());
			ps.executeUpdate();
			ps.close();
			conn.commit();
			System.out.println(" time table update data successfully");

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating timetable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	
	/**
	* to show list of timetable
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_timetable");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		TimeTableDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseid(rs.getLong(2));
				dto.setCoursename(rs.getString(3));
				dto.setSubjectid(rs.getLong(4));
				dto.setSubjectname(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamtime(rs.getString(7));
				dto.setExamdate(rs.getDate(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
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

	public List search(TimeTableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	* search timetable information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Connection conn =null;
		StringBuffer sql = new StringBuffer("select * from ST_TIMETABLE where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getSubjectid() > 0) {
				sql.append(" AND SUB_ID = " + dto.getSubjectid());
			}
			if ((dto.getSubjectname() != null) && (dto.getSubjectname().length() > 0)) {
				sql.append(" AND SUB_NAME like '" + dto.getSubjectname() + "%'");
			}
			if (dto.getCourseid() > 0) {
				sql.append(" AND COURSE_ID = " + dto.getCourseid());
			}
			if ((dto.getCoursename() != null) && (dto.getCoursename().length() > 0)) {
				sql.append(" AND COURSE_NAME like '" + dto.getCoursename() + "%'");

			}
			if ((dto.getSemester() != null) && (dto.getSemester().length() > 0)) {
				sql.append(" AND SEMESTER like '" + dto.getSemester() + "%'");

			}
			if ((dto.getExamdate() != null) && (dto.getExamdate().getDate() > 0)) {
				Date date = new Date(dto.getExamdate().getTime());
				System.out.println(">>>>" + date);
				sql.append(" AND EXAM_DATE = '" + date + "'");
			}

			if ((dto.getExamtime() != null) && (dto.getExamtime().length() > 0)) {
				sql.append(" AND EXAM_TIME like '" + dto.getExamtime() + "%'");

			}
			
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList<TimeTableDTO> list = new ArrayList<TimeTableDTO>();

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseid(rs.getLong(2));
				dto.setCoursename(rs.getString(3));
				dto.setSubjectid(rs.getLong(4));
				dto.setSubjectname(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamtime(rs.getString(7));
				dto.setExamdate(rs.getDate(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
				list.add(dto);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search time table");
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

	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		Connection conn =null;
		TimeTableDTO dto = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from ST_TIMETABLE where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseid(rs.getLong(2));
				dto.setCoursename(rs.getString(3));
				dto.setSubjectid(rs.getLong(4));
				dto.setSubjectname(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamtime(rs.getString(7));
				dto.setExamdate(rs.getDate(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));

			}
			ps.close();
			conn.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Timetable by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findBy pk end");

		return dto;
	}

	
	
	public TimeTableDTO checkByCourseName(long courseId, Date examDate)throws ApplicationException, DuplicateRecordException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, courseId);
			ps.setDate(2, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseid(rs.getLong(2));
				dto.setCoursename(rs.getString(3));
				dto.setSubjectid(rs.getLong(4));
				dto.setSubjectname(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamtime(rs.getString(7));
				dto.setExamdate(rs.getDate(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByCourseName..." + e.getMessage());
		}
		return dto;
		
	}

	public TimeTableDTO checkBySubjectName(long courseId, long subjectId, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, courseId);
			ps.setLong(2, subjectId);
			ps.setDate(3, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseid(rs.getLong(2));
				dto.setCoursename(rs.getString(3));
				dto.setSubjectid(rs.getLong(4));
				dto.setSubjectname(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamtime(rs.getString(7));
				dto.setExamdate(rs.getDate(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return dto;
	}


	public TimeTableDTO checkBysemester(long courseId, long subjectId, String semester, Date examDate)
			throws ApplicationException, DuplicateRecordException {
		
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		
		Date ExDate = new Date(examDate.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUB_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, courseId);
			ps.setLong(2, subjectId);
			ps.setString(3, semester);
			ps.setDate(4, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseid(rs.getLong(2));
				dto.setCoursename(rs.getString(3));
				dto.setSubjectid(rs.getLong(4));
				dto.setSubjectname(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamtime(rs.getString(7));
				dto.setExamdate(rs.getDate(8));
				dto.setCreatedby(rs.getString(9));
				dto.setModifiedby(rs.getString(10));
				dto.setCreateddatetime(rs.getTimestamp(11));
				dto.setModifieddatetime(rs.getTimestamp(12));
				
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return dto;
		
	}

}
