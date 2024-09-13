/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-13
 * @author 강동준
 * 
 * 주요 수정 내용: m_no가 number 자료형으로 바뀜에 따라 
 */

package vo;

import java.sql.Date;

/* 사원 정보를 담는 VO 클래스 입니다. */
public class MemberVO {
	int m_no = 0;				// 사번
	String m_id = null;			// 아이디
	String m_pw = null;			// 비밀번호
	String m_name = null;		// 이름
	Date m_day = null;			// 입사일자
	String m_position = null;	// 직급
	String m_phone = null;		// 전화번호
	String m_email = null;		// 이메일
	int m_leave = 0;			// 남은 연차(휴가) 일수
	int m_salary = 0;			// 급여: 월급, 원화
	String m_dept = null;		// 부서명

	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }

	public String getM_id() { return m_id; }
	public void setM_id(String m_id) { this.m_id = m_id; }

	public String getM_pw() { return m_pw; }
	public void setM_pw(String m_pw) { this.m_pw = m_pw; }

	public String getM_name() { return m_name; }
	public void setM_name(String m_name) { this.m_name = m_name; }

	public Date getM_day() { return m_day; }
	public void setM_day(Date m_day) { this.m_day = m_day; }

	public String getM_position() { return m_position; }
	public void setM_position(String m_position) { this.m_position = m_position; }

	public String getM_phone() { return m_phone; }
	public void setM_phone(String m_phone) { this.m_phone = m_phone; }

	public String getM_email() { return m_email; }
	public void setM_email(String m_email) { this.m_email = m_email; }
	
	public int getM_leave() { return m_leave; }
	public void setM_leave(int m_leave) { this.m_leave = m_leave; }

	public int getM_salary() { return m_salary; }
	public void setM_salary(int m_salary) { this.m_salary = m_salary; }

	public String getM_dept() { return m_dept; }
	public void setM_dept(String m_dept) { this.m_dept = m_dept; }
}