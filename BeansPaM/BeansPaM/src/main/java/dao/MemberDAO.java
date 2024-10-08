/**
 * 최초 생성일: 2024-09-11
 * 
 * 기여자
 * @author 강동준
 * @author 임성현
 * @author 민기홍
 * 
 * 주요 수정 내용: updateMemberInfo, updatePasswdInfo 메소드 생성
 */

package dao;

import static database.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import action.AdminAction;
import vo.MemberVO;

/* 사원 테이블에 대한 메서드가 선언된 DAO 클래스 */
public class MemberDAO {
	/**
	 * 로그인을 위한 메소드
	 * 
	 * @author 임성현
	 */
	public MemberVO Login(MemberVO memberVO) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM MEMBER WHERE M_ID = ? AND M_PW = ?"; // DB에서의 아이디, 비밀번호 값들과 입력된 값들을 비교해주는 SQL 쿼리문

		try {
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, memberVO.getM_id());
			ps.setString(2, memberVO.getM_pw());
			rs = ps.executeQuery();

			// 아이디와 비밀번호가 맞으면 MEMBER 테이블 정보를 비밀번호를 제외하고 VO 객체에 저장한다.
			if (rs.next()) {
				memberVO.setM_no(rs.getInt("M_NO"));
				memberVO.setM_id(rs.getString("M_ID"));
				memberVO.setM_name(rs.getString("M_NAME"));
				memberVO.setM_day(rs.getDate("M_DAY"));
				memberVO.setM_position(rs.getString("M_POSITION"));
				memberVO.setM_phone(rs.getString("M_PHONE"));
				memberVO.setM_leave(rs.getInt("M_LEAVE"));
				memberVO.setM_salary(rs.getInt("M_SALARY"));
				memberVO.setM_dept(rs.getString("M_DEPT"));
				memberVO.setM_email(rs.getString("M_EMAIL"));
			} else {
				memberVO.setM_id("false"); // 아이디와 비밀번호가 맞지 않으면 id에 임시로 false 값 저장
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return memberVO;
	}

	/**
	 * 사용자 신청을 위한 메소드
	 * 
	 * @author 임성현
	 */
	public int memberRegister(MemberVO memberVO) {
		int registerCount = 0; // 0: 사용자 신청 실패, 1: 사용자 신청 성공, 2: 잘못된 사원 번호 입력, 3: 이미 아이디를 생성함, 4: 아이디 중복

		Connection con = getConnection();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		Statement st = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		String sql1 = "SELECT M_ID, M_NO FROM MEMBER WHERE M_NO = ?"; // 등록 여부를 확인 하기 위한 SQL 쿼리문
		String sql2 = "SELECT M_ID FROM MEMBER"; // ID 중복 확인을 위한 SQL 쿼리문
		String sql3 = "UPDATE MEMBER SET M_ID = ?, M_PW = ?, M_DAY = SYSDATE, M_LEAVE = 0, M_SALARY = 0 WHERE M_NO = ?"; // 사용자
																															// 신청을
																															// 위한
																															// SQL
																															// 쿼리문

		try {
			ps1 = con.prepareStatement(sql1);
			ps1.setInt(1, memberVO.getM_no());
			rs1 = ps1.executeQuery();

			// ResultSet객체(rs1)의 next()가 실행이 되지 않으면 M_NO(사원번호)가 잘못 되었거나 없단 의미임
			// ResultSet객체(rs1)에서 M_ID(아이디)가 null이 아니라면 이미 DB에 ID가 등록 되었다는 의미임
			if (!rs1.next()) {
				return registerCount = 2; // 2: 잘못된 사원 번호 입력
			} else {
				if (rs1.getString("M_ID") != null) {
					return registerCount = 3; // 3: 이미 아이디를 생성함
				}
			}

			st = con.createStatement();
			rs2 = st.executeQuery(sql2);

			// 입력된 아이디 값과 DB에서의 아이디 값을 비교하는 반복문(중복 ID를 확인하는 코드)
			while (rs2.next()) {
				if (rs2.getString("M_ID") != null && rs2.getString("M_ID").equals(memberVO.getM_id())) {
					return registerCount = 4; // 4: 아이디 중복
				}
			}
			ps2 = con.prepareStatement(sql3);
			ps2.setString(1, memberVO.getM_id());
			ps2.setString(2, memberVO.getM_pw());
			ps2.setInt(3, memberVO.getM_no());
			registerCount = ps2.executeUpdate();

			// registerCount가 1이면 사용자 신청에 성공해 DB에 값이 업데이트 되었단 의미임
			if (registerCount == 1) { // 1: 사용자 신청 성공
				commit();
			}
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		} finally {
			close(rs2);
			close(ps2);
			close(st);
			close(rs1);
			close(ps1);
		}
		return registerCount; // 0: 사용자 신청 실패, 1: 사용자 신청 성공
	}

	/**
	 * 아이디 비밀번호를 찾기 위한 메소드
	 * 
	 * @author 임성현
	 */
	public MemberVO findIdPw(MemberVO memberVO) {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		String sql1 = "SELECT M_ID, M_PW FROM MEMBER WHERE M_NO = ?"; // 사원번호를 입력 했을때의 쿼리문
		String sql2 = "SELECT M_ID, M_PW FROM MEMBER WHERE M_ID = ?"; // 아이디를 입력 했을때의 쿼리문

		try {
			ps1 = getConnection().prepareStatement(sql1);
			ps1.setInt(1, memberVO.getM_no());
			rs1 = ps1.executeQuery();

			// 입력된 사원 번호를 통해서 아이디와 비밀번호를 가져온다.
			if (rs1.next()) {
				memberVO.setM_id(rs1.getString("M_ID"));
				memberVO.setM_pw(rs1.getString("M_PW"));
			}

			ps2 = getConnection().prepareStatement(sql2);
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
			close(rs2);
			close(ps2);
			close(rs1);
			close(ps2);
		}
		return memberVO; // M_ID, M_PW 반환
	}

