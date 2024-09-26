/**
 * 기여자
 * @author 민기홍
 */

package dao;

import static database.JdbcUtil.close;
import static database.JdbcUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.AttendanceVO;

public class AttendanceDAO {
    Connection con = null;

    /* 연결객체 생성 메소드 */
    public void setConnection() {
        con = getConnection();
    }

    // attendance 테이블의 사번으로 데이터 찾기
    public ArrayList<AttendanceVO> getMemberByNo(int m_no) {
    	System.out.println("DAO m_no:"+m_no);
        PreparedStatement ps = null;
        ResultSet rs = null;
        AttendanceVO attendanceVO = null;
        ArrayList<AttendanceVO> list = new ArrayList<AttendanceVO>();
        
        try {
            // Connection 설정
            con = getConnection();

            String sql = "SELECT * FROM attendance WHERE M_NO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, m_no);
            rs = ps.executeQuery();
           System.out.println("rs: "+rs);
           System.out.println("con m_no:"+m_no);
           
            // 데이터 처리
            while (rs.next()) {
            	System.out.println("while 문");
                attendanceVO = new AttendanceVO();
                attendanceVO.setM_no(rs.getInt("M_NO"));
                attendanceVO.setA_workdate(rs.getDate("a_workdate"));
                attendanceVO.setA_checkin(rs.getString("a_checkin"));
                attendanceVO.setA_checkout(rs.getString("a_checkout"));
                attendanceVO.setA_issue(rs.getString("a_issue"));
                list.add(attendanceVO);
            }
            System.out.println("list:"+list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 자원 반환
            close(rs);
            close(ps);
           
        }

        return list;
    }

    // 이미 오늘 출근 기록이 있는지 확인하는 메서드
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
        int rs=0;
        System.out.println("checkIn time:" + checkInTime);
        System.out.println("checkIn today:" + today);
        try {
            String sql = "INSERT INTO attendance (m_no, a_workdate, a_checkin) VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'))";
            ps =  getConnection().prepareStatement(sql);
            ps.setInt(1, m_no);
            ps.setString(2, today);
            ps.setString(3, checkInTime);

            rs=ps.executeUpdate();
            System.out.println("rs!"+rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            ps =  getConnection().prepareStatement(sql);
            ps.setString(1, issue);
            ps.setInt(2, m_no);
            ps.setString(3, today);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close(ps);
        }
    }
}