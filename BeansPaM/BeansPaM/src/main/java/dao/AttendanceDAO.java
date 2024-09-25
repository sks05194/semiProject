package dao;

import static database.JdbcUtil.close;
import static database.JdbcUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
