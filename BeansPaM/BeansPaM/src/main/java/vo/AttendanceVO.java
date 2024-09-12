/**
 * 최초 생성일: 2024-09-12
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 멤버변수, getter, setter 선언
 */

package vo;

import java.sql.Date;

/* 근태 내역을 담는 VO 클래스입니다. */
public class AttendanceVO {
	int m_no = 0;
	Date a_workdate = null;
	Date a_checkin = null;
	Date a_checkout = null;
	String a_issue = null;
	
	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }

	public Date getA_workdate() { return a_workdate; }
	public void setA_workdate(Date a_workdate) { this.a_workdate = a_workdate; }

	public Date getA_checkin() { return a_checkin; }
	public void setA_checkin(Date a_checkin) { this.a_checkin = a_checkin; }

	public Date getA_checkout() { return a_checkout; }
	public void setA_checkout(Date a_checkout) { this.a_checkout = a_checkout; }

	public String getA_issue() { return a_issue; }
	public void setA_issue(String a_issue) { this.a_issue = a_issue; }
}