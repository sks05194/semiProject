/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 임성현
 * 
 * 주요 수정 내용: 로그인 메뉴 DAO 작업
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

import database.JdbcUtil;
import vo.MemberVO;
import static database.JdbcUtil.*;

/* 사원 테이블에 대한 메서드가 선언된 클래스 */
public class MemberDAO {

	// 연결객체 생성
	Connection con;

	// 연결객체 생성
	public void setConnection() {
		con = JdbcUtil.getConnection();
	}

	// 아이디, 비밀번호를 DB와 비교해줄 쿼리문 메소드
	public int checkingLogin(MemberVO memberVO) {
		int loginCount = 0; // 아이디, 비밀번호가 일치하면 1, 일치하지 않으면 0으로 반환할 int 자료형 초기화
		String check = null; // DB의 아이디 값이 들어올 String 자료형 변수
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT M_ID FROM MEMBER WHERE M_ID = ? AND M_PW = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, memberVO.getM_id()); // 입력한 아이디값을 M_ID = ? 에 넣기
			ps.setString(2, memberVO.getM_pw()); // 입력한 비밀번호값을 M_PW = ? 에 넣기
			rs = ps.executeQuery(); // DB에 있는 아이디와 비밀번호를 비교한다.

			if (rs.next()) {
				check = rs.getString("M_ID"); // DB의 아이디값을 String 자료형인 check에 저장
				if (check.equals(memberVO.getM_id())) { // 입력한 아이디와 DB의 아이디값이 같은지 물어본다. 일치하면 1로 반환한다.
					loginCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return loginCount;
	}
}