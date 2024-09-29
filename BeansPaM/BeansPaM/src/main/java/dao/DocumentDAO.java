/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 검색 관련 메소드 추가
 */
package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import vo.DocumentVO;
import static database.JdbcUtil.*;

/* 모든 메서드들이 한 곳으로 반환되어 이동함 */
/** @see svc.ApprovalService */
public class DocumentDAO {

	// 결재 상신 메서드 (근태, 출장, 견적)
	public int approval(DocumentVO documentVO) {
		int att = 0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO DOCUMENT (D_NO, D_TITLE, D_CLASS, M_NO, D_CONTENT, D_REQUEST, FILENAME) VALUES (SEQ_D_NO.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?)";

		try {

			if (documentVO.getM_no() < 1) {
				return att;
			}

			ps = con.prepareStatement(sql);
			ps.setString(1, documentVO.getD_title());
			ps.setString(2, documentVO.getD_class());
			ps.setInt(3, documentVO.getM_no());
			ps.setString(4, documentVO.getD_content());
			ps.setString(5, documentVO.getFilename());
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
	
	// 페이지 가져오기 메서드
	public List<DocumentVO> getPageList(int pageNum, int amount, int mNo) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<DocumentVO> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String sql = "SELECT * FROM (SELECT ROWNUM RN, A.* FROM (SELECT D.*, M.M_NAME, M.M_POSITION FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE (? = 1 OR D.M_NO = ?) ORDER BY D.D_NO DESC) A) WHERE RN > ? AND RN <= ?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, mNo);
			ps.setInt(2, mNo);
			ps.setInt(3, (pageNum - 1) * amount);
			ps.setInt(4, pageNum * amount);
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
		}
		return list;
	}
	
	// 총 페이지 수 가져오기 메서드
	public int getTotalPage(int mNo) {
		int result = 0;

		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) AS TOTAL FROM DOCUMENT WHERE (? = 1 OR M_NO = ?)";

		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mNo);
			pstmt.setInt(2, mNo);
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
	
	// 결재글 가져오기 메서드
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
				documentVO.setFilename(rs.getString("FILENAME"));
				list.add(documentVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return list;
	}
	
	// 결재글 삭제 메서드
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
		}
		return check;
	}
	
	// 이름, 직급 가져오기 메서드
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
		}
		return getName;
	}
	
	// 결재 승인 메서드
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
			close(rs2);
			close(ps3);
			close(ps2);
			close(rs1);
			close(ps1);
		}
		return d_response;
	}
	
	// 사원 이름 가져오기 메서드
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
		}
		return d_name;
	}
	
	// 검색 후 페이지 가져오는 메서드
	public int getfCount(String sel, String sfind, int nfind, int mNo){
		Connection con = getConnection();
		PreparedStatement ppst = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		int fCount = 0;
		
		String sql = "SELECT COUNT(*) FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE " + sel + " LIKE ? AND D.M_NO = ?";
		String sql2 = "SELECT COUNT(*) FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE D_RESPONSE IS NULL AND D.M_NO = ?";
		String sql3 = "SELECT COUNT(*) FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE " + sel + " LIKE ?";
		String sql4 = "SELECT COUNT(*) FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE D_RESPONSE IS NULL";
		
		try {
			ppst = con.prepareStatement(sql);
			
			if (mNo == 1) {
				if (sel.equals("D_RESPONSE")) {
					if (sfind.equals("완료")) {
						ps2 = con.prepareStatement(sql3);
						ps2.setString(1, "%" + 2 + "%");
						rs = ps2.executeQuery();
						if (rs.next()) {
							fCount = rs.getInt(1);
						}
						return fCount;
					} else {
						ps2 = con.prepareStatement(sql4);
						rs = ps2.executeQuery();
						if (rs.next()) {
							fCount = rs.getInt(1);
						}
						return fCount;
					}
				}
				ps2 = con.prepareStatement(sql3);
				if (sfind == null || sfind.equals("")) {
					ps2.setInt(1, nfind);
				} else {
					ps2.setString(1, "%" + sfind + "%");
				}
				rs = ps2.executeQuery();
				if (rs.next()) {
					fCount = rs.getInt(1);
				}
				return fCount;
			}
			
			if (sel.equals("D_RESPONSE")) {
				if (sfind.equals("완료")) {
					ppst.setString(1, "%" + 2 + "%");
					ppst.setInt(2, mNo);
					rs = ppst.executeQuery();
					if (rs.next()) {
						fCount = rs.getInt(1);
					}
					return fCount; 
				} else {
					ps = con.prepareStatement(sql2);
					ps.setInt(1, mNo);
					rs = ps.executeQuery();
					if (rs.next()) {
						fCount = rs.getInt(1);
					}
					return fCount;
				}
			}
			
			if (sfind == null || sfind.equals("")) {
				ppst.setInt(1, nfind);
			} else {
				ppst.setString(1, "%" + sfind + "%");
			}
			ppst.setInt(2, mNo);
			rs = ppst.executeQuery();
			if (rs.next()) {
				fCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ppst);
			close(ps);
			close(ps2);
		}
		return fCount; 
	}
	
	// 검색 후 총 페이지 수 가져오는 메서드
	public List<DocumentVO> getfList(int mNo, int pageNum, int amount, String sel, String sfind, int nfind) {
		Connection con = getConnection();
		PreparedStatement ppst = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<DocumentVO> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String sql = "SELECT * FROM (SELECT ROWNUM RN, A.* FROM (SELECT D.*, M.M_NAME, M.M_POSITION FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE " + sel + " LIKE ? AND (? = 1 OR D.M_NO = ?) ORDER BY D.D_NO DESC) A) WHERE RN > ? AND RN <= ?";
		String sql2 = "SELECT * FROM (SELECT ROWNUM RN, A.* FROM (SELECT D.*, M.M_NAME, M.M_POSITION FROM DOCUMENT D LEFT JOIN MEMBER M ON D.M_NO = M.M_NO WHERE " + sel + " IS NULL AND (? = 1 OR D.M_NO = ?) ORDER BY D.D_NO DESC) A) WHERE RN > ? AND RN <= ?";
		
		try {
			ppst = con.prepareStatement(sql); 
			
			if (sel.equals("D_RESPONSE")) {
				if (sfind.equals("완료")) {
					ppst.setString(1, "%" + 2 + "%");
					ppst.setInt(2, mNo);
					ppst.setInt(3, mNo);
					ppst.setInt(4, (pageNum - 1) * amount); 
					ppst.setInt(5, pageNum * amount);  
					rs = ppst.executeQuery();
					
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
					return list; 
				} else {
					ps = con.prepareStatement(sql2); 
					ps.setInt(1, mNo);
					ps.setInt(2, mNo);
					ps.setInt(3, (pageNum - 1) * amount); 
					ps.setInt(4, pageNum * amount);  
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
					return list; 
				}
			}
			
			if (sfind == null || sfind.equals("")) {
				ppst.setInt(1, nfind);				
			} else {
				ppst.setString(1, "%" + sfind + "%");
			}
			ppst.setInt(2, mNo);
			ppst.setInt(3, mNo);
			ppst.setInt(4, (pageNum - 1) * amount); 
			ppst.setInt(5, pageNum * amount);  
			rs = ppst.executeQuery(); 
			
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
			close(ppst);
			close(ps);
		}
		return list; 
	}
}