	/**
	 * 사번으로 사용자 정보를 조회하는 메소드
	 * 
	 * @author 민기홍
	 * @see action.AdminDetailAction
	 */
	public MemberVO getMemberByNo(int m_no) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		MemberVO member = null;

		try {
			ps = getConnection().prepareStatement("SELECT * FROM MEMBER WHERE M_NO = ?");
			ps.setInt(1, m_no);
			rs = ps.executeQuery();

			if (rs.next()) {
				member = new MemberVO();
				member.setM_no(m_no);
				member.setM_id(rs.getString("m_id"));
				member.setM_pw(rs.getString("m_pw"));
				member.setM_name(rs.getString("m_name"));
				member.setM_day(rs.getDate("m_day"));
				member.setM_position(rs.getString("m_position"));
				member.setM_phone(rs.getString("m_phone"));
				member.setM_email(rs.getString("m_email"));
				member.setM_salary(rs.getInt("m_salary"));
				member.setM_dept(rs.getString("m_dept"));
				member.setM_leave(rs.getInt("m_leave"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}

		return member;
	}

	/**
	 * 모든 사원을 불러오는 메소드 - 관리자
	 * 
	 * @author 강동준
	 * @see action.AdminAction
	 */
	public ArrayList<MemberVO> getAllMembersListVerAdmin(String sql) {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = getConnection().createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setM_no(rs.getInt("m_no"));
				vo.setM_id(rs.getString("m_id"));
				vo.setM_name(rs.getString("m_name"));
				vo.setM_day(rs.getDate("m_day"));
				vo.setM_position(rs.getString("m_position"));
				vo.setM_phone(rs.getString("m_phone"));
				vo.setM_dept(rs.getString("m_dept"));
				vo.setM_leave(rs.getInt("m_leave"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(st);
		}

		return list;
	}

	/**
	 * 사원 데이터를 생성하는 메소드 - 관리자
	 * 
	 * @author 강동준
	 * @see action.MemberRegistAction
	 */
	public int InsertEmp(MemberVO vo) {
		PreparedStatement ps = null;
		int result = 0;

		try {
			ps = getConnection()
					.prepareStatement("INSERT INTO member (m_no, m_name, m_position, m_phone, m_dept, m_email) "
							+ "VALUES (seq_m_no.nextval, ?, ?, ?, ?, ?)");
			ps.setString(1, vo.getM_name());
			ps.setString(2, vo.getM_position());
			ps.setString(3, vo.getM_phone());
			ps.setString(4, vo.getM_dept());
			ps.setString(5, vo.getM_email());

			result = ps.executeUpdate();
			if (result > 0) {
				commit();				
			} else {
				rollback();
			}
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		} finally {
			close(ps);
		}

		return result;
	}

	/**
	 * 사원 정보를 사번으로 삭제하는 메소드 - 관리자
	 * 
	 * @author 강동준
	 * @see admin.jsp
	 */
	public void deleteEmpForNo(int m_no) {
		PreparedStatement ps = null;

		try {
			ps = getConnection().prepareStatement("DELETE FROM member WHERE m_no = ?");
			ps.setInt(1, m_no);
			int result = ps.executeUpdate();

			if (result > 0) {
				commit();
			}
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	/**
	 * 페이지 수를 반환해주는 함수
	 * 
	 * @author 강동준
	 * @see AdminAction
	 */
	public int getPageCount(String sql) {
		int count = 0;
		Statement st = null;
		ResultSet rs = null;

		try {
			st = getConnection().createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				count = rs.getInt("c");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(st);
		}

		if (count > 0) {
			count = (count - 1) / 10 + 1;
		}

		return count;
	}

	/**
	 * 사원 정보 변경
	 * 
	 * @author 민기홍
	 * @since 09.25
	 * @see action.ChangePasswordAction
	 */
	public boolean updateMemberInfo(int m_no, String email, String phone) {
		System.out.println("updateMemberInfo 실행");
		PreparedStatement ps = null;
		boolean isUpdated = false;

		try {
			String sql = "UPDATE member SET m_email = ?, m_phone = ? WHERE m_no = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, phone);
			ps.setInt(3, m_no);

			int result = ps.executeUpdate();
			System.out.println("result" + result);
			if (result > 0) {
				isUpdated = true;
				commit();
			} else {
				rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		} finally {
			close(ps);
		}
		return isUpdated;
	}

	/**
	 * 비밀번호 수정
	 * 
	 * @author 민기홍
	 * @see action.ChangePasswordAction
	 * @since 09.25
	 */
	public boolean updatePasswdInfo(int m_no, String newPwd) {
		PreparedStatement ps = null;
		boolean isUpdated = false;

		try {
			String sql = "UPDATE member SET m_pw = ? WHERE m_no = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, newPwd);
			ps.setInt(2, m_no);

			int result = ps.executeUpdate();
			System.out.println("result" + result);
			if (result > 0) {
				isUpdated = true;
				commit();
			} else {
				rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		} finally {
			close(ps);
		}

		return isUpdated;
	}
}