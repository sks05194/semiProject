/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-15
 * @author 임성현
 * 
 * 주요 수정 내용: 전반적인 수정 및 주석 작업
 * 
 * ps. 함수 이름 변경 함부러 안 했으면 좋겠어요.
 * 그리고 2가지고 나뉘는건 int형 말고 boolean형으로 하셨으면 좋겠다고 했습니다.\
 * 일단 sql과 if문 내부 수정하였습니다. -강동준
 */

package dao;

import java.sql.*;
import database.JdbcUtil;
import vo.MemberVO;
import static database.JdbcUtil.*;

/* 사원 테이블에 대한 메서드가 선언된 클래스 */
public class MemberDAO {
	/* 연결객체 변수 초기화 */
	Connection con = null;

	/* 연결객체 생성 메소드 */
	public void setConnection() {
		con = JdbcUtil.getConnection();
	}

	/* 로그인을 위한 메소드 */
	public boolean Login(MemberVO memberVO) {
		setConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM member WHERE m_id = ? AND m_pw = ?"; // DB에서의 아이디, 비밀번호 값들과 입력된 값들을 비교해주는 SQL 쿼리문

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, memberVO.getM_id());
			ps.setString(2, memberVO.getM_pw());
			rs = ps.executeQuery();

			// ResultSet(rs)이 공간을 가지면(rs.next()메소드가 true면) DB에서의 아이디, 비밀번호 값과 입력한 값들이 일치하는 의미이다, 즉 로그인에 성공함 
			if (rs.next()) {
				return (int) rs.getInt("M_NO") > 0; // 사원번호를 반환한다. (사원번호는 최소한 1이상의 값을 갖는다.)
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return false;
	}
	
	/* 사용자 신청을 위한 메소드 */
	public int memberRegister(MemberVO memberVO) {
		int registerCount = 0; // 0: 사용자 신청 실패, 1: 사용자 신청 성공, 2: 잘못된 사원 번호 입력, 3: 이미 아이디를 생성함, 4: 아이디 중복
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		Statement st = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String sql1 = "SELECT M_ID, M_NO FROM MEMBER WHERE M_NO = ?"; // 등록 여부를 확인 하기 위한 SQL 쿼리문
		String sql2 = "SELECT M_ID FROM MEMBER"; // ID 중복 확인을 위한 SQL 쿼리문
		String sql3 = "UPDATE MEMBER SET M_ID = ?, M_PW = ? WHERE M_NO = ?"; // 사용자 신청을 위한 SQL 쿼리문
				
		try {		
			ps1 = con.prepareStatement(sql1);
			ps1.setInt(1, memberVO.getM_no());
			rs1 = ps1.executeQuery();			
			
			// ResultSet객체(rs1)의 next()가 실행이 되지 않으면 M_NO(사원번호)가 잘못 되었거나 없단 의미임
			// ResultSet객체(rs1)에서 M_ID(아이디)가 null이 아니라면 이미 DB에 ID가 등록 되었다는 의미임
			if(!rs1.next()) { 
				return registerCount = 2; // 2: 잘못된 사원 번호 입력
			} else {
				if(rs1.getString("M_ID") != null) {
					return registerCount = 3; // 3: 이미 아이디를 생성함
				}
			}
			
			st = con.createStatement();
			rs2 = st.executeQuery(sql2);
			
			// 입력된 아이디 값과 DB에서의 아이디 값을 비교하는 반복문(중복 ID를 확인하는 코드)
			while (rs2.next()) {
				if(rs2.getString("M_ID") != null && rs2.getString("M_ID").equals(memberVO.getM_id())){
					return registerCount = 4; // 4: 아이디 중복
				}
			}
			ps2 = con.prepareStatement(sql3); 
			ps2.setString(1, memberVO.getM_id()); 
			ps2.setString(2, memberVO.getM_pw()); 
			ps2.setInt(3, memberVO.getM_no());
			registerCount = ps2.executeUpdate();
			
			// registerCount가 1이면 사용자 신청에 성공해 DB에 값이 업데이트 되었단 의미임
			if(registerCount == 1) { // 1: 사용자 신청 성공 
				commit(con);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs2 != null) close(rs2);
			if(ps2 != null) close(ps2);
			if(st != null) close(st);
			if(rs1 != null) close(rs1);
			if(ps1 != null) close(ps1);
		}
		return registerCount; // 0: 사용자 신청 실패, 1: 사용자 신청 성공 
	}

	/* 아이디 비밀번호를 찾기 위한 메소드 */
	public MemberVO findIdPw(MemberVO memberVO) {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String sql1 = "SELECT M_ID, M_PW FROM MEMBER WHERE M_NO = ?"; // 사원번호를 입력 했을때의 쿼리문
		String sql2 = "SELECT M_ID, M_PW FROM MEMBER WHERE M_ID = ?"; // 아이디를 입력 했을때의 쿼리문
		
		try {
			ps1 = con.prepareStatement(sql1);
			ps1.setInt(1, memberVO.getM_no());
			rs1 = ps1.executeQuery();
			
			// 입력된 사원 번호를 통해서 아이디와 비밀번호를 가져온다.
			if (rs1.next()) {
				memberVO.setM_id(rs1.getString("M_ID"));
				memberVO.setM_pw(rs1.getString("M_PW"));
			}
			
			ps2 = con.prepareStatement(sql2);
			ps2.setString(1, memberVO.getM_id());
			rs2 = ps2.executeQuery();
			
			// 입력된 아이디를 통해서 아이디와 비밀번호를 가져온다.
			if (rs2.next()) {
				memberVO.setM_id(rs2.getString("M_ID"));
				memberVO.setM_pw(rs2.getString("M_PW"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs2 != null) close(rs2);
			if(ps2 != null) close(ps2);
			if(rs1 != null) close(rs1);
			if(ps1 != null) close(ps2);
		}
		return memberVO; // M_ID, M_PW 반환
	}
}