/**
 * 기여자
 * @author 민기홍
 */

package dao;

import static database.JdbcUtil.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.AttendanceVO;

public class AttendanceDAO {
	// attendance 테이블의 사번으로 데이터 찾기
	public ArrayList<AttendanceVO> getMemberByNo(int m_no) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		AttendanceVO attendanceVO = null;
		ArrayList<AttendanceVO> list = new ArrayList<AttendanceVO>();

		try {
			ps = getConnection().prepareStatement("SELECT * FROM attendance WHERE M_NO = ? ORDER BY a_workdate DESC");
			ps.setInt(1, m_no);
			rs = ps.executeQuery();

			// 데이터 처리
			while (rs.next()) {
				attendanceVO = new AttendanceVO();
				attendanceVO.setM_no(m_no);
				attendanceVO.setA_workdate(rs.getDate("a_workdate"));
				attendanceVO.setA_checkin(rs.getString("a_checkin"));
				attendanceVO.setA_checkout(rs.getString("a_checkout"));
				attendanceVO.setA_issue(rs.getString("a_issue"));
				list.add(attendanceVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(rs);
			close(ps);
		}
		return list;
	}

	public boolean isAlreadyCheckedIn(int m_no, String today) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean exists = false;

		try {
			String sql = "SELECT COUNT(*) FROM attendance WHERE m_no = ? AND a_workdate = TO_DATE(?, 'YYYY-MM-DD')";
			ps = getConnection().prepareStatement(sql);
			ps.setInt(1, m_no);
			ps.setString(2, today);
			rs = ps.executeQuery();

			if (rs.next()) {
				exists = rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		return exists;
	}

	// 출근 시간을 기록하는 메서드
	public void checkIn(int m_no, String today, String checkInTime) {
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO attendance (m_no, a_workdate, a_checkin) VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'))";
			ps = getConnection().prepareStatement(sql);
			ps.setInt(1, m_no);
			ps.setString(2, today);
			ps.setString(3, checkInTime);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			commit();
		}
	}

	// 퇴근 시간을 기록하는 메서드
	public void checkOut(int m_no, String today, String checkOutTime) {
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE attendance SET a_checkout = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') WHERE m_no = ? AND a_workdate = TO_DATE(?, 'YYYY-MM-DD')";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, checkOutTime);
			ps.setInt(2, m_no);
			ps.setString(3, today);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	// a_issue를 업데이트하는 메서드
	public void updateIssue(int m_no, String today, String issue) {
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE attendance SET a_issue = ? WHERE m_no = ? AND a_workdate = TO_DATE(?, 'YYYY-MM-DD')";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, issue);
			ps.setInt(2, m_no);
			ps.setString(3, today);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		commit();
	}
}