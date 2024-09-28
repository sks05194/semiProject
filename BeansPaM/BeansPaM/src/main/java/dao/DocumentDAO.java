/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-25
 * @author 임성현
 * 
 * 주요 수정 내용: 결재 상신 액션 클래스 생성 및 입력
 */
package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import vo.DocumentVO;
import static database.JdbcUtil.*;

public class DocumentDAO {

	public int approval(DocumentVO documentVO) {
		int att = 0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO DOCUMENT (D_NO, D_TITLE, D_CLASS, M_NO, D_CONTENT, D_REQUEST) VALUES (SEQ_D_NO.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

		try {

			if (documentVO.getM_no() < 1) {
				return att;
			}

			ps = con.prepareStatement(sql);
			ps.setString(1, documentVO.getD_title());
			ps.setString(2, documentVO.getD_class());
			ps.setInt(3, documentVO.getM_no());
			ps.setString(4, documentVO.getD_content());
			att = ps.executeUpdate();

			if (att > 0) {
				commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return att;
	}
	
	public List<DocumentVO> getPageList(int pageNum, int amount) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<DocumentVO> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String sql = "SELECT * FROM (SELECT ROWNUM RN, A.* FROM (SELECT D.*, M.M_NAME, M.M_POSITION FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO ORDER BY D.D_NO DESC) A) WHERE RN > ? AND RN <= ?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, (pageNum - 1) * amount);
			ps.setInt(2, pageNum * amount);
			rs = ps.executeQuery(); 
			
			while (rs.next()) {
				DocumentVO documentVO = new DocumentVO();
				documentVO.setD_no(rs.getInt("D_NO"));
				documentVO.setD_title(rs.getString("D_TITLE"));
				documentVO.setD_class(rs.getString("D_CLASS"));
				documentVO.setM_no(rs.getInt("M_NO"));
				documentVO.setD_m_no(rs.getInt("D_M_NO"));
				documentVO.setD_content(rs.getString("D_CONTENT"));
				if (rs.getDate("D_REQUEST") != null) {
					documentVO.setD_request(sdf.format(rs.getDate("D_REQUEST")));
				}
				if (rs.getDate("D_RESPONSE") != null) {
					documentVO.setD_response(sdf.format(rs.getDate("D_RESPONSE")));
				}
				documentVO.setM_name(rs.getString("M_NAME"));	
				documentVO.setM_position(rs.getString("M_POSITION"));
				list.add(documentVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
			connClose();
		}
		return list;
	}
	
	public int getTotalPage() {
		int result = 0;

		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) AS TOTAL FROM DOCUMENT";

		try {

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public List<DocumentVO> getPageContents(int d_no) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<DocumentVO> list = new ArrayList<>();
		DocumentVO documentVO = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String sql = "SELECT D.*, M.M_NAME, M.M_POSITION FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE D.D_NO = ?";
		
		try {
			ps = con.prepareStatement(sql); 
			ps.setInt(1, d_no);
			rs = ps.executeQuery(); 
			
			while (rs.next()) {
				documentVO = new DocumentVO();
				documentVO.setD_no(rs.getInt("D_NO"));
				documentVO.setD_title(rs.getString("D_TITLE"));
				documentVO.setD_class(rs.getString("D_CLASS"));
				documentVO.setM_no(rs.getInt("M_NO"));
				documentVO.setD_m_no(rs.getInt("D_M_NO"));
				documentVO.setD_content(rs.getString("D_CONTENT"));
				if (rs.getDate("D_REQUEST") != null) {
					documentVO.setD_request(sdf.format(rs.getDate("D_REQUEST")));
				} else {
					documentVO.setD_request("");
				}
				if (rs.getDate("D_RESPONSE") != null) {
					documentVO.setD_response(sdf.format(rs.getDate("D_RESPONSE")));
				} else {
					documentVO.setD_response("");
				}
				documentVO.setM_name(rs.getString("M_NAME"));
				documentVO.setM_position(rs.getString("M_POSITION"));
				list.add(documentVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
			connClose();
		}
		return list;
	}
	
	public int delPage(int d_no) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		
		int check = 0;
		
		String sql = "DELETE FROM DOCUMENT WHERE D_NO = ?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, d_no);
			check = ps.executeUpdate();
			
			if(check > 0) {
				commit();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps);
			connClose();
		}
		return check;
	}
	
	public List<DocumentVO> getName(int mNo) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		DocumentVO confirm = new DocumentVO();
		List<DocumentVO> getName = new ArrayList<>();
		
		String sql = "SELECT M_NAME, M_POSITION FROM MEMBER WHERE M_NO = ?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, mNo);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				confirm.setM_name(rs.getString("M_NAME"));
				confirm.setM_position(rs.getString("M_POSITION"));
			} 
		    getName.add(confirm);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
			connClose();
		}
		return getName;
	}
	
	public String Confirm(String m_name, String m_position, int d_no) {
		Connection con = getConnection();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		int d_m_no = 0;
		int check = 0;
		String d_response = null;
		
		String sql1 = "SELECT M_NO FROM MEMBER WHERE M_POSITION = ? AND M_NAME = ?";
		String sql2 = "UPDATE DOCUMENT SET D_M_NO = ?, D_RESPONSE = SYSDATE WHERE D_NO = ?";
		String sql3 = "SELECT D_RESPONSE FROM DOCUMENT WHERE D_NO = ?";
		
		try {
			ps1 = con.prepareStatement(sql1);
			ps1.setString(1, m_position);
			ps1.setString(2, m_name);
			rs1 = ps1.executeQuery();
			
			while(rs1.next()) {
				d_m_no = rs1.getInt("M_NO");
			}
			
			ps2 = con.prepareStatement(sql2);
			ps2.setInt(1, d_m_no);
			ps2.setInt(2, d_no);
			check = ps2.executeUpdate();
			
			if(check == 0) {
				return d_response = "";
			} else {
				commit();
			}
			
			ps3 = con.prepareStatement(sql3);
			ps3.setInt(1, d_no);
			rs2 = ps3.executeQuery();
			
			while(rs2.next()) {	
				if (rs2.getDate("D_RESPONSE") != null) {
					d_response = sdf.format(rs2.getDate("D_RESPONSE"));
				} else {
					d_response = "";
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return d_response;
	}
	
	public String getDName(int d_m_no) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String d_name = "";
		
		DocumentVO confirm = new DocumentVO();
		
		String sql = "SELECT M_NAME, M_POSITION FROM MEMBER WHERE M_NO = ?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, d_m_no);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				confirm.setM_name(rs.getString("M_NAME"));
				confirm.setM_position(rs.getString("M_POSITION"));
			} 
		    d_name = confirm.getM_name() + " " + confirm.getM_position();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
			connClose();
		}
		return d_name;
	}
}
