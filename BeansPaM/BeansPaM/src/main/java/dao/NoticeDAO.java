/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-21
 * @author 설보라
 * 
 * 주요 수정 내용: 1. 메소드 생성
 *                   
 */

package dao;

import static database.JdbcUtil.close;
import static database.JdbcUtil.commit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.JdbcUtil;
import vo.NoticeVO;

/* 공지사항 테이블에 대한 메서드가 선언된 DAO 클래스 */
public class NoticeDAO {
	/* 공지사항 리스트 조회를 위한 메소드 */
	public ArrayList<Map<String, Object>> noticeList() {
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		ArrayList<Map<String, Object>> noticeList = new ArrayList<>();
		
		String sql1 = "SELECT ROWNUM AS NO, N_NO, N_TITLE, N_VIEWS, TO_CHAR(N_R_DATE,'YYYY/MM/DD') AS N_R_DATE";
		       sql1 += " FROM NOTICE";
			   sql1 += " WHERE 1=1";
			   sql1 += " AND N_DELETE_YN = 'N'";
			   sql1 += " ORDER BY N_C_DATE DESC";
		try {
			ps1 = JdbcUtil.getConnection().prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			
			while(rs1.next()) {
				
				Map<String, Object> noticeMap = new HashMap<>();
				
				noticeMap.put("no", rs1.getString("NO"));
				noticeMap.put("n_no", rs1.getString("N_NO"));
				noticeMap.put("n_title", rs1.getString("N_TITLE"));
				noticeMap.put("n_views", rs1.getString("N_VIEWS"));
				noticeMap.put("n_r_date", rs1.getString("N_R_DATE"));
				
				noticeList.add(noticeMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps1);
			close(rs1);
		}
		return noticeList; 
	}
	
	/* 공지사항 등록을 위한 메소드 */
	public int noticeRegister(NoticeVO noticeVO) {
		int registerCount = 0; //
		
		PreparedStatement ps1 = null;
		Statement st = null;
		ResultSet rs1 = null;
		
		String sql1 = "INSERT INTO NOTICE VALUES (";
			   sql1 += "N_NO.NEXTVAL, ";
			   sql1 += "?, ";
			   sql1 += "?, ";
			   sql1 += "0, ";
			   sql1 += "'N'" + ", ";
			   sql1 += "SYSDATE, ";
			   sql1 += "SYSDATE, ";
			   sql1 += "'ADMIN'" + ", ";
			   sql1 += "'ADMIN'";
			   sql1 += ")";
			   
		try {		
			ps1 = JdbcUtil.getConnection().prepareStatement(sql1);
			ps1.setString(1, noticeVO.getN_title()); 
			ps1.setString(2, noticeVO.getN_content()); 
			
			registerCount = ps1.executeUpdate(); //ps1.executeQuery();			
			
			// registerCount가 1이면 공지사항 등록 성공해 DB에 값이 업데이트 셩공
			if(registerCount == 1) { // 1: 공지사항 등록 성공 
				commit(JdbcUtil.getConnection());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(st);
			close(rs1);
			close(ps1);
		}
		return registerCount;
	}

	/* 공지사항 상세내용 조회를 위한 메소드 */
	public NoticeVO noticeDetail(NoticeVO noticeVO) {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
//		ResultSet rs2 = null;
		
		//공지사항 상세내용 조회
		String sql1 = "SELECT ROWNUM AS NO, N_NO, N_TITLE, N_CONTENT, N_VIEWS, TO_CHAR(N_R_DATE,'YYYY/MM/DD')";
		       sql1 += " FROM NOTICE";
			   sql1 += " WHERE 1=1";
			   sql1 += " AND N_NO = ?";
			   sql1 += " AND N_DELETE_YN = 'N'";
			   sql1 += " ORDER BY N_C_DATE DESC";
			   
		//공지사항 조회 수 업데이트   
		String sql2 =  "UPDATE NOTICE SET N_VIEWS = (SELECT MAX(NVL(N_VIEWS,0) + 1) FROM NOTICE ";
			   sql2 += "WHERE 1=1 AND N_NO = ?)";
			   sql2 += "WHERE 1=1";
			   sql2 += "AND N_NO = ?";
		try {
			ps1 = JdbcUtil.getConnection().prepareStatement(sql1);
			ps1.setInt(1, noticeVO.getN_no());
			rs1 = ps1.executeQuery();
			
			if (rs1.next()) {
				noticeVO.setN_title(rs1.getString("N_TITLE"));
				noticeVO.setN_content(rs1.getString("N_CONTENT"));
				noticeVO.setN_no(rs1.getInt("N_NO"));
			}
			
			ps2 = JdbcUtil.getConnection().prepareStatement(sql2);
			ps2.setInt(1, noticeVO.getN_no());
			ps2.setInt(2, noticeVO.getN_no());
			ps2.executeUpdate();
			JdbcUtil.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps2);
			close(rs1);
			close(ps1);
		}
		return noticeVO; 
	}
	
